package com.strady.rabbit.normal.Routing;

import com.rabbitmq.client.BuiltinExchangeType;
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
public class Routing {

    private static final String QUEUE_INFORM_EMAIL = "fanout.A";
    private static final String QUEUE_INFORM_SMS = "fanout.B";
    private static final String ROUTING_KEY_EMAIL = "inform_email";
    private static final String ROUTING_KEY_SMS = "inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchangeRouting";

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
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //队列绑定交换机
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, "inform");
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, "inform");
            //发送消息
//            for (int i = 0; i < 5; i++) {
//                String message = "this is a inform for Email!";
//                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL, null, message.getBytes());
//                System.out.println("send inform for Email: " + message);
//            }
//            for (int i = 0; i < 5; i++) {
//                String message = "this is a inform for Sms!";
//                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS, null, message.getBytes());
//                System.out.println("send inform for Sms: " + message);
//            }
            for (int i = 0; i < 5; i++) {
                String message = "this is a inform for inform!";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "inform", null, message.getBytes());
                System.out.println("send inform for inform: " + message);
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
