package com.mq.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

	public static Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		
		factory.setPort(5672);
		
		factory.setVirtualHost("/mq_test");
		
		factory.setUsername("polunzi");
		
		factory.setPassword("wsa1583505");
		
		
		return factory.newConnection();
	}
}
