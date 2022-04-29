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
    public static final String ROUTING_KEY_C = "routing_key_Topic";
    public static final String ROUTING_KEYTOPIC1 = "queue.teacher.*";
    public static final String ROUTING_KEYTOPIC2 = "queue.estudiantes.*";

    // Direct Exchange
    @Bean
    public Queue queueA() {
        return new Queue(QUEUEA);
    }

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange(EXCHANGEA);
    }

    @Bean
    public Binding bindingA(Queue queueA, DirectExchange exchangeA) {
        return BindingBuilder
                .bind(queueA)
                .to(exchangeA)
                .with(ROUTING_KEY_A);
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

    // Fanout Exchange
    @Bean
    public Queue queueB() {
        return new Queue(QUEUEB);
    }

    @Bean
    public FanoutExchange exchangeB() {
        return new FanoutExchange(EXCHANGEB);
    }

    @Bean
    public Binding financeBindingB1(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    public Binding financeBindingB2(Queue queueFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueFanout).to(fanoutExchange);
    }

    // Topic Exchange
    @Bean
    public Queue queueC() {
        return new Queue(QUEUEC);
    }

    @Bean
    Queue teacher() {
        return new Queue("teacher");
    }
    @Bean
    Queue estudiantes() {
        return new Queue("estudiantes");
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGEC);
    }
    @Bean
    Binding teacherBinding(Queue teacher,TopicExchange topicExchange  ) {
        return BindingBuilder.bind(teacher).to(topicExchange).with("queue.teacher.*");
   }
    @Bean
    Binding estudianteBinding(Queue estudiantes,TopicExchange topicExchange  ) {
        return BindingBuilder.bind(estudiantes).to(topicExchange).with("queue.estudiantes.* uniforme .*");
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with(ROUTING_KEY_C);
    }
}
