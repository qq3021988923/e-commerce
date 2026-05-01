package com.yang.service.impl;

import com.yang.mapper.DishMapper;
import com.yang.mapper.OrderMapper;
import com.yang.mapper.SetmealMapper;
import com.yang.mapper.UserMapper;
import com.yang.service.WorkspaceService;
import com.yang.vo.BusinessDataVO;
import com.yang.vo.DishOverViewVO;
import com.yang.vo.OrderOverViewVO;
import com.yang.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;



    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        // 营业额：当日已完成订单的总金额
        // 有效订单 ：当日已完成订单的数量
        // 订单完成率：有效订单/总订单
        // 平均客单价：营业额/有效订单数
        // 新增用户：当日新增用户的数量

        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = orderMapper.countByMap(map);

        map.put("status", 5);
        // 营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover=turnover == null?0.0:turnover;

        //有效订单数
        Integer validOrderCount = orderMapper.countByMap(map);

        Double unitPrice = 0.0;// 单价
        Double orderRate = 0.0;// 订单率
        if(totalOrderCount != 0 && validOrderCount != 0){

            orderRate=validOrderCount.doubleValue()/totalOrderCount;
            unitPrice=turnover/validOrderCount;
        }

        // 统计区间用户
        Integer newUsers = userMapper.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        HashMap<Object, Object> map = new HashMap<>();
        // 查询今天
        map.put("begin",LocalDateTime.now().with(LocalTime.MIN));
        map.put("status",2);
        Integer i = orderMapper.countByMap(map);

        map.put("status",3);
        Integer i2 = orderMapper.countByMap(map);

        map.put("status",5);
        Integer i5 = orderMapper.countByMap(map);

        map.put("status",6);
        Integer i6 = orderMapper.countByMap(map);

        map.put("status",null);
        Integer ii = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(i)
                .deliveredOrders(i2)
                .completedOrders(i5)
                .cancelledOrders(i6)
                .allOrders(ii)
                .build();
    }

    @Override
    public DishOverViewVO getDishOverView() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status", 1);
        Integer i1 = dishMapper.countByMap(map);

        map.put("status", 0);
        Integer i0 = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(i1)
                .discontinued(i0)
                .build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverView() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status", 1);
        Integer i1 = setmealMapper.countByMap(map);

        map.put("status", 0);
        Integer i0 = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(i1)
                .discontinued(i0)
                .build();
    }
}
