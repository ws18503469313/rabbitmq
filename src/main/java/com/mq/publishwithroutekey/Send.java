package com.mq.publishwithroutekey;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 一个消息到达多个消费者.
 * 
 * 交换机=====exchange
 * 	接受生产者的消息
 * 	发送给订阅了exchange的消费者
 * 	
 * 
 * redirect ----路由必须匹配
 * @author polunzi
 *
 */
public class Send {
	/**
	 * 交换机没有存储能力,消息丢失,所以交换机必须绑定队列.
	 * rabbit只有队列有存储能力
	 */
	private static final String EXCHANGE_NAME = "publish_queue_direct";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtils.getConnection();
		
		Channel channel = connection.createChannel();
		//fanout====不处理路由键,接收便发布,不需要路由key,即为""
		//direct====处理路由键
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String msg = "hello direct ex";
		
		String routingKey = "warning";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
		
		System.out.println("send");
		channel.close();
		
		connection.close();
	}
}
