package com.mq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Client {

	
	private static final String QUEUE_NAME = "simple_mq_queue";
	
	
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                System.out.println("消费者获取到的消息是=="+new String(body));
            }

		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
	}
}
