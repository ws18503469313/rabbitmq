package com.mq.topic;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式
 * 将路由键和摸个模式匹配
 * '#' 匹配多个
 * '*' 匹配一个
 * @author polunzi
 *
 */
public class Send {

	private static final String EXCHANGE_NAME = "publish_queue_topic";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		String msg = "update商品...";
		
		channel.basicPublish(EXCHANGE_NAME, "good.delete", null, msg.getBytes());
		
		System.out.println("send");
		channel.close();
		connection.close();
	}
}
