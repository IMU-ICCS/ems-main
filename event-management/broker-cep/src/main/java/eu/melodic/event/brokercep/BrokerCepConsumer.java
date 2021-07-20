/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep;

import eu.melodic.event.brokercep.broker.BrokerConfig;
import eu.melodic.event.brokercep.cep.CepService;
import eu.melodic.event.brokercep.properties.BrokerCepProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class BrokerCepConsumer implements MessageListener, InitializingBean {
    private static AtomicLong eventCounter = new AtomicLong(0);
    private static AtomicLong textEventCounter = new AtomicLong(0);
    private static AtomicLong objectEventCounter = new AtomicLong(0);
    private static AtomicLong otherEventCounter = new AtomicLong(0);
    private static AtomicLong eventFailuresCounter = new AtomicLong(0);

    @Autowired
    private BrokerCepProperties properties;
    @Autowired
    private BrokerConfig brokerConfig;
    @Autowired
    private BrokerService brokerService;    // Added in order to ensure that BrokerService will be instantiated first
    @Autowired
    private CepService cepService;

    private Connection connection;
    private Session session;
    private final Map<String,MessageConsumer> addedDestinations = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        initialize();
    }

    public synchronized void initialize() {
        log.debug("BrokerCepConsumer.initialize(): Initializing Broker-CEP consumer instance...");
        try {
            // close previous session and connection
            if (session!=null) {
                session.close();
                session = null;
                log.debug("BrokerCepConsumer.initialize(): Closed pre-existing sessions");
            }
            if (connection!=null) {
                connection.close();
                connection = null;
                log.debug("BrokerCepConsumer.initialize(): Closed pre-existing connection");
            }

            // If an alternative Broker URL is provided for consumer, it will be use
            ConnectionFactory connectionFactory;
            if (StringUtils.isNotBlank(properties.getBrokerUrlForConsumer())) {
                log.debug("BrokerCepConsumer.initialize(): Broker URL for Broker-CEP consumer instance: {}", properties.getBrokerUrlForConsumer());
                connectionFactory = brokerConfig.getConnectionFactoryFor(properties.getBrokerUrlForConsumer());
            } else {
                log.debug("BrokerCepConsumer.initialize(): Default broker URL will be used for Broker-CEP consumer instance: {}", brokerConfig.getBrokerUrl());
                connectionFactory = brokerConfig.getConnectionFactoryFor(null);
            }

            // Initialize connection
            connection = (brokerConfig.getBrokerLocalAdminUsername() != null)
                    ? connectionFactory.createConnection(brokerConfig.getBrokerLocalAdminUsername(), brokerConfig.getBrokerLocalAdminPassword())
                    : connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            log.debug("BrokerCepConsumer.initialize(): Initializing Broker-CEP consumer instance... done");
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.initialize(): EXCEPTION: ", ex);
        }
    }

    public synchronized void addQueue(String queueName) {
        log.debug("BrokerCepConsumer.addQueue(): Adding queue: {}", queueName);
        if (addedDestinations.containsKey(queueName)) {
            log.debug("BrokerCepConsumer.addQueue(): Queue already added: {}", queueName);
            return;
        }
        try {
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            addedDestinations.put(queueName, consumer);
            log.debug("BrokerCepConsumer.addQueue(): Added queue: {}", queueName);
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.addQueue(): EXCEPTION: ", ex);
        }
    }

    public synchronized void addTopic(String topicName) {
        log.debug("BrokerCepConsumer.addTopic(): Adding topic: {}", topicName);
        if (addedDestinations.containsKey(topicName)) {
            log.debug("BrokerCepConsumer.addTopic(): Topic already added: {}", topicName);
            return;
        }
        try {
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(this);
            addedDestinations.put(topicName, consumer);
            log.debug("BrokerCepConsumer.addTopic(): Added topic: {}", topicName);
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.addTopic(): EXCEPTION: ", ex);
        }
    }

    public synchronized void removeConsumerOf(String name) {
        log.debug("BrokerCepConsumer.removeConsumerOf(): Removing topic or queue: {}", name);
        if (!addedDestinations.containsKey(name)) {
            log.debug("BrokerCepConsumer.removeConsumerOf(): Topic/Queue not exists: {}", name);
            return;
        }
        try {
            MessageConsumer consumer = addedDestinations.remove(name);
            if (consumer!=null) consumer.close();
            log.debug("BrokerCepConsumer.removeConsumerOf(): Removed topic: {}", name);
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.removeConsumerOf(): EXCEPTION: ", ex);
        }
    }

    public boolean containsDestination(String name) {
        return addedDestinations.containsKey(name);
    }

    @Override
    public void onMessage(Message message) {
        try {
            log.trace("BrokerCepConsumer.onMessage(): {}", message);
            if (message instanceof ActiveMQObjectMessage) {
                ActiveMQObjectMessage mesg = (ActiveMQObjectMessage) message;
                ActiveMQDestination messageDestination = mesg.getDestination();
                log.debug("BrokerCepConsumer.onMessage(): Message received: source={}, payload={}",
                        messageDestination.getPhysicalName(), mesg.getObject());

                // Send message to Esper
                if (mesg.getObject() instanceof Map) {
                    cepService.handleEvent((Map<String, Object>) mesg.getObject(), messageDestination.getPhysicalName());
                } else {
                    cepService.handleEvent(mesg.getObject());
                }
                objectEventCounter.incrementAndGet();
            } else if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage mesg = (ActiveMQTextMessage) message;
                ActiveMQDestination messageDestination = mesg.getDestination();
                log.debug("BrokerCepConsumer.onMessage(): Message received: source={}, payload={}, mime={}",
                        messageDestination.getPhysicalName(), mesg.getText(), mesg.getJMSXMimeType());

                // Send message to Esper
                cepService.handleEvent(mesg.getText(), messageDestination.getPhysicalName());
                textEventCounter.incrementAndGet();
            } else {
                otherEventCounter.incrementAndGet();
                log.warn("BrokerCepConsumer.onMessage(): Message ignored: type={}", message.getClass().getName());
            }
            eventCounter.incrementAndGet();
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.onMessage(): EXCEPTION: ", ex);
            eventFailuresCounter.incrementAndGet();
        }
    }

    public static long getEventCounter() { return eventCounter.get(); }
    public static long getTextEventCounter() { return textEventCounter.get(); }
    public static long getObjectEventCounter() { return objectEventCounter.get(); }
    public static long getOtherEventCounter() { return otherEventCounter.get(); }
    public static long getEventFailuresCounter() { return eventFailuresCounter.get(); }
    public static synchronized void clearCounters() {
        eventCounter.set(0L);
        textEventCounter.set(0L);
        objectEventCounter.set(0L);
        otherEventCounter.set(0L);
        eventFailuresCounter.set(0L);
    }
}