package com.mq.topic;

import java.io.IOException;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Rcv2 {

	private static final String EXCHANGE_NAME = "publish_queue_topic";
	private static final String QUEUE_NAME = "topic_queue_consumer2";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "good.#");
		channel.basicQos(1);
		Consumer consumer = new DefaultConsumer(channel) {
		
			@Override
			 public void handleDelivery(String consumerTag,
                     Envelope envelope,
                     AMQP.BasicProperties properties,
                     byte[] body)throws IOException{
				System.out.println("[2]:"+new String(body));
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
		
		boolean autoACK = false;
		channel.basicConsume(QUEUE_NAME, autoACK, consumer);
		
		
	}
}
