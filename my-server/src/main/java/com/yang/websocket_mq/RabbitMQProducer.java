package com.yang.websocket_mq;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RabbitMQ生产者
 * 负责发送消息到队列
 */
@Component
@RequiredArgsConstructor
@Slf4j //发消息的工具：谁要发消息，调用它就行
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 初始化生产者确认回调
     * 派送成功或失败都给我发条短信
     */
    @PostConstruct
    public void initConfirmCallback() {
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            if (ack) {
                log.info("✅ 消息成功发送到 RabbitMQ Broker，correlationData: {}", correlationData);
            } else {
                log.error("❌ 消息发送到 Broker 失败！原因: {}, correlationData: {}", cause, correlationData);
                // TODO: 这里可以添加重试或告警逻辑
            }
        });
    }


    /**
     * 发送订单操作通知消息
     * 场景：新增/修改订单后，异步通知WebSocket客户端
     *
     * @param orderId 订单ID
     * @param operation 操作类型（add/update）
     */
    public void sendorderNotify(Long orderId, String operation) {
        log.info("【RabbitMQ】发送订单通知消息：orderId={}, operation={}", orderId, operation);
        // 构造 CorrelationData，可以存业务 ID
        CorrelationData correlationData = new CorrelationData("delay-" + orderId);
        // 构造消息内容（可以用JSON，这里简单用字符串拼接）
        String message = orderId + ":" + operation;

        // 发送消息到交换机
        // 参数1: 交换机名称
        // 参数2: 路由键（决定消息去哪个队列）
        // 参数3: 消息内容
        rabbitTemplate.convertAndSend(
                "order.exchange",   // 交换机
                "order.add",       // 路由键 → 匹配 order.notify.queue 队列
                message ,             // 消息内容
                correlationData
        );

        log.info("【RabbitMQ】消息发送成功！消息内容：{}", message);
    }

    /**
     * 发送延迟消息
     * 场景：30秒后检查某个操作是否完成（演示延迟队列用法）
     *
     * @param orderId 订单ID
     */
    public void sendDelayMessage(Long orderId) {
        log.info("【RabbitMQ】发送延迟消息（30秒后触发）：orderId={}", orderId);
        // 构造 CorrelationData，可以存业务 ID
        CorrelationData correlationData = new CorrelationData("delay-" + orderId);

//        webSocketServer.sendToAllClient("我是生产则的延迟方法30秒后触发再触发一次\n" +
//         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


        // 发送到延迟队列（TTL=30秒）
        rabbitTemplate.convertAndSend(
                "order.exchange",
                "order.delay",      // 路由键 匹配 → 进入 order.delay.queue 延迟队列
                "timeout:" + orderId,
                correlationData
        );

        log.info("【RabbitMQ】延迟消息已发送，30秒后将自动触发超时处理");
    }
}
