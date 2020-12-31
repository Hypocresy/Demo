package com.expame;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
public class App {
    public  static  String EXCHANGE_NAME = "fanout_exchange";
    public static void main( String[] args ) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.105.54.245");
        factory.setPassword("123456");
        factory.setUsername("root");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        for(int i =0;i<100;i++){
             String message = "fanout 消息 "+i;
             channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes("UTF-8"));
        }
        channel.close();
        connection.close();
    }
}
