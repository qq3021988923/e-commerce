package com.yang.task;

import com.yang.entity.Orders;
import com.yang.mapper.OrderMapper;
import com.yang.websocket_mq.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderScheduled {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WebSocketServer webSocketServer;

     //使用mq代替
    // @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeoutOrder(){
        log.info("定时处理超时订单", LocalDateTime.now());
        // 举个例子 我下单时间是10点，当前往前推15分钟就是9.45，查询9.45之前所有的订单数据。
        // 过了20分钟之后，又重新扫描了这个方法，当前再往前推就是10.05分，查询这个10.05之前所有的订单数据
        // 下单时间一直都是10点不会变

        // 如果现在是 2026-03-31 18:00:00 返回  2026-03-31 17:45:00
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        //webSocketServer.sendToAllClient("1时间"+time);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
        if(ordersList !=null && ordersList.size()>0){
            //webSocketServer.sendToAllClient("1每分钟监听订单");
            for(Orders order:ordersList){
                System.out.println(order.getNumber()+"订单被我取消了");
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单操作，自动取消");
                order.setCancelTime(LocalDateTime.now());

                webSocketServer.sendToAllClient("订单操作，自动取消"+order.toString());
                orderMapper.update(order);
            }
        }
    }







    @Scheduled(cron = "0 0 1 * * ? ") // 每天凌晨1点触发
    public void processDelivery(){

        log.info("定时处理派送中的订单", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList !=null && ordersList.size()>0){
            for(Orders order:ordersList){

                System.out.println(order.getNumber()+"订单自动完成");
                webSocketServer.sendToAllClient("订单自动完成"+order.toString());

                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }






    }

}
