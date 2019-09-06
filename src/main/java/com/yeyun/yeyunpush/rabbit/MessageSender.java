package com.yeyun.yeyunpush.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component

public class MessageSender {

    /**
     * AmqpTemplate可以说是RabbitTemplate父类，RabbitTemplate实现类RabbitOperations接口，RabbitOperations继承了AmqpTemplate接口
     */
    @Autowired

    private AmqpTemplate rabbitTemplate;

    @Autowired

    private RabbitTemplate rabbitTemplate1;
    /**
     * 用于单生产者-》单消费者测试
     */
    public void send(Object param) {
        //String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + param);
        this.rabbitTemplate1.convertAndSend("helloQueue", param);

    }
}