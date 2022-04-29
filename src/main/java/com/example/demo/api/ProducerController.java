package com.example.demo.api;

import com.example.demo.config.RabbitMqConfig;
import com.example.demo.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate template;

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer")
    public String sendMessageA(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        template.convertAndSend(RabbitMqConfig.EXCHANGEA, RabbitMqConfig.ROUTING_KEY_A, messageDto);
        return "Mensaje enviado";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer")
    public String sendMessageB(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        template.convertAndSend(RabbitMqConfig.EXCHANGEB, "", messageDto);
        return "Mensaje enviado";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer")
    public String sendMessageC(@RequestBody MessageDto messageDto) {
        messageDto.setMessageId(UUID.randomUUID().toString());
        messageDto.setMessageDate(new Date());
        template.convertAndSend(RabbitMqConfig.EXCHANGEC, RabbitMqConfig.ROUTING_KEY_C, messageDto);
        return "Mensaje enviado";
    }
}
