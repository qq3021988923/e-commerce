package com.yang.websocket_mq;

import com.rabbitmq.client.Channel;
import com.yang.entity.Orders;
import com.yang.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RabbitMQ消费者
 * 负责监听队列并处理消息
 * 自动收消息的工具：MQ 一有消息，它自动处理、推送给前端
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {

    private final WebSocketServer webSocketServer;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 消费者1：监听订单通知消息 → 推送WebSocket给所有客户端
     *
     * @param messageBody 消息内容（格式：orderId:operation）
     * @param message      RabbitMQ消息对象（包含deliveryTag等信息）
     * @param channel      通道（用于手动ACK/NACK）
     */
    @RabbitListener(queues = "order.notify.queue")
    public void handleorderNotify(String messageBody, Message message, Channel channel) {
        // 获取消息的唯一标识（用于ACK确认）
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            log.info("【RabbitMQ消费者】收到订单通知消息：{}", messageBody);

            // 解析消息内容
            String[] parts = messageBody.split(":");
            Integer orderId = Integer.parseInt(parts[0]); // 生产者存放的id
            String operation = parts[1]; //  // 得到 "add"

            // TODO: 这里可以查询数据库获取详细信息
            // order order = orderMapper.getorderById(orderId);

            // 推送WebSocket通知给所有连接的客户端
            String notifyMsg;
            if ("add".equals(operation)) {
                notifyMsg = "🎉 新增订单成功！ID=" + orderId + "，请刷新列表查看";
            } else {
                notifyMsg = "✏️ 订单信息已更新！ID=" + orderId;
            }

            webSocketServer.sendToAllClient("消费者监听的" + orderId + " ");

            log.info("【RabbitMQ消费者】已通过WebSocket推送通知：{}", notifyMsg);

            // ✅ 手动ACK：告诉RabbitMQ消息处理成功，可以删除了
            channel.basicAck(deliveryTag, false);
            log.info("【RabbitMQ消费者】消息处理完成，已发送ACK确认");

        } catch (Exception e) {
            log.error("【RabbitMQ消费者】处理消息失败！", e);

            try {
                // ❌ 手动NACK：消息处理失败，重新放回队列（让其他消费者重试）
                // 参数1: deliveryTag
                // 参数2: multiple（是否批量确认）
                // 参数3: requeue（是否重新入队，true=重新入队，false=丢弃或进入死信队列）
                channel.basicNack(deliveryTag, false, true);
                log.warn("【RabbitMQ消费者】消息已重新入队，等待下次重试");
            } catch (Exception ex) {
                log.error("【RabbitMQ消费者】NACK操作失败", ex);
            }
        }
    }

    /**
     * 消费者2：监听延迟消息（死信队列）
     * 场景：30秒后检查某个操作是否超时
     *
     * @param messageBody 消息内容（格式：timeout:orderId）
     * @param message      RabbitMQ消息对象
     * @param channel      通道
     */

 /*   @RabbitListener(queues = "order.timeout.queue")  // 注意：实际应该是专门的超时队列
    public void handleTimeoutMessage(String messageBody, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            log.info("【RabbitMQ-延迟消息】收到超时消息：{}", messageBody);

            // 解析消息
            String[] parts = messageBody.split(":");
            Integer orderId = Integer.parseInt(parts[1]);

            // TODO: 检查业务状态（比如：订单是否已支付、任务是否已完成）
            // if (orderStatus == PENDING) {
            //     orderService.cancelOrder(orderId);
            //     log.info("订单{}超时未支付，已自动取消", orderId);
            // }


            LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

            List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);
            if(ordersList !=null && ordersList.size()>0){

                for(Orders order:ordersList){
                    System.out.println(order.getNumber()+"订单被我取消了");
                    order.setStatus(Orders.CANCELLED);
                    order.setCancelReason("订单操作，自动取消");
                    order.setCancelTime(LocalDateTime.now());

                    webSocketServer.sendToAllClient("订单操作，自动取消"+order.toString());
                    orderMapper.update(order);
                }
            }


            // ACK确认
            channel.basicAck(deliveryTag, false);
            log.info("【RabbitMQ-延迟消息】超时消息处理完成");

        } catch (Exception e) {
            log.error("【RabbitMQ-延迟消息】处理失败", e);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (Exception ex) {
                log.error("NACK失败", ex);
            }
        }
    }*/
    @RabbitListener(queues = "order.timeout.queue")
    public void handleTimeoutMessage(String messageBody, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("【延迟队列】收到超时消息: {}", messageBody);
            // 1. 解析 "timeout:订单ID"
            String[] parts = messageBody.split(":");
            Long orderId = Long.parseLong(parts[1]);

            // 2. 根据ID查询订单（精准定位到单个订单）
            Orders order = orderMapper.getById(orderId);
            if (order == null) {
                log.warn("订单不存在, 直接ACK: orderId={}", orderId);
                channel.basicAck(deliveryTag, false);
                return;
            }

            // 3. 只有待支付订单才取消（做幂等保护）
            if (order.getStatus() == Orders.PENDING_PAYMENT) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("超时未支付，系统自动取消(30秒)");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);

                // 4. 推送通知
                webSocketServer.sendToAllClient("订单 " + order.getNumber() + " 已超时自动取消");
                log.info("订单已取消: {}", order.getNumber());
            } else {
                log.info("订单状态已变更，无需取消: orderId={}, status={}", orderId, order.getStatus());
            }

            // 5. 手动ACK
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("处理超时消息异常", e);
            try {
                // 异常时拒绝且不重新入队，避免死信队列堆积
                channel.basicNack(deliveryTag, false, false);
            } catch (Exception ex) {
                log.error("NACK失败", ex);
            }
        }
    }






}
