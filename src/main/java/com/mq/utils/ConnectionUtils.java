package com.mq.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

	public static Connection getConnection() throws IOException, TimeoutException {
		InputStream in = new FileInputStream("queue_seting.properties");
		Properties properties = new Properties();
		properties.load(in);
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(properties.getProperty("host"));
		
		factory.setPort(5672);
		
		factory.setVirtualHost(properties.getProperty("virtualhost"));
		
		factory.setUsername(properties.getProperty("username"));
		
		factory.setPassword(properties.getProperty("password"));
		
		
		return factory.newConnection();
	}
}
