package com.strady.rabbit.normal.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: strady
 * @Date: 2019-10-22
 * @Time: 13:59:54
 * @Description:
 */
public class Topic {

    private static final String QUEUE_INFORM_EMAIL = "fanout.A";
    private static final String QUEUE_INFORM_SMS = "fanout.B";
    private static final String ROUTING_KEY_EMAIL = "inform.#.email.#";
    private static final String ROUTING_KEY_SMS = "inform.#.sms.#";
    private static final String EXCHANGE_TOPIC_INFORM = "exchangeTopic";

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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_TOPIC_INFORM, BuiltinExchangeType.TOPIC);
            //队列绑定交换机
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPIC_INFORM, ROUTING_KEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_TOPIC_INFORM, ROUTING_KEY_SMS);
            //发送消息
            for (int i = 0; i < 5; i++) {
                String message = "this is a inform for Email!";
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email", null, message.getBytes());
                System.out.println("send inform for Email: " + message);
            }
            for (int i = 0; i < 5; i++) {
                String message = "this is a inform for Sms!";
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.sms", null, message.getBytes());
                System.out.println("send inform for Sms: " + message);
            }
            for (int i = 0; i < 5; i++) {
                String message = "this is a inform for Email&Sms!";
                channel.basicPublish(EXCHANGE_TOPIC_INFORM, "inform.email.sms", null, message.getBytes());
                System.out.println("send inform for Email&Sms: " + message);
            }
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
