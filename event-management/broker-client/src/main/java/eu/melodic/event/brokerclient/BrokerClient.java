/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.event.brokerclient.event.EventMap;
import eu.melodic.event.brokerclient.properties.BrokerClientProperties;
import java.io.Serializable;
import java.util.*;
import javax.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BrokerClient {

    @Autowired
    private BrokerClientProperties properties;
    private Connection connection;
    private Session session;
    private HashMap<MessageListener,MessageConsumer> listeners = new HashMap<>();
    private Gson gson = new GsonBuilder().create();

    public BrokerClient() {
    }

    public BrokerClient(BrokerClientProperties bcp) {
        properties = bcp;
    }

    public BrokerClient(Properties p) {
        properties = new BrokerClientProperties(p);
    }

    // ------------------------------------------------------------------------

    public static BrokerClient newClient() throws java.io.IOException, JMSException {
        log.info("BrokerClient: Initializing...");

        // get properties file
        String configDir = System.getenv("MELODIC_CONFIG_DIR");
        if (configDir == null || configDir.trim().isEmpty()) configDir = ".";
        log.info("BrokerClient: config-dir: {}", configDir);
        String configPropFile = configDir + "/" + "eu.melodic.event.brokerclient.properties";
        log.info("BrokerClient: config-file: {}", configPropFile);

        // load properties
        Properties p = new Properties();
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //try (java.io.InputStream in = loader.getClass().getResourceAsStream(configPropFile)) { p.load(in); }
        try (java.io.InputStream in = new java.io.FileInputStream(configPropFile)) {
            p.load(in);
        }
        log.info("BrokerClient: config-properties: {}", p);

        // initialize broker client
        BrokerClient client = new BrokerClient(p);
        log.info("BrokerClient: Configuration:\n{}", client.properties);

        return client;
    }

    public static BrokerClient newClient(String username, String password) throws java.io.IOException, JMSException {
        BrokerClient client = newClient();
        if (username!=null && password!=null) {
            client.getClientProperties().setBrokerUsername(username);
            client.getClientProperties().setBrokerPassword(password);
        }
        return client;
    }

    // ------------------------------------------------------------------------

    public BrokerClientProperties getClientProperties() {
        checkProperties();
        return properties;
    }

    protected void checkProperties() {
        if (properties==null) {
            //use defaults
            properties = new BrokerClientProperties();
        }
    }

    // ------------------------------------------------------------------------

    public synchronized Set<String> getDestinationNames(String connectionString) throws JMSException {
        // open or reuse connection
        checkProperties();
        boolean _closeConn = false;
        if (session==null) {
            openConnection(connectionString);
            _closeConn = ! properties.isPreserveConnection();
        }

        // Get destinations from Broker
        log.info("BrokerClient.getDestinationNames(): Getting destinations: connection={}, username={}", connectionString, properties.getBrokerUsername());
        ActiveMQConnection conn = (ActiveMQConnection)connection;
        DestinationSource ds = conn.getDestinationSource();
        Set<ActiveMQQueue> queues = ds.getQueues();
        Set<ActiveMQTopic> topics = ds.getTopics();
        Set<ActiveMQTempQueue> tempQueues = ds.getTemporaryQueues();
        Set<ActiveMQTempTopic> tempTopics = ds.getTemporaryTopics();
        log.info("BrokerClient.getDestinationNames(): Getting destinations: done");

        // Get destination names
        HashSet<String> destinationNames = new HashSet<>();
        for (ActiveMQQueue q : queues) destinationNames.add("QUEUE "+q.getQueueName());
        for (ActiveMQTopic t : topics) destinationNames.add("TOPIC "+t.getTopicName());
        for (ActiveMQTempQueue tq : tempQueues) destinationNames.add("Temp QUEUE "+tq.getQueueName());
        for (ActiveMQTempTopic tt : tempTopics) destinationNames.add("Temp TOPIC "+tt.getTopicName());

        // close connection
        if (_closeConn) {
            closeConnection();
        }

        return destinationNames;
    }

    // ------------------------------------------------------------------------

    public synchronized void publishEvent(String connectionString, String destinationName, Map<String, Object> eventMap) throws JMSException {
        _publishEvent(connectionString, destinationName, new EventMap(eventMap));
    }

    protected synchronized void _publishEvent(String connectionString, String destinationName, Serializable event) throws JMSException {
        // open or reuse connection
        checkProperties();
        boolean _closeConn = false;
        if (session==null) {
            openConnection(connectionString);
            _closeConn = ! properties.isPreserveConnection();
        }

        // Create the destination (Topic or Queue)
        //Destination destination = session.createQueue( destinationName );
        Destination destination = session.createTopic(destinationName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(javax.jms.DeliveryMode.NON_PERSISTENT);

        // Create a messages
        //ObjectMessage message = session.createObjectMessage(event);
        //TextMessage message = session.createTextMessage(event.toString());
        String payload = gson.toJson(event);
        TextMessage message = session.createTextMessage(payload);
        log.debug("BrokerClient.publishEvent(): Message payload: payload={}", payload);

        // Tell the producer to send the message
        long hash = message.hashCode();
        log.info("BrokerClient.publishEvent(): Sending message: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);
        producer.send(message);
        log.info("BrokerClient.publishEvent(): Message sent: connection={}, username={}, destination={}, hash={}, payload={}", connectionString, properties.getBrokerUsername(), destinationName, hash, event);

        // close connection
        if (_closeConn) {
            closeConnection();
        }
    }

    // ------------------------------------------------------------------------

    public void subscribe(String connectionString, String destinationName, MessageListener listener) throws JMSException {
        // Create or open connection
        checkProperties();
        if (session==null) {
            openConnection(connectionString);
        }

        // Create the destination (Topic or Queue)
        log.info("BrokerClient: Subscribing to destination: {}...", destinationName);
        //Destination destination = session.createQueue( destinationName );
        Destination destination = session.createTopic(destinationName);

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(listener);
        listeners.put(listener, consumer);
    }

    public void unsubscribe(MessageListener listener) throws JMSException {
        MessageConsumer consumer = listeners.get(listener);
        if (consumer!=null) {
            consumer.close();
        }
    }

    // ------------------------------------------------------------------------

    public void receiveEvents(String connectionString, String destinationName, MessageListener listener) throws JMSException {
        checkProperties();
        MessageConsumer consumer = null;
        boolean _closeConn = false;
        try {
            // Create or open connection
            if (session==null) {
                openConnection(connectionString);
                _closeConn = ! properties.isPreserveConnection();
            }

            // Create the destination (Topic or Queue)
            log.info("BrokerClient: Subscribing to destination: {}...", destinationName);
            //Destination destination = session.createQueue( destinationName );
            Destination destination = session.createTopic(destinationName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);

            // Wait for messages
            log.info("BrokerClient: Waiting for messages...");
            while (true) {
                Message message = consumer.receive();
                listener.onMessage(message);
            }

        } finally {
            // Clean up
            log.info("BrokerClient: Closing connection...");
            if (consumer != null) consumer.close();
            if (_closeConn) {
                closeConnection();
            }
        }
    }

    // ------------------------------------------------------------------------

    public ActiveMQConnectionFactory createConnectionFactory() {
        // Create connection factory based on Broker URL scheme
        checkProperties();
        final ActiveMQConnectionFactory connectionFactory;
        String brokerUrl = properties.getBrokerUrl();
        if (brokerUrl.startsWith("ssl")) {
            log.info("BrokerClient.createConnectionFactory(): Creating new SSL connection factory instance: url={}", brokerUrl);
            final ActiveMQSslConnectionFactory sslConnectionFactory = new ActiveMQSslConnectionFactory(brokerUrl);
            try {
                sslConnectionFactory.setTrustStore(properties.getTruststoreFile());
                sslConnectionFactory.setTrustStoreType(properties.getTruststoreType());
                sslConnectionFactory.setTrustStorePassword(properties.getTruststorePassword());
                sslConnectionFactory.setKeyStore(properties.getKeystoreFile());
                sslConnectionFactory.setKeyStoreType(properties.getKeystoreType());
                sslConnectionFactory.setKeyStorePassword(properties.getKeystorePassword());
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

    // ------------------------------------------------------------------------

    public synchronized void openConnection() throws JMSException {
        checkProperties();
        openConnection(properties.getBrokerUrl(), null, null);
    }

    public synchronized void openConnection(String connectionString) throws JMSException {
        openConnection(connectionString, null, null);
    }

    public synchronized void openConnection(String connectionString, String username, String password) throws JMSException {
        openConnection(connectionString, username, password, properties.isPreserveConnection());
    }

    public synchronized void openConnection(String connectionString, String username, String password, boolean preserveConnection) throws JMSException {
        checkProperties();
        if (connectionString == null) connectionString = properties.getBrokerUrl();
        log.debug("BrokerClient: Credetials provided as arguments: username={}, password={}", username, password);
        if (StringUtils.isBlank(username)) {
            username = properties.getBrokerUsername();
            password = properties.getBrokerPassword();
            log.debug("BrokerClient: Credetials read from properties: username={}, password={}", username, password);
        }

        // Create connection factory
        ActiveMQConnectionFactory connectionFactory = createConnectionFactory();
        connectionFactory.setBrokerURL(connectionString);
        if (StringUtils.isNotBlank(username) && password != null) {
            connectionFactory.setUserName(username);
            connectionFactory.setPassword(password);
        }
        log.debug("BrokerClient: Connection credentials: username={}, password={}", username, password);

        // Create a Connection
        log.info("BrokerClient: Connecting to broker: {}...", connectionString);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        log.info("BrokerClient: Opening session...");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.connection = connection;
        this.session = session;
    }

    public synchronized void closeConnection() throws JMSException {
        // Clean up
        session.close();
        connection.close();
        session = null;
        connection = null;
    }
}