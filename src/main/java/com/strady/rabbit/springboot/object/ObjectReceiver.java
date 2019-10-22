package com.strady.rabbit.springboot.object;

import com.strady.rabbit.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 23:35:00
 * @Description:
 */
@Component
@RabbitListener(queues = "object")
public class ObjectReceiver {

    @RabbitHandler
    public void process(User user) {
        System.out.println("Receiver object:" + user);
    }

}
