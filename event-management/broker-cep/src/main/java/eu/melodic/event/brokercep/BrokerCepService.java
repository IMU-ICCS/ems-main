/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep;

//import eu.melodic.event.brokercep.broker.BrokerAdvisoryWatcher;
import eu.melodic.event.brokercep.cep.CepService;
import eu.melodic.event.brokercep.cep.FunctionDefinition;
import eu.melodic.event.brokercep.event.EventMap;
//import eu.melodic.event.brokercep.event.MetricEvent;
import eu.melodic.event.brokercep.properties.BrokerCepProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.management.ObjectName;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.DestinationInfo;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrokerCepService {
	@Autowired
	private BrokerCepProperties properties;
	@Autowired
	private BrokerService brokerService;	// Added in order to ensure that BrokerService will be instantiated first
	@Autowired
	private ActiveMQConnectionFactory connectionFactory;
	//@Autowired
	//private BrokerAdvisoryWatcher advisoryMessageWatcher;
	@Getter
	@Autowired
	private BrokerCepConsumer brokerCepBridge;
	@Getter
	@Autowired
	private CepService cepService;
	
	public BrokerCepProperties getBrokerCepProperties() { return properties; }
	
	public synchronized void clearState() {
//XXX:DEL: ...next line
		log.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("BrokerCepService.clearState(): Clearing Broker-CEP state...");
		
		cepService.clearStatements();
		cepService.clearEventTypes();
		cepService.clearConstants();
		cepService.clearFunctionDefinitions();
		
		try {
			BrokerView bv = brokerService.getAdminView();
			ObjectName[] queues = bv.getQueues();
			ObjectName[] queueSubscribers = bv.getQueueSubscribers();
			ObjectName[] topics = bv.getTopics();
			ObjectName[] topicSubscribers = bv.getTopicSubscribers();
			for (ObjectName q : queues) {
				String name = q.getCanonicalName();
				bv.removeQueue(name);
				log.info("BrokerCepService.clearState(): Queue removed: {}", name);
			}
			for (ObjectName t : topics) {
				String name = t.getCanonicalName();
				bv.removeQueue(name);
				log.info("BrokerCepService.clearState(): Topic removed: {}", name);
			}
			
			log.info("BrokerCepService.clearState(): Broker-CEP state cleared");
		} catch (Exception ex) {
			log.error("BrokerCepService.clearState(): Failed to clear Broker state: ", ex);
		}
	}
	
	public synchronized void addEventTypes(Set<String> eventTypeNames, String[] eventPropertyNames, Class[] eventPropertyTypes) {
		log.info("BrokerCepService.addEventTypes(): Adding event types: {}", eventTypeNames);
		eventTypeNames.stream().forEach(name -> addEventType(name, eventPropertyNames, eventPropertyTypes));
		log.info("BrokerCepService.addEventTypes(): Adding event types: ok");
	}
	
	public synchronized void addEventTypes(Set<String> eventTypeNames, Class eventType) {
		log.info("BrokerCepService.addEventTypes(): Adding event types: {}", eventTypeNames);
		eventTypeNames.stream().forEach(name -> addEventType(name, eventType));
		log.info("BrokerCepService.addEventTypes(): Adding event types: ok");
	}
	
	public synchronized void addEventType(String eventTypeName, String[] eventPropertyNames, Class[] eventPropertyTypes) {
		// Add a new queue/topic in ActiveMQ (broker) named after 'eventTypeName'
		//brokerCepBridge.addQueue(eventTypeName);
		brokerCepBridge.addTopic(eventTypeName);
		
		// Register a new event type in Esper (cep engine)
		cepService.addEventType(eventTypeName, eventPropertyNames, eventPropertyTypes);
		log.info("BrokerCepService.addEventType(): New event type registered: {}", eventTypeName);
	}
	
	public synchronized void addEventType(String eventTypeName, Class eventType) {
		// Add a new queue/topic in ActiveMQ (broker) named after 'eventTypeName'
		//brokerCepBridge.addQueue(eventTypeName);
		brokerCepBridge.addTopic(eventTypeName);
		
		// Register a new event type in Esper (cep engine)
		cepService.addEventType(eventTypeName, eventType);
		log.info("BrokerCepService.addEventType(): New event type registered: {}", eventTypeName);
	}
	
	public void setConstant(String constName, double constValue) {
		log.debug("BrokerCepService.setConstant(): Add/Set constant: name={}, value={}", constName, constValue);
		cepService.setConstant(constName, constValue);
	}
	
	public void setConstants(Map<String,Double> constants) {
		log.debug("BrokerCepService.setConstants(): Add/Set constants in map: {}", constants);
		cepService.setConstants(constants);
	}
	
	public void addFunctionDefinitions(Set<FunctionDefinition> definitions) {
		log.info("BrokerCepService.addFunctionDefinitions(): Adding function definitions: {}", definitions);
		definitions.stream().forEach(def -> addFunctionDefinition(def));
		log.info("BrokerCepService.addFunctionDefinitions(): Adding function definitions: ok");
	}
	
	public void addFunctionDefinition(FunctionDefinition definition) {
		log.info("BrokerCepService.addFunction(): New function definition registered: {}", definition);
		cepService.addFunctionDefinition(definition);
	}
	
	public synchronized void publishEvent(String connectionString, String destinationName, Map<String,Object> eventMap) throws JMSException {
		_publishEvent(connectionString, destinationName, new EventMap(eventMap));
	}
	
	/*public synchronized void publishEvent(String connectionString, String destinationName, MetricEvent event) throws JMSException {
		_publishEvent(connectionString, destinationName, event);
	}*/
	
//XXX:TODO: Optimize this method
	protected synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
		// Create a ConnectionFactory
		if (connectionString==null) connectionString = properties.getBrokerUrl();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory( connectionString );

		// Create a Connection
		Connection connection = connectionFactory.createConnection();
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
		log.info("BrokerCepService.publishEvent(): Sending message: connection={}, destination={}, hash={}, payload={}", connectionString, destinationName, hash, event);
		producer.send(message);
		log.info("BrokerCepService.publishEvent(): Message sent: connection={}, destination={}, hash={}, payload={}", connectionString, destinationName, hash, event);

		// Clean up
		session.close();
		connection.close();
	}
}