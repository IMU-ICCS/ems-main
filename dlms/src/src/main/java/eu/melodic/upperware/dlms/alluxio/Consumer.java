/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.dlms.alluxio;

//This file is to subscribe the ACTIVEMQ agents 

import java.io.IOException;

//
//Java to subscribe to the relevant topic and read the event payload:

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	String DEFAULT_BROKER_URL = url; // URL of the JMS server 147.102.17.107
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	// Name of the topic from which we will receive messages from = "MyLocalTopic"

	public static void main(String[] args) throws JMSException {
		// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// here we use sessions not transactions
		Topic topic = session.createTopic("MyLocalTopic");
		MessageConsumer consumer = session.createConsumer(topic);
		MessageListener listner = new MessageListener() {

			public void onMessage(Message message) {
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						System.out.println("Received message" + textMessage.getText() + "'");
					}
				} catch (JMSException e) {
					System.out.println("Caught:" + e);
					e.printStackTrace();
				}
			}
		};

		consumer.setMessageListener(listner);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// the connection will close after we give a click from our keyboard
		connection.close();

	};
}
