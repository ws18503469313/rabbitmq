package com.mq.balancequeue;

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
	
	private static final String QUEUE_NAME = "work_mq_queue_balance";
	
	
//	private static final Logger log = LoggerFactory.getLogger(Send.class);
	
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		//创建队列,持久化必须在第一次创建的时候设置
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//确认消费完成才再发送
		channel.basicQos(1);
		for (int i = 0; i < 50; i++) {
			String msg = "hello word queue"+i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(20*i);
		}
		
//		log.info("publish");
		channel.close();
		
		connection.close();
		
		
	}
}
