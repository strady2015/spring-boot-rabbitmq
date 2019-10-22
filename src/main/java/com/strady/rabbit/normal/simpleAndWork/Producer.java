package com.strady.rabbit.normal.simpleAndWork;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: strady
 * @Date: 2019-10-22
 * @Time: 09:18:57
 * @Description:
 */
public class Producer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        //创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建通道
            channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //发送消息
            String message = "this is a message!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("send message: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
