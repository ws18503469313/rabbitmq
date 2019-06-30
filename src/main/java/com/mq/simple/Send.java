package com.mq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 简单队列,无法满足多个消费者
 * 耦合性高
 * (队列名)
 * @author polunzi
 *
 */
public class Send {
	
	private static final String QUEUE_NAME = "simple_mq_queue";
	
	
//	private static final Logger log = LoggerFactory.getLogger(Send.class);
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		//创建队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String msg = "hello simple queue";
		
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//		log.info("publish");
		channel.close();
		
		connection.close();
		
		
	}
}
