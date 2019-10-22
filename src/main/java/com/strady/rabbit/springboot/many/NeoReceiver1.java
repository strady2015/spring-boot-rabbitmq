package com.strady.rabbit.springboot.many;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 22:44:41
 * @Description:
 */
@Component
@RabbitListener(queues = "neo")
public class NeoReceiver1 {

    @RabbitHandler
    public void process(String neo) {
        System.out.println("Receiver 1:" + neo);
    }
}
