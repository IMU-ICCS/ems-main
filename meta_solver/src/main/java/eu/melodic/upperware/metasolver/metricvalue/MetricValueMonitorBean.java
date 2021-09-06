/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.metricvalue;

import eu.melodic.upperware.metasolver.Coordinator;
import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import eu.melodic.upperware.metasolver.util.PredictionHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricValueMonitorBean implements ApplicationContextAware {

    private MetaSolverProperties properties;
    private Coordinator coordinator;
    private PredictionHelper predictionHelper;
    private MessageProducer debugEventProducer = null;

    private final HashMap<String, ConnectionConf> connectionCache = new HashMap<>();
    private final MetricValueRegistry<Object> registry;

	@Value("${ems-broker-username:#{null}}")
	private String brokerUsername;
	@Value("${ems-broker-password:#{null}}")
	private String brokerPassword;
	@Value("${ems-broker-certificate:#{null}}")
	private String brokerCertificate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.properties = applicationContext.getBean(MetaSolverProperties.class);
        this.coordinator = applicationContext.getBean(Coordinator.class);
        this.predictionHelper = applicationContext.getBean(PredictionHelper.class);
        log.debug("MetaSolver.MetricValueMonitorBean: setApplicationContext(): configuration={}", properties);
        log.debug("MetaSolver.MetricValueMonitorBean: setApplicationContext(): Broker username: {}", brokerUsername);

        initDebugEventTopic();
    }

    public MetricValueRegistry<Object> getMetricValuesRegistry() {
        return registry;
    }

    protected void initDebugEventTopic() {
        MetaSolverProperties.DebugEvent deCfg = properties.getDebugEvents();
        if (deCfg!=null && deCfg.isEnabled() && debugEventProducer==null) {
            log.info("MetaSolver.MetricValueMonitorBean: Subscribing to Debug Event topic: {}", deCfg.getTopicName());
            try {
                subscribe(deCfg.getUrl(), deCfg.getUsername(), deCfg.getPassword(), deCfg.getCertificate(),
                        deCfg.getTopicName(), deCfg.getClientId(), TopicType.DEBUG_EVENT);
                Session session = connectionCache.remove(deCfg.getUrl()).getSessions().get(0).getSession();
                Topic topic = session.createTopic(deCfg.getTopicName());
                debugEventProducer = session.createProducer(topic);
                log.info("MetaSolver.MetricValueMonitorBean: Subscribed to Debug Event topic: {}", deCfg.getTopicName());
            } catch (Exception e) {
                log.error("MetaSolver.MetricValueMonitorBean: EXCEPTION while subscribing to Debug Event topic: {}. Exception: ", deCfg.getTopicName(), e);
            }
        }
    }

    public void subscribe() {
        // Check if Pub/Sub should be activated
        log.info("*****   Pub/Sub is SWITCHED {}", properties.getPubsub().isOn() ? "ON" : "OFF");
        if (!properties.getPubsub().isOn()) {
            return;
        }

        // Subscribe to configured topics
        log.debug("Subscribing to STARTUP topics: ");
        if (properties.getPubsub() != null && properties.getPubsub().getStartupTopics() != null) {
            subscribeToTopicsList(properties.getPubsub().getStartupTopics());
        }
        log.debug("Subscribing to topics: ok");
    }

    public void subscribeToCommonTopics() {
        if (properties.getPubsub() != null) {
            List<MetaSolverProperties.Pubsub.Topic> topicsList = properties.getPubsub().getCommonTopics();
            if (topicsList!=null && topicsList.size()>0) {
                log.debug("Subscribing to COMMON topics: ");
                subscribeToTopicsList(properties.getPubsub().getCommonTopics());
            }
        }
    }

    private void subscribeToTopicsList(List<MetaSolverProperties.Pubsub.Topic> topicsList) {
        log.debug("subscribeToTopicsList: topicsLists: {}", topicsList);
        if (topicsList != null) {
            int i = 1;
            for (MetaSolverProperties.Pubsub.Topic pst : topicsList) {
                log.debug("subscribeToTopicsList: Processing #{} entry: {}", i, pst);
                // Get topic configuration
                String url = pst.getUrl();
                String topicName = pst.getName();
                String clientId = pst.getClientId();
                TopicType type = pst.getType();
                if (StringUtils.isBlank(url) || url.trim().equalsIgnoreCase("DEFAULT_BROKER_URL"))
                    url = ActiveMQConnection.DEFAULT_BROKER_URL + "?daemon=true&trace=false&useInactivityMonitor=false&connectionTimeout=0&keepAlive=true";
                else url = url.trim();
                if (StringUtils.isBlank(topicName))
                    throw new IllegalArgumentException("Topic name not set: #"+i+" pubsub settings");
                if (StringUtils.isEmpty(clientId)) clientId = "";
                else clientId = clientId.trim();
                if (type == null) type = TopicType.UNKNOWN;
                log.debug("Topic : {}", pst);
                if (type==TopicType.MVV) {
                    registry.addMvvMetricName(topicName);
                }

                // Subscribe to topic
                _do_subscribe(url, brokerUsername, brokerPassword, brokerCertificate, topicName, clientId, type, false);
                i++;
            }
        }
    }

    public void subscribe(String url, String username, String password, String certificate, String topicName, String clientId, TopicType type) {
        // Check if Pub/Sub should be activated
        if (! properties.getPubsub().isOn()) {
            log.info("*****   Pub/Sub is SWITCHED OFF");
            return;
        }

        _do_subscribe(url, username, password, certificate, topicName, clientId, type, false);

        if (properties.isPredictionMonitoringEnabled()) {
            String predictionTopicName = predictionHelper.getPredictionTopicNameFor(topicName);
            _do_subscribe(url, username, password, certificate, predictionTopicName, clientId, type, true);
        }
    }

    private void _do_subscribe(String url, String username, String password, String certificate, String topicName, String clientId, TopicType type, boolean isPrediction) {
        try {
            log.debug("*****   SUBSCRIBE:\n  URL      : {}\n  Username : {}\n  Topic    : {}\n  Client-Id: {}\n  Type     : {}",
                    url, username, topicName, clientId, type);

            // Get ActiveMQ connection to the server
            log.trace("*****   SUBSCRIBE: connection factory created: url={}", url);
            ConnectionConf cconf = connectionCache.get(url);
            if (cconf == null) {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                //Connection connection = connectionFactory.createConnection(brokerUsername, brokerPassword);
                Connection connection = connectionFactory.createConnection(username, password);
                log.trace("*****   SUBSCRIBE: connection created");
                if (StringUtils.isNotBlank(clientId)) {
                    connection.setClientID(clientId.trim());
                    log.trace("*****   SUBSCRIBE: client id set: {}", clientId);
                }

                cconf = new ConnectionConf();
                cconf.setUrl(url);
                cconf.setConnectionFactory(connectionFactory);
                cconf.setConnection(connection);

                connectionCache.put(url, cconf);

                // Open JMS session  (We use sessions not transactions)
                Session session = cconf.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
                log.trace("*****   SUBSCRIBE: session created");

                SessionConf sconf = new SessionConf();
                sconf.setSession(session);
                cconf.getSessions().add(sconf);
            }

            // Currently, only one session per connection is supported
            SessionConf sconf = cconf.getSessions().get(0);
            Session session = sconf.getSession();

            // Subscribe to topic  (We use sessions not transactions)
            Topic topic = session.createTopic(topicName);
            log.trace("*****   SUBSCRIBE: topic created: {}", topicName);
            MessageConsumer consumer = session.createConsumer(topic);
            log.trace("*****   SUBSCRIBE: consumer created: topic={}", topicName);

            TopicConf tconf = new TopicConf();
            tconf.setTopic(topic);
            tconf.setConsumer(consumer);

            sconf.getTopics().add(tconf);

            // Add message listener to receive incoming messages
            MessageListener lsnr = getListener(topic, type, isPrediction);
            consumer.setMessageListener(lsnr);
            log.trace("*****   SUBSCRIBE: listener added");

            // Ready to go
            cconf.getConnection().start();
            log.trace("*****   SUBSCRIBE: connection started");

            log.info("*****   SUBSCRIBE: ok  --  {} / {}", topicName, type);

            //saving metricNames
            if (type==TopicType.MVV) {
                registry.addMvvMetricName(topicName);
            }


        } catch (Exception e) {
            log.error("*****   SUBSCRIBE: ERROR: ", e);
        }
    }

    private MessageListener getListener(Topic topic, TopicType type, boolean isPrediction) throws JMSException {
        MessageListener listener = new MetricValueListener(coordinator, topic, type, registry, isPrediction);
        return listener;
    }

    public void unsubscribe() {
        try {
            // Check if Pub/Sub should be activated
            if (! properties.getPubsub().isOn()) {
                log.debug("*****   Pub/Sub is SWITCHED OFF");
                return;
            }

            log.debug("*****   UN-SUBSCRIBE:");

            // Stop and close consumer, JMS session and ActiveMQ connection
            for (ConnectionConf cconf : connectionCache.values()) {
                try {
                    log.debug("  Closing connection to url: {}", cconf.getUrl());

                    for (SessionConf sconf : cconf.getSessions()) {
                        try {
                            log.debug("    Closing session: {}", sconf);

                            for (TopicConf tconf : sconf.getTopics()) {
                                String topicName = tconf.getTopic() != null ? tconf.getTopic().getTopicName() : null;
                                try {
                                    log.debug("      Unsubscribing from topic: {}", topicName);
                                    tconf.getConsumer().close();
                                    log.debug("      Unsubscribing from topic: {} : ok", topicName);
                                } catch (Exception e) {
                                    log.error("      Unsubscribing from topic: {} : ERROR: {}", topicName, e);
                                }
                            }

                            sconf.getSession().close();
                            log.debug("    Closing session: {} : ok", sconf);
                        } catch (Exception e) {
                            log.error("    Closing session: {} : ERROR: {}", sconf, e);
                        }
                    }

                    cconf.getConnection().stop();
                    log.debug("  Closing connection to url: {} : ok", cconf.getUrl());
                } catch (Exception e) {
                    log.error("  Closing connection to url: {} : ERROR: {}", cconf.getUrl(), e);
                }
            }

            log.info("*****   UN-SUBSCRIBE: ok");

        } catch (Exception e) {
            log.error("*****   SUBSCRIBE: ERROR: ", e);
        } finally {
            connectionCache.clear();
        }
    }

    public void sendDebugEvent(String topicName, Map<String, String> metricValues) throws JMSException {
        initDebugEventTopic();

        /*ActiveMQMapMessage message = new ActiveMQMapMessage();
        message.setObject("metricValues", metricValues);
        message.setLong("timestamp", System.currentTimeMillis());
        */
        Map<String,Object> map = new HashMap<>();
        map.put("metricValues", metricValues);
        map.put("timestamp", System.currentTimeMillis());
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText(map.toString());

        if (debugEventProducer==null)
            log.warn("MetaSolver.MetricValueMonitorBean.sendDebugEvent: Debug Event producer has not been initialized");
        else
            debugEventProducer.send(message);
    }

    public void setMetricValueInRegistry(String name, String value) {
        this.registry.setMetricValue(name, value);
    }


    @Getter
    @Setter
    private class ConnectionConf {
        private String url;
        private ConnectionFactory connectionFactory;
        private Connection connection;
        private List<SessionConf> sessions = new ArrayList<>();
    }

    @Getter
    @Setter
    private class SessionConf {
        private Session session;
        private List<TopicConf> topics = new ArrayList<>();
    }

    @Getter
    @Setter
    private class TopicConf {
        private Topic topic;
        private MessageConsumer consumer;
    }
}
