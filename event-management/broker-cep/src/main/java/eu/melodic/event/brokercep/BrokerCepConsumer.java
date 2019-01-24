/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep;

import eu.melodic.event.brokercep.broker.BrokerConfig;
import eu.melodic.event.brokercep.cep.CepService;
import eu.melodic.event.brokercep.properties.BrokerCepProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class BrokerCepConsumer implements MessageListener, InitializingBean {
    @Autowired
    private BrokerCepProperties properties;
    @Autowired
    private BrokerConfig brokerConfig;
    @Autowired
    private BrokerService brokerService;    // Added in order to ensure that BrokerService will be instantiated first
    @Autowired
    private ActiveMQConnectionFactory connectionFactory;
    @Autowired
    private CepService cepService;

    private Connection connection;
    private Session session;
    private final Set<String> addedDestinations = new HashSet<>();

    @Override
    public void afterPropertiesSet() {
        initialize();
    }

    private void initialize() {
        log.debug("BrokerCepConsumer.initialize(): Initializing Broker-CEP consumer instance...");
        try {
            // If an alternative Broker URL is provided for consumer, it will be use
            ActiveMQConnectionFactory connectionFactory = this.connectionFactory;
            if (StringUtils.isNotBlank(properties.getBrokerUrlForConsumer())) {
                log.debug("BrokerCepConsumer.initialize(): Alternative broker URL will be used for Broker-CEP consumer instance: {}", properties.getBrokerUrlForConsumer());
                connectionFactory = this.connectionFactory.copy();
                connectionFactory.setBrokerURL(properties.getBrokerUrlForConsumer());
            } else {
                log.debug("BrokerCepConsumer.initialize(): Default broker URL will be used for Broker-CEP consumer instance: {}", brokerConfig.getBrokerUrl());
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
        if (addedDestinations.contains(queueName)) {
            log.debug("BrokerCepConsumer.addQueue(): Queue already added: {}", queueName);
            return;
        }
        try {
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            addedDestinations.add(queueName);
            log.debug("BrokerCepConsumer.addQueue(): Added queue: {}", queueName);
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.addQueue(): EXCEPTION: ", ex);
        }
    }

    public synchronized void addTopic(String topicName) {
        log.debug("BrokerCepConsumer.addTopic(): Adding topic: {}", topicName);
        if (addedDestinations.contains(topicName)) {
            log.debug("BrokerCepConsumer.addTopic(): Topic already added: {}", topicName);
            return;
        }
        try {
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(this);
            addedDestinations.add(topicName);
            log.debug("BrokerCepConsumer.addTopic(): Added topic: {}", topicName);
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.addTopic(): EXCEPTION: ", ex);
        }
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
            } else if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage mesg = (ActiveMQTextMessage) message;
                ActiveMQDestination messageDestination = mesg.getDestination();
                log.debug("BrokerCepConsumer.onMessage(): Message received: source={}, payload={}, mime={}",
                        messageDestination.getPhysicalName(), mesg.getText(), mesg.getJMSXMimeType());

                // Send message to Esper
                cepService.handleEvent(mesg.getText(), messageDestination.getPhysicalName());
            } else {
                log.warn("BrokerCepConsumer.onMessage(): Message ignored: type={}", message.getClass().getName());
            }
        } catch (Exception ex) {
            log.error("BrokerCepConsumer.onMessage(): EXCEPTION: ", ex);
        }
    }
}