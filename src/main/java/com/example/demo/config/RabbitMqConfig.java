package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUEA = "QueueA";
    public static final String QUEUEB = "QueueB";
    public static final String QUEUEC = "QueueC";
    public static final String EXCHANGEA = "Direct Exhange";
    public static final String EXCHANGEB = "Fanout Exchange";
    public static final String EXCHANGEC = "Topic Exchange";
    public static final String ROUTING_KEY_A = "routing_key_A";
    public static final String ROUTING_KEY_C = "routing_key_C";

    @Bean
    public Queue queueA() {
        return new Queue(QUEUEA);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUEB);
    }

    @Bean
    public Queue queueC() {
        return new Queue(QUEUEC);
    }

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange(EXCHANGEA);
    }

    @Bean
    public FanoutExchange exchangeB() {
        return new FanoutExchange(EXCHANGEB);
    }

    @Bean
    public TopicExchange exchangeC() {
        return new TopicExchange(EXCHANGEC);
    }

    @Bean
    public Binding bindingA(Queue queueA, DirectExchange exchangeA) {
        return BindingBuilder
                .bind(queueA)
                .to(exchangeA)
                .with(ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingB(Queue queueB, FanoutExchange exchangeB) {
        return BindingBuilder
                .bind(queueB)
                .to(exchangeB);
    }

    @Bean
    public Binding bindingC(Queue queueC, TopicExchange exchangeC) {
        return BindingBuilder
                .bind(queueC)
                .to(exchangeC)
                .with(ROUTING_KEY_C);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
