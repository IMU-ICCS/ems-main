/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerclient;
 
import javax.jms.*;

import eu.melodic.event.brokerclient.event.EventMap;
import eu.melodic.event.brokerclient.properties.BrokerClientProperties;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;

@Slf4j
public class BrokerClient {

	@Autowired
	private BrokerClientProperties properties;

	public static void main(String args[]) throws JMSException {
		log.info("BrokerClient: Test");
		final PropertySourcesPlaceholderConfigurer theConfigurer = new PropertySourcesPlaceholderConfigurer();
		theConfigurer.setLocation(new UrlResource("eu.melodic.event.brokercep.properties"));
		
		BrokerClient client = new BrokerClient();
		log.info("BrokerClient: Configuration:\n{}", client.properties);
		//client.listenToTopic(args);
	}
	
	public BrokerClient() { properties = new BrokerClientProperties(); }	//use defaults
	
	public BrokerClient(BrokerClientProperties bcp) { properties = bcp; }
	
	public BrokerClient(Properties p) { properties = new BrokerClientProperties(p); }
	
	/*public void listenToTopic(String args[]) throws JMSException {
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
			ActiveMQConnectionFactory connectionFactory = createConnectionFactory();
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
	}*/

	public synchronized void publishEvent(String connectionString, String destinationName, Map<String,Object> eventMap) throws JMSException {
		_publishEvent(connectionString, destinationName, new EventMap(eventMap));
	}

	protected synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
		// Clone connection factory
		if (connectionString==null) connectionString = properties.getBrokerUrl();
		ActiveMQConnectionFactory connectionFactory = createConnectionFactory();
		connectionFactory.setBrokerURL(connectionString);

		// Create a Connection
		Connection connection = (properties.getBrokerUsername()==null)
				? connectionFactory.createConnection()
				: connectionFactory.createConnection(properties.getBrokerUsername(), properties.getBrokerPassword());
		connection.start();

		// Create a Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the destination (Topic or Queue)
		//Destination destination = session.createQueue( destinationName );
		Destination destination = session.createTopic( destinationName );

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(javax.jms.DeliveryMode.NON_PERSISTENT);

		// Create a messages
		//ObjectMessage message = session.createObjectMessage(event);
		TextMessage message = session.createTextMessage(event.toString());

		// Tell the producer to send the message
		long hash = message.hashCode();
		log.info("BrokerClient.publishEvent(): Sending message: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);
		producer.send(message);
		log.info("BrokerClient.publishEvent(): Message sent: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);

		// Clean up
		session.close();
		connection.close();
	}
	
	// ------------------------------------------------------------------------
	
	public ActiveMQConnectionFactory createConnectionFactory() {
		// Create connection factory based on Broker URL scheme
		final ActiveMQConnectionFactory connectionFactory;
		String brokerUrl = properties.getBrokerUrl();
		if (brokerUrl.startsWith("ssl")) {
			log.info("BrokerClient.createConnectionFactory(): Creating new SSL connection factory instance: url={}", brokerUrl);
			final ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl);
			try {
				sslConnectionFactory.setTrustStore( properties.getTruststoreFile() );
				sslConnectionFactory.setTrustStoreType( properties.getTruststoreType() );
				sslConnectionFactory.setTrustStorePassword( properties.getTruststorePassword() );
				sslConnectionFactory.setKeyStore( properties.getKeystoreFile() );
				sslConnectionFactory.setKeyStoreType( properties.getKeystoreType() );
				sslConnectionFactory.setKeyStorePassword( properties.getKeystorePassword() );
				//sslConnectionFactory.setKeyStoreKeyPassword( properties........ );

				connectionFactory = sslConnectionFactory;
			} catch (final Exception theException) {
				throw new Error(theException);
			}
		} else {
			log.info("BrokerClient.createConnectionFactory(): Creating new non-SSL connection factory instance: url={}", brokerUrl);
			connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
		}
		
		// Other connection factory settings
		//connectionFactory.setSendTimeout(....5000L);
		//connectionFactory.setTrustedPackages(Arrays.asList("eu.melodic.event"));
		connectionFactory.setTrustAllPackages(true);
		connectionFactory.setWatchTopicAdvisories(true);

		return connectionFactory;
	}
}