/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.simulationHandler;

import javax.jms.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;

@Slf4j
class MetricValueSender {

    private boolean isPreserveConnection = true;
    private String metricQueueUrl;

    private Connection connection;
    private Session session;

    private String topicName;
    private String username;
    private String password;

    // ------------------------------------------------------------------------

    MetricValueSender(String url, String topicName, String username, String password) {
        this.metricQueueUrl = url;
        this.topicName = topicName;
        this.username = username;
        this. password = password;
    }

    // ------------------------------------------------------------------------

    synchronized void publishEvent(String metricValue) throws JMSException {
        // open or reuse connection

        boolean _closeConn = false;
        if (session==null) {
            openConnection();
            _closeConn = ! isPreserveConnection;
        }

        // Create the destination (Topic)
        Destination destination = session.createTopic(topicName);

        // Create a MessageProducer from the Session to the Topic
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(javax.jms.DeliveryMode.NON_PERSISTENT);

        // Create a messages
        TextMessage message = session.createTextMessage(metricValue);

        // Tell the producer to send the message
        long hash = message.hashCode();
        log.info("SimulationHandler.MetricValueSender.publishEvent(): Sending message: connection={}, destination={}, hash={}, payload={}", metricQueueUrl, topicName, hash, metricValue);
        producer.send(message);
        log.info("SimulationHandler.MetricValueSender.publishEvent(): Message sent: connection={}, destination={}, hash={}, payload={}", metricQueueUrl, topicName, hash, metricValue);

        // close connection
        if (_closeConn) {
            closeConnection();
        }
    }

    // ------------------------------------------------------------------------

    private ActiveMQConnectionFactory createConnectionFactory() {
        // Create connection factory based on received URL scheme from EMS
        final ActiveMQConnectionFactory connectionFactory;
        String brokerUrl = metricQueueUrl;
        if (brokerUrl.startsWith("ssl")) {
            //I believe this url should never contain ssl
            log.info("MetricValueSender.createConnectionFactory(): Creating new SSL connection factory instance: url={}", brokerUrl);
            final ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl);
            try {
                /*
                sslConnectionFactory.setTrustStore(properties.getTruststoreFile());
                sslConnectionFactory.setTrustStoreType(properties.getTruststoreType());
                sslConnectionFactory.setTrustStorePassword(properties.getTruststorePassword());
                sslConnectionFactory.setKeyStore(properties.getKeystoreFile());
                sslConnectionFactory.setKeyStoreType(properties.getKeystoreType());
                sslConnectionFactory.setKeyStorePassword(properties.getKeystorePassword());
                //sslConnectionFactory.setKeyStoreKeyPassword( properties........ );
                */
                connectionFactory = sslConnectionFactory;
            } catch (final Exception theException) {
                throw new Error(theException);
            }
        } else {
            log.info("SimulationHandler.MetricValueSender.createConnectionFactory(): Creating new non-SSL connection factory instance: url={}", brokerUrl);
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        }

        // Other connection factory settings
        //connectionFactory.setSendTimeout(....5000L);
        //connectionFactory.setTrustedPackages(Arrays.asList("eu.melodic.event"));
        //connectionFactory.setTrustAllPackages(true);
        connectionFactory.setWatchTopicAdvisories(true);

        return connectionFactory;
    }

    // ------------------------------------------------------------------------

    private synchronized void openConnection() throws JMSException {

        // Create connection factory
        ActiveMQConnectionFactory connectionFactory = createConnectionFactory();
        connectionFactory.setBrokerURL(metricQueueUrl);

        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);

        // Create a Connection
        log.info("MetricValueSender: Connecting to activeMQ: {}...", metricQueueUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        log.info("MetricValueSender: Opening session...");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.connection = connection;
        this.session = session;
    }

    private synchronized void closeConnection() throws JMSException {
        // Clean up
        session.close();
        connection.close();
        session = null;
        connection = null;
    }
}