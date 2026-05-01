package com.yang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 * 定义交换机、队列、绑定关系
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    // ==================== 场景1：学生操作通知 ====================
    
    /**
     * 交换机：订单相关操作的直连交换机
     * Direct Exchange: 精确匹配routingKey
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange");
    }
    
    /**
     * 队列1：新增/修改订单通知队列（持久化）
     * durable=true: 服务器重启后队列不会丢失
     */
    @Bean
    public Queue orderNotifyQueue() {
        return QueueBuilder.durable("order.notify.queue").build();
    }

    /**
     * 绑定：交换机 + routingKey + 队列
     * 当发送消息的routingKey = "order.add" 时，会路由到这个队列
     */
    @Bean
    public Binding orderNotifyBinding() {
        return BindingBuilder.bind(orderNotifyQueue())
                .to(orderExchange()).with("order.add");
    }

    @Bean
    public Queue orderTimeoutQueue() {
        return QueueBuilder.durable("order.timeout.queue").build();
    }

    @Bean
    public Binding orderTimeoutBinding() {
        return BindingBuilder.bind(orderTimeoutQueue())
                .to(orderExchange()).with("order.timeout");
    }


    // ==================== 场景2：延迟消息（死信队列）====================
    
    /**
     * 延迟队列：用于实现"超时自动取消"功能
     * TTL=30秒: 消息在队列中存活30秒后过期
     * 过期后转入死信交换机->重新绑定队列
     */
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable("order.delay.queue")
                .deadLetterExchange("order.exchange")           // 死信交换机
                .deadLetterRoutingKey("order.timeout")         // 死信路由键
                .ttl(30000)                                     // 30秒过期（演示用，实际15分钟）
                .build();
    }
    
    /**
     * 绑定延迟队列
     */
    @Bean
    public Binding orderDelayBinding() {
        // 路由键匹配成功，将消息扔进【延迟队列】等待30秒  然后调用order.timeout匹配这个路由的方法
        return BindingBuilder.bind(orderDelayQueue())
                .to(orderExchange()).with("order.delay");
    }
}
