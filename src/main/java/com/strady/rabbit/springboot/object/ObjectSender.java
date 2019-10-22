package com.strady.rabbit.springboot.object;

import com.strady.rabbit.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 23:28:43
 * @Description:
 */
@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate template;

    public void send(User user) {
        System.out.println("Send object:" + user.toString());
        this.template.convertAndSend("object", user);
    }
}
