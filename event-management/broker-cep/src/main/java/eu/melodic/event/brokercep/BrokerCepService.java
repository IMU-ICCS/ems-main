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

import eu.melodic.event.brokercep.broker.BrokerConfig;
import eu.melodic.event.brokercep.cep.CepService;
import eu.melodic.event.brokercep.cep.FunctionDefinition;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.brokercep.properties.BrokerCepProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.management.ObjectName;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

//import eu.melodic.event.brokercep.event.MetricEvent;

@Service
@Slf4j
public class BrokerCepService {
    @Autowired
    private BrokerCepProperties properties;
    @Autowired
    private BrokerConfig brokerConfig;
    @Autowired
    private BrokerService brokerService;    // Added in order to ensure that BrokerService will be instantiated first
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

    public BrokerCepProperties getBrokerCepProperties() {
        return properties;
    }

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

    public void setConstants(Map<String, Double> constants) {
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

    public synchronized void publishEvent(String connectionString, String destinationName, Map<String, Object> eventMap) throws JMSException {
        if (properties.isBypassLocalBroker() && _publishLocalEvent(connectionString, destinationName, new EventMap(eventMap)))
            return;
        _publishEvent(connectionString, destinationName, new EventMap(eventMap));
    }
	
	/*public synchronized void publishEvent(String connectionString, String destinationName, MetricEvent event) throws JMSException {
		if (properties.isBypassLocalBroker() && _publishLocalEvent(connectionString, destinationName, event)) return;
		_publishEvent(connectionString, destinationName, event);
	}*/

    // When destination is the local broker then hand event to (local) CEP engine, bypassing local broker
    private final static java.util.regex.Pattern urlPattern = java.util.regex.Pattern.compile("^([a-z]+://[a-zA-Z0-9_\\.\\-]+:[0-9]+)([/#\\?].*)?$");

    protected synchronized boolean _publishLocalEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
        java.util.regex.Matcher matcher = urlPattern.matcher(connectionString);
        String connBrokerUrl = matcher.matches() ? matcher.group(1) : connectionString;
        log.debug("BrokerCepService._publishLocalEvent(): Check if event is published to the local broker: local-broker-url={}, connection-broker-url={}, connection={}, destination={}, payload={}",
                properties.getBrokerUrl(), connBrokerUrl, connectionString, destinationName, event);
        if (!connBrokerUrl.equals(properties.getBrokerUrl())) return false;

        Class eventClass = event.getClass();
        log.debug("BrokerCepService._publishLocalEvent(): It is local event. Skipping publish through broker: connection={}, destination={}, payload-class={}, payload={}",
                connectionString, destinationName, eventClass.getName(), event);
        if (String.class.isAssignableFrom(eventClass)) {
            log.debug("BrokerCepService._publishLocalEvent(): String event...");
            cepService.handleEvent((String) event, destinationName);
        } else if (Map.class.isAssignableFrom(eventClass)) {
            log.debug("BrokerCepService._publishLocalEvent(): Map event...");
            cepService.handleEvent((Map) event, destinationName);
        } else {
            log.debug("BrokerCepService._publishLocalEvent(): Object event...");
            cepService.handleEvent(event);
        }
        return true;
    }

    //XXX:TODO: Optimize this method
    protected synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
        // Clone connection factory
        if (connectionString == null) connectionString = properties.getBrokerUrl();
        ActiveMQConnectionFactory connectionFactory = this.connectionFactory.copy();
        connectionFactory.setBrokerURL(connectionString);

        // Create a Connection
        Connection connection = (brokerConfig.getBrokerUsername() == null)
                ? connectionFactory.createConnection()
                : connectionFactory.createConnection(brokerConfig.getBrokerUsername(), brokerConfig.getBrokerPassword());
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        //Destination destination = session.createQueue( destinationName );
        Destination destination = session.createTopic(destinationName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(javax.jms.DeliveryMode.NON_PERSISTENT);

        // Create a messages
        //ObjectMessage message = session.createObjectMessage(event);
        TextMessage message = session.createTextMessage(event.toString());

        // Tell the producer to send the message
        long hash = message.hashCode();
        log.info("BrokerCepService.publishEvent(): Sending message: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, getBrokerUsername(), destinationName, hash, event);
        producer.send(message);
        log.info("BrokerCepService.publishEvent(): Message sent: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, getBrokerUsername(), destinationName, hash, event);

        // Clean up
        session.close();
        connection.close();
    }

    public void setBrokerCredentials(String u, String p) {
        brokerConfig.setBrokerUsername(u);
        brokerConfig.setBrokerPassword(p);
        log.info("BrokerCepService.setBrokerCredentials(): Broker credentials set: username={}, password=****", u);
    }

    public String getBrokerUsername() {
        return brokerConfig.getBrokerUsername();
    }

    public String getBrokerPassword() {
        return brokerConfig.getBrokerPassword();
    }
}