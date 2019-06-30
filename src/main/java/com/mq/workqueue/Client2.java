package com.mq.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Client2 {
	
	private static final String QUEUE_NAME = "work_mq_queue";
	public static void main(String[] args) throws Exception {
		
		Connection connection = ConnectionUtils.getConnection();
		
		final Channel channel = connection.createChannel();
		//创建队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		Consumer consumer =  new DefaultConsumer(channel) {
			@Override
		    public void handleDelivery(String consumerTag,
		                               Envelope envelope,
		                               AMQP.BasicProperties properties,
		                               byte[] body) throws IOException{
				System.out.println("[2]"+new String(body, "utf-8"));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					System.out.println("[2] done");
				}
		    }
		};
		
		
		boolean autoCB = true;
		
		channel.basicConsume(QUEUE_NAME, autoCB, consumer);
		
		
		
	}
}
