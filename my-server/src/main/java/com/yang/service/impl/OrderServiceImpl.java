package com.yang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.context.BaseContext;
import com.yang.dto.*;
import com.yang.entity.*;
import com.yang.exception.BaseException;
import com.yang.mapper.*;
import com.yang.result.PageResult;
import com.yang.service.OrderService;
import com.yang.utils.WeChatPayUtil;
import com.yang.vo.*;
import com.yang.websocket_mq.RabbitMQProducer;
import com.yang.websocket_mq.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;



    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO dto) {

        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<Orders> orders = orderMapper.pageQuery(dto);

        return new PageResult(orders.getTotal(),orders.getResult());
    }

    @Override
    public OrderStatisticsVO statistics() {

        Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
        Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        OrderStatisticsVO vo=OrderStatisticsVO.builder()
                .toBeConfirmed(toBeConfirmed)
                .confirmed(confirmed)
                .deliveryInProgress(deliveryInProgress)
                .build();
        return vo;
    }

    @Override
    public OrderVO details(Long id) {

        Orders orders = orderMapper.getById(id);
        List<OrderDetail> byOrderId = orderDetailMapper.getByOrderId(orders.getId());
        OrderVO v=new OrderVO();
        BeanUtils.copyProperties(orders,v);
        v.setOrderDetailList(byOrderId);

        return v;
    }

    @Override
    public void confirm(OrdersConfirmDTO dto) {

        Orders o=Orders.builder()
                .id(dto.getId())
                .status(Orders.CONFIRMED)
                .build();

        orderMapper.update(o);
    }


    @Override // 拒单
    public void rejection(OrdersRejectionDTO dto) throws Exception {
        // 查询记录 订单只有存在且状态为2（待接单）才可以拒单/抛 if是否支付状态 调用微信工具
        // 更新当前数据记录  id,6,取消原因，时间
        Long id = dto.getId();
        Orders o = orderMapper.getById(id);
        if(o == null || !o.getStatus().equals(Orders.TO_BE_CONFIRMED)){
           throw new BaseException("订单错误");
        }

        if(o.getPayStatus() == Orders.PAID){ // 调用支付工具 退款
            String refund = weChatPayUtil.refund(o.getNumber());
            log.info("商家拒单：{}", refund);
        }


        Orders orders=new Orders();
        orders.setId(id);
        orders.setStatus(6);
        orders.setRejectionReason(dto.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        orderMapper.update(orders);
    }

    @Override
    public void cancel(OrdersCancelDTO dto) throws Exception {

        Long id = dto.getId();
        Orders o = orderMapper.getById(id);

        if(o.getPayStatus() == Orders.PAID){
            String refund = weChatPayUtil.refund(o.getNumber());
            log.info("申请退款：{}", refund);
        }

        Orders orders=new Orders();
        orders.setId(id);
        orders.setStatus(6);
        orders.setCancelTime(LocalDateTime.now());
        orders.setCancelReason(dto.getCancelReason());
        orderMapper.update(orders);

    }

    @Override // 将 已接单 修改为 派送中
    public void delivery(Long id) {

        Orders o = orderMapper.getById(id);

        if(o == null || !o.getStatus().equals(Orders.CONFIRMED)) {
            throw new BaseException("订单错误");
        }

        Orders o2=new Orders();
        o2.setId(id);
        o2.setStatus(4);
        o2.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(o2);
    }

    @Override //　将　派送中　修改为　完成
    public void complete(Long id) {

        Orders o = orderMapper.getById(id);
        if(o == null || !o.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)) {
            throw new BaseException("订单错误");
        }

        Orders o2=new Orders();
        o2.setId(id);
        o2.setStatus(5);
        orderMapper.update(o2);
    }

    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO dto) {

        // 1. 查询收货地址
        AddressBook addressBook = addressBookMapper.getById(dto.getAddressBookId());
        if (addressBook == null) {
            throw new BaseException("用户地址为空");
        }

        Long userId = BaseContext.getCurrentId();

        // 2. 查询当前用户的购物车
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> cartList = shoppingCartMapper.list(shoppingCart);
        if (cartList == null || cartList.isEmpty()) {
            throw new BaseException("购物车数据为空");
        }

        // 3. 计算订单总金额，并构建订单明细列表（暂不设置 orderId）
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (ShoppingCart cart : cartList) {

            // 计算该商品的小计金额（单价 × 数量）
            BigDecimal itemAmount = cart.getAmount().multiply(new BigDecimal(cart.getNumber()));
            totalAmount = totalAmount.add(itemAmount); //金额累加

            // 创建订单明细
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setAmount(itemAmount);   // 覆盖为正确的小计
            // 注意：此时订单主表尚未插入，orderId 暂不设置
            orderDetailList.add(orderDetail);
        }

        // 4. 创建订单主表对象
        Orders orders = new Orders();
        BeanUtils.copyProperties(dto, orders);   // 复制 addressBookId, remark 等

        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);   // 待付款状态
        orders.setNumber(String.valueOf(System.currentTimeMillis())); // 订单号
        orders.setAddress(addressBook.getDetail());  // 详细地址（建议拼接省市区）
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);
        orders.setAmount(totalAmount);   // 设置正确的订单总金额

        // 5. 插入订单主表（获得自增 ID）
        orderMapper.insert(orders);

        // 6. 回填订单明细的 orderId 并批量插入
        for (OrderDetail detail : orderDetailList) {
            detail.setOrderId(orders.getId());
        }
        orderDetailMapper.insertBatch(orderDetailList);

        // 7. 清空购物车
        shoppingCartMapper.deleteByUserId(userId);

