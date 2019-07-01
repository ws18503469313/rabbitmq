package com.mq.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

public class ConfirmAsync {

	
	private static final String QUEUE_NAME = "test_queue_confirm_normal";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		channel.confirmSelect();
		
		final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
		
		channel.addConfirmListener(new ConfirmListener() {
			
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					System.out.println("handelNack----------multiple");
					confirmSet.headSet(deliveryTag + 1).clear();;
				}else {
					System.out.println("handelNack----------multiple-----false");
					confirmSet.remove(deliveryTag);
				}
			}
			
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) {
					System.out.println("handelNack----------multiple");
					confirmSet.headSet(deliveryTag + 1).clear();
				}else {
					System.out.println("handelNack----------multiple-----false");
					confirmSet.remove(deliveryTag);
				}
			}
		});
		
		String msg = "hello async confirm msg";
		
		while(true) {
			long seqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			confirmSet.add(seqNo);
			
		}
	}
}
