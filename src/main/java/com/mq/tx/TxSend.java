package com.mq.tx;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 这种模式降低吞吐量
 * @author polunzi
 *
 */
public class TxSend {

	private static final String QUEUE_NAME = "test_queue_tx";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		String msg = "hello tx message";
		
		try {
			channel.txSelect();
			int x = 1/0;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			
			channel.txCommit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			channel.txRollback();
			System.out.println("send msg rollback");
		}
		
		channel.close();
		
		connection.close();
	}
}
