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
import eu.passage.upperware.commons.passwords.PasswordEncoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.management.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

//import eu.melodic.event.brokercep.event.MetricEvent;

@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Service
@Slf4j
public class BrokerCepService {
    private BrokerCepProperties properties;
    private BrokerConfig brokerConfig;
    private BrokerService brokerService;
    private ActiveMQConnectionFactory connectionFactory;
    private PasswordEncoder passwordEncoder;

    //private BrokerAdvisoryWatcher advisoryMessageWatcher;
    @Getter
    private BrokerCepConsumer brokerCepBridge;
    @Getter
    private CepService cepService;

    public BrokerCepProperties getBrokerCepProperties() {
        return properties;
    }

    public synchronized void clearState() {
        log.info("BrokerCepService.clearState(): Clearing Broker-CEP state...");

        // Clear CEP service state
        cepService.clearStatements();
        cepService.clearEventTypes();
        cepService.clearConstants();
        cepService.clearFunctionDefinitions();

        // Clear Broker service state
        try {
            BrokerView bv = brokerService.getAdminView();
            ObjectName[] queues = bv.getQueues();
            //ObjectName[] queueSubscribers = bv.getQueueSubscribers();
            ObjectName[] topics = bv.getTopics();
            //ObjectName[] topicSubscribers = bv.getTopicSubscribers();
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

            //XXX: remove tests
            /*ConnectionContext cc = brokerService.getAdminConnectionContext();
            ActiveMQDestination dest[] = brokerService.getRegionBroker().getDestinations();
            long removeDelay = 1;
            for (ActiveMQDestination d : dest) {
                if (d.getQualifiedName().indexOf("://ActiveMQ.")<0) {
                    String name = d.getDestinationTypeAsString() + " " + d.getQualifiedName();
                    brokerService.getRegionBroker().removeDestination(null, d, removeDelay);
                    log.info("BrokerCepService.clearState(): Destination removed: {}", name);
                }
            }*/

            //XXX: remove tests
            /*log.warn(">>>>>>>>>>> MBeans: {}", brokerService.getManagementContext().getMBeanServer().queryMBeans(null, null));*/
            String topicMBeanNames = "org.apache.activemq:type=Broker,brokerName="+properties.getBrokerName()
                            +",destinationType=Topic,destinationName=*";
            Set<ObjectInstance> instances = brokerService.getManagementContext()
                    .getMBeanServer().queryMBeans(new ObjectName(topicMBeanNames), null);
            /*log.warn(">>>>>>>>>>> TopicViews: {}", instances);*/
            /*for (ObjectInstance oi: instances) {
                log.warn("---->  oi: {} -> {} -> {}", oi.getObjectName(), oi.getClassName(), oi.getObjectName().getKeyProperty("destinationName"));
            }*/

            ObjectName brokerNameQuery =
                    new ObjectName("org.apache.activemq:type=Broker,brokerName="+properties.getBrokerName());
            instances.stream()
                    .map(ObjectInstance::getObjectName)
                    .map(objName -> objName.getKeyProperty("destinationName"))
                    .filter(name -> ! name.startsWith("ActiveMQ."))
                    .peek(topicName -> log.warn("---->  {}", topicName))
                    .forEach(topicName -> {
                        try {
                            brokerService.getManagementContext().getMBeanServer()
                                    .invoke(brokerNameQuery,
                                            "removeTopic",
                                            new String[]{topicName},
                                            new String[]{"java.lang.String"});
                        } catch (Exception e) {
                            log.error("Exception while deleting topic: {} -> {}", topicName, e);
                        }
                    });

            log.info("BrokerCepService.clearState(): Broker-CEP state cleared");
        } catch (Exception ex) {
            log.error("BrokerCepService.clearState(): Failed to clear Broker state: ", ex);
        }
    }

    public synchronized void addEventTypes(Set<String> eventTypeNames, String[] eventPropertyNames, Class[] eventPropertyTypes) {
        log.info("BrokerCepService.addEventTypes(): Adding event types: {}", eventTypeNames);
        eventTypeNames.forEach(name -> addEventType(name, eventPropertyNames, eventPropertyTypes));
        log.info("BrokerCepService.addEventTypes(): Adding event types: ok");
    }

