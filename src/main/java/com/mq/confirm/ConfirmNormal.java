package com.mq.confirm;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ConfirmNormal {
	
	private static final String QUEUE_NAME = "test_queue_confirm_normal";
	public static void main(String[] args)throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//将chanle设为confirm模式
		channel.confirmSelect();
		
		String msg = "hello confirm message";
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		
		if(!channel.waitForConfirms()) {
			System.out.println("message send failed");
		}else {
			System.out.println("message send ");
		}
		
			
		
		channel.close();
		
		connection.close();
		
	}
}
