package com.mq.publishwithroutekey;

import java.io.IOException;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv2 {

	private static final String EXCHANGE_NAME = "publish_queue_direct";
	private static final String QUEUE_NAME = "test_queue_route_sms";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		final Channel channel = connection.createChannel();
		//队列声明
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		
		//队列绑定到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");
		channel.basicQos(1);
		Consumer consumer =  new DefaultConsumer(channel) {
			@Override
		    public void handleDelivery(String consumerTag,
		                               Envelope envelope,
		                               AMQP.BasicProperties properties,
		                               byte[] body) throws IOException{
				System.out.println("[2]"+new String(body, "utf-8"));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					System.out.println("[2] done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
		    }
		};
		
		
		boolean autoCB = false;
		
		channel.basicConsume(QUEUE_NAME, autoCB, consumer);
	}
}
