package com.strady.rabbit.normal.header;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: strady
 * @Date: 2019-10-22
 * @Time: 09:18:57
 * @Description:
 */
public class Header {

    private static final String QUEUE_INFORM_EMAIL = "fanout.A";
    private static final String QUEUE_INFORM_SMS = "fanout.B";
    private static final String EXCHANGE_FANOUT_INFORM = "headerExchange";

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
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.HEADERS);
            //队列绑定交换机
            Map<String, Object> header_email = new Hashtable<>();
            header_email.put("header_email", "email");
//            Map<String, Object> header_sms = new Hashtable<>();
//            header_email.put("header_sms", "sms");
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_FANOUT_INFORM, "", header_email);
//            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_FANOUT_INFORM, "", header_sms);
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.headers(header_email);
            //发送消息
            for (int i = 0; i < 10; i++) {
                String message = "this is a inform by header!";
                channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", builder.build(), message.getBytes());
                System.out.println("send inform: " + message);
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
