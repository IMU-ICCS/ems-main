/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerutil;

import eu.melodic.event.brokerutil.BrokerConfig;
import eu.melodic.event.brokerutil.event.EventMap;
import eu.melodic.event.brokerutil.properties.BrokerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;
import java.util.Map;

@Service
@Slf4j
public class BrokerUtil {
	@Autowired
	private BrokerProperties properties;
	@Autowired
	private BrokerConfig brokerConfig;
	@Autowired
	private ActiveMQConnectionFactory connectionFactory;

	public BrokerProperties getBrokerProperties() { return properties; }
	
	public synchronized void publishEvent(String connectionString, String destinationName, Map<String,Object> eventMap) throws JMSException {
		_publishEvent(connectionString, destinationName, new EventMap(eventMap));
	}
	
	protected synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
		// Clone connection factory
		if (connectionString==null) connectionString = properties.getBrokerUrl();
		ActiveMQConnectionFactory connectionFactory = this.connectionFactory.copy();
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
		log.info("BrokerService.publishEvent(): Sending message: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);
		producer.send(message);
		log.info("BrokerService.publishEvent(): Message sent: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);
		
		// Clean up
		session.close();
		connection.close();
	}
}