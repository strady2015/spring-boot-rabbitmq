package com.strady.rabbit.normal.publish_subscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author: strady
 * @Date: 2019-10-22
 * @Time: 10:42:23
 * @Description:
 */
public class Subscribe_Sms {

    private static final String QUEUE_INFORM_Sms = "fanout.B";
    private static final String EXCHANGE_FANOUT_INFORM = "fanoutExchange";

    public static void main(String[] args) throws Exception {
        //创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_INFORM_Sms, true, false, false, null);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
        //队列绑定交换机
        channel.queueBind(QUEUE_INFORM_Sms, EXCHANGE_FANOUT_INFORM, "");
        //实现消费方法
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag 消费者标签
             * @param envelope 信封
             * @param properties 消息属性
             * @param body 消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //消息id，mq在channel中标识消息的id，用于确认消息已接受
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String message = new String(body, "utf-8");
                System.out.println("Receive Sms inform: " + message);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_INFORM_Sms, true, consumer);
    }
}
