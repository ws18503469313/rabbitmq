package com.mq.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 
 * 满足一个生产者多个消费者,
 * 生产者生产消息代价低,消费者消费消息慢
 * 
 * 任务轮询,平均分配
 * @author polunzi
 *
 */
public class Send {
	
	private static final String QUEUE_NAME = "work_mq_queue";
	
	
//	private static final Logger log = LoggerFactory.getLogger(Send.class);
	
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		//创建队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for (int i = 0; i < 50; i++) {
			String msg = "hello word queue"+i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(20);
		}
		
//		log.info("publish");
		channel.close();
		
		connection.close();
		
		
	}
}