    public synchronized void addEventTypes(Set<String> eventTypeNames, Class eventType) {
        log.info("BrokerCepService.addEventTypes(): Adding event types: {}", eventTypeNames);
        eventTypeNames.forEach(name -> addEventType(name, eventType));
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
        definitions.forEach(def -> addFunctionDefinition(def));
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
	
    public synchronized void publishEvent(String connectionString, String username, String password, String destinationName, Map<String, Object> eventMap) throws JMSException {
        if (properties.isBypassLocalBroker() && _publishLocalEvent(connectionString, destinationName, new EventMap(eventMap)))
            return;
        _publishEvent(connectionString, username, password, destinationName, new EventMap(eventMap));
    }

	/*public synchronized void publishEvent(String connectionString, String destinationName, MetricEvent event) throws JMSException {
		if (properties.isBypassLocalBroker() && _publishLocalEvent(connectionString, destinationName, event)) return;
		_publishEvent(connectionString, destinationName, event);
	}*/

    // When destination is the local broker then hand event to (local) CEP engine, bypassing local broker
    private final static java.util.regex.Pattern urlPattern = java.util.regex.Pattern.compile("^([a-z]+://[a-zA-Z0-9_\\.\\-]+:[0-9]+)([/#\\?].*)?$");

    private synchronized boolean _publishLocalEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
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

    private synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
        // Get username/password for local broker service
        String username = null;
        String password = null;
        if (connectionString==null || brokerConfig.getBrokerUrl().equals(connectionString)) {
            username = brokerConfig.getBrokerLocalAdminUsername();
            password = brokerConfig.getBrokerLocalAdminPassword();
        }
        _publishEvent(connectionString, username, password, destinationName, event);
    }

    private synchronized void _publishEvent(String connectionString, String username, String password, String destinationName, Serializable event) throws JMSException {
        // Clone connection factory
        if (connectionString == null) connectionString = properties.getBrokerUrl();
        ActiveMQConnectionFactory connectionFactory = this.connectionFactory.copy();
        connectionFactory.setBrokerURL(connectionString);

        // Create a Connection
        log.debug("BrokerCepService._publishEvent(): Connection info: conn-string={}, username={}, password={}", connectionString, username, passwordEncoder.encode(password));
        Connection connection = StringUtils.isBlank(username)
                ? connectionFactory.createConnection()
                : connectionFactory.createConnection(username, password);
        connection.start();

        // Publish event
        _publishEvent(connection, destinationName, event);

        // Clean up
        connection.close();
    }

    private synchronized void _publishEvent(Connection connection, String destinationName, Serializable event) throws JMSException {
        log.debug("BrokerCepService._publishEvent(): Connection given: {}", connection);

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Publish event
        _publishEvent(session, destinationName, event);

        // Clean up
        session.close();
    }

    private synchronized void _publishEvent(Session session, String destinationName, Serializable event) throws JMSException {
        log.debug("BrokerCepService._publishEvent(): Session: {}", session);

        // Create the destination (Topic or Queue)
        log.debug("BrokerCepService._publishEvent(): Destination info: name={}", destinationName);
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
        //log.info("BrokerCepService.publishEvent(): Sending message: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, username, destinationName, hash, event);
        log.info("BrokerCepService.publishEvent(): Sending message: destination={}, hash={}, payload={}", destinationName, hash, event);
        producer.send(message);
        //log.info("BrokerCepService.publishEvent(): Message sent: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, username, destinationName, hash, event);
        log.info("BrokerCepService.publishEvent(): Message sent: destination={}, hash={}, payload={}", destinationName, hash, event);
    }

    public void setBrokerCredentials(String u, String p) {
        brokerConfig.setBrokerUsername(u);
        brokerConfig.setBrokerPassword(p);
        log.info("BrokerCepService.setBrokerCredentials(): Broker credentials set: username={}, password={}", u, passwordEncoder.encode(p));
    }

    public String getBrokerUsername() {
        return brokerConfig.getBrokerLocalUserUsername();
    }

    public String getBrokerPassword() {
        return brokerConfig.getBrokerLocalUserPassword();
    }
}