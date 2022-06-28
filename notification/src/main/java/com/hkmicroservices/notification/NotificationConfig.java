package com.hkmicroservices.notification;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    //ALL THESE VALUES COME FROM THE RABBITMQ SECTION OF THE APPLICATION yml FILE
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    // LETS DEFINE OR CONFIGURE OUR INTERNAL TOPIC EXCHANGE
    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(this.internalExchange);
    }

    // LETS DEFINE OR CONFIGURE THE QUEUE
    @Bean
    public Queue notificationQueue(){
        return new Queue(this.notificationQueue);
    }

    // THEN WE CAN BIND BOTH THE EXCHANGE AND QUEUE TOGETHER WITH THE ROUTING KEY
    @Bean
    public Binding internalExchangeToNotificationQueueBinding(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }
}
