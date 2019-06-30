package com.mq.balancequeue;

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
	
	private static final String QUEUE_NAME = "work_mq_queue_balance";
	public static void main(String[] args) throws Exception {
		
		Connection connection = ConnectionUtils.getConnection();
		
		final Channel channel = connection.createChannel();
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		Consumer consumer =  new DefaultConsumer(channel) {
			@Override
		    public void handleDelivery(String consumerTag,
		                               Envelope envelope,
		                               AMQP.BasicProperties properties,
		                               byte[] body) throws IOException{
				System.out.println("[1]"+new String(body, "utf-8"));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					System.out.println("[1] done");
					//手动回复消费完成
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
		    }
		};
		
		
		boolean autoCB = false;
		
		channel.basicConsume(QUEUE_NAME, autoCB, consumer);
		
		
		
	}
}
