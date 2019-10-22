package com.strady.rabbit.springboot.many;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 22:40:01
 * @Description:
 */
@Component
public class NeoSender1 {

    @Autowired
    private AmqpTemplate template;

    public void send(int i){
        String context = "spirng boot neo queue"+" ****** "+i;
//        System.out.println("Sender1 : " + context);
        this.template.convertAndSend("neo", context);
    }
}