//        // 8. WebSocket 推送新订单消息
//        Map<String, Object> map = new HashMap<>();
//        map.put("type", 1);   // 1表示来单，2表示催单
//        map.put("orderId", orders.getId());
//        map.put("content", "订单号：" + orders.getNumber());
//        map.put("amount", orders.getAmount());

       // webSocketServer.sendToAllClient(JSON.toJSONString(map));
        rabbitMQProducer.sendorderNotify(orders.getId(),"add");

        rabbitMQProducer.sendDelayMessage(orders.getId());// 规定时间未支付取消订单
        // 9. 返回结果
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();
    }

    @Override // 生成支付参数，不改库
    public OrderPaymentVO payment(OrdersPaymentDTO dto) throws Exception {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        JSONObject pay = WeChatPayUtil.pay(dto.getOrderNumber(), new BigDecimal(0.01),"准备下单",user.getOpenid());

        // orderpaid 已支付
//        if(pay.getString("code") !=null && pay.getString("code").equals("orderpaid")){
//            throw new BaseException("已支付");
//        }
        OrderPaymentVO vo=new OrderPaymentVO();
        vo.setOrderNumber(pay.getString("order_number"));
        vo.setTimeStamp(pay.getString("timeStamp"));
        vo.setNonceStr(pay.getString("nonceStr"));
        vo.setSignType(pay.getString("signType"));
        vo.setPackageStr(pay.getString("package"));
        vo.setPaySign(pay.getString("paySign"));

        return vo;
    }

    @Override
    public List<OrderVO> pageQuerysUser(int  pageNum, int  pageSize, Integer status) {

        PageHelper.startPage(pageNum, pageSize);
        Long userId = BaseContext.getCurrentId();
        OrdersPageQueryDTO dto = new OrdersPageQueryDTO();
        dto.setUserId(userId);
        dto.setStatus(status);

        Page<Orders> orders = orderMapper.pageQuery(dto);
        List<Orders> list = orders.getResult();

        OrderVO vo=new OrderVO();
        List<OrderVO> vlist=new ArrayList<>();
        for(Orders o:list){

            Long id = o.getId();
            List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(id);
            BeanUtils.copyProperties(o,vo);
            vo.setOrderDetailList(orderDetails);
            vlist.add(vo);
        }

        return vlist;
    }

    @Override
    public void userCancelById(Long id) throws Exception {
        Orders o = orderMapper.getById(id);
        if(o == null ){
            throw new BaseException("订单不存在");
        }

        if(o.getStatus() > 2){
            throw new BaseException("订单异常");
        }

        if(o.getStatus() == Orders.TO_BE_CONFIRMED){ // 调用微信工具退款
            weChatPayUtil.refund(o.getNumber());
            o.setPayStatus(Orders.REFUND);
        }

        o.setStatus(Orders.CANCELLED);
        o.setCancelTime(LocalDateTime.now());
        orderMapper.update(o);
    }

    @Override
    public void repetition(Long orderId) {
        // 根据订单id查询当前订单详情 将订单详情对象转换为购物车对象 重新设置购物用户和创建时间 批量插入
        List<OrderDetail> orderDetail = orderDetailMapper.getByOrderId(orderId);

        Long userId = BaseContext.getCurrentId();

        List<ShoppingCart> cartList= orderDetail.stream().map(detail->{
            ShoppingCart cart=new ShoppingCart();
            BeanUtils.copyProperties(detail,cart,"id");
            cart.setUserId(userId);
            cart.setCreateTime(LocalDateTime.now());
            return cart;
        }).collect(Collectors.toList()); // 转成list

        shoppingCartMapper.insertBatch(cartList);

//        // 插入后，每个 cart 对象的 id 已经被回填
//        for (ShoppingCart cart : cartList) {
//            System.out.println("已经被回填"+cart.getId());  // 这里能拿到自增ID
//            rabbitMQProducer.sendorderNotify(cart.getId(),"add");
//        }





    }

    @Override
    public Map<String,Object> reminder(Long id) {
        Orders orders = orderMapper.getById(id);
        if(orders == null){
            throw new BaseException("订单异常");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("type",2); // 1表示来电 2客户催单
        map.put("orderId",id);
        map.put("content","订单号："+orders.getNumber());
        map.put("message","客户催单");

        // 前端发送后端  用于测试
        webSocketServer.onMessage("计算机应用技术","zhangsan");

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json); // 服务器主动给所有连接的客户端发消息
        return map;


    }
}
