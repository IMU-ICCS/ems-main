/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.broker;
 
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;

@Slf4j
public class BrokerClient {
	
	public static void main(String args[]) throws JMSException {
		String connectionString = args[0].trim();
		String destinationName = args[1].trim();
		String username = args.length>2 ? args[2].trim() : null;
		String password = args.length>3 ? args[3] : null;
		
		if (username!=null && username.length()>0 && password==null) {
			password = new String( System.console().readPassword("Enter broker password: ") );
		}
		
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL( connectionString );
			if (username!=null && password!=null) {
				connectionFactory.setUserName(username);
				connectionFactory.setPassword(password);
			}
			connectionFactory.setTrustAllPackages(true);
			connectionFactory.setWatchTopicAdvisories(true);
			
			// Create a Connection
			log.info("Connecting to broker: {}...", connectionString);
			connection = connectionFactory.createConnection();
			connection.start();
			
			// Create a Session
			log.info("Opening session...");
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// Create the destination (Topic or Queue)
			log.info("Subscribing to destination: {}...", destinationName);
			//Destination destination = session.createQueue( destinationName );
			Destination destination = session.createTopic( destinationName );
			
			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(destination);
			
			// Wait for messages
			log.info("Waiting for messages...");
			while (true) {
				Message message = consumer.receive();
				if (message instanceof ObjectMessage) {
					ObjectMessage objMessage = (ObjectMessage) message;
					Object obj = objMessage.getObject();
					log.info(" - Received object message: {}", obj);
				} else
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					log.info(" - Received text message: {}", text);
				} else {
					log.info(" - Received message: {}", message);
				}
			}
			
		} finally {
			// Clean up
			log.info("Closing connection...");
			if (consumer!=null) consumer.close();
			if (session!=null) session.close();
			if (connection!=null) connection.close();
			log.info("Bye");
		}
	}
}