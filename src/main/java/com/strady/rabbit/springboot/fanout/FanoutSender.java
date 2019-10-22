package com.strady.rabbit.springboot.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: strady
 * @Date: 2019-10-20
 * @Time: 19:09:57
 * @Description:
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate template;

    public void send() {
        String context = "hi,fanout msg ";
        System.out.println("Sender : " + context);
        this.template.convertAndSend("fanoutExchange", "", context);
    }
}
