package com.hkmicroservices.amqp;


import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor //Allows us to have parameterized constructor for Constructor Injection
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory; //Constructor injection of the connectionfactory

    //This config allows us to publish messages to a QUEUE
    @Bean
    public AmqpTemplate amqpTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonConverter()); //Convert message from Java Object to Json
        return template;
    }

    //This config allows us to consume messages from a QUEUE
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }

    //This config allows us to serialize and deserialize messages sent to, and received from a QUEUE
    @Bean
    public MessageConverter jacksonConverter(){
        MessageConverter messageConverter = new Jackson2JsonMessageConverter();
        return messageConverter;
    }
}
