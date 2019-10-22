package com.strady.rabbit.springboot.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 21:27:58
 * @Description:
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate template;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender:" + context);
        this.template.convertAndSend("hello", context);
    }
}
