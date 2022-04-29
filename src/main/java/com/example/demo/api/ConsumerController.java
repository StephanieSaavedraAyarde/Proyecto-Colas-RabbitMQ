package com.example.demo.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConsumerController {

    @RabbitListener(queues = "QueueA")
    private void receiveFromA (Message message){
        log.info("Mensaje recibido de QUEQUE A->{}", message);
    }

    @RabbitListener(queues = "QueueB")
    private void receiveFromB (Message message){
        log.info("Mensaje recibido de QUEQUE B->{}", message);
    }

    @RabbitListener(queues = "QueueC")
    private void receiveFromC (Message message){
        log.info("Mensaje recibido de QUEQUE C->{}", message);
    }
}
