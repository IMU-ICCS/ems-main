/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep;

import eu.melodic.event.brokercep.properties.BrokerCepProperties;
import eu.melodic.event.util.GroupingConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventForwarder implements InitializingBean, Runnable {
    @Getter @Setter
    private static EventForwarder instance;

    private final BrokerCepProperties properties;
    private final BrokerCepService brokerCepService;
    private final LinkedBlockingDeque<EventForwardTask> eventForwardingQueue = new LinkedBlockingDeque<>();

    public void addEventForwardTask(@NonNull String senderName, @NonNull GroupingConfiguration.BrokerConnectionConfig brokerConnectionConfig, @NonNull String topic, @NonNull Map<String,Object> eventMap, Runnable success, Runnable failure) {
        boolean isLocalPublish =
                brokerCepService.getBrokerCepProperties().getBrokerUrlForConsumer()
                        .equals(brokerConnectionConfig.getUrl());
        eventForwardingQueue.add(new EventForwardTask(senderName, isLocalPublish, brokerConnectionConfig, topic, eventMap, success, failure));
        log.debug("EventForwarder: {} task in the queue", eventForwardingQueue.size());
    }

    public void addEventForwardTask(@NonNull String senderName, String brokerUrl, String certificate, String username, String password, @NonNull String topic, @NonNull Map<String,Object> eventMap, Runnable success, Runnable failure) {
        GroupingConfiguration.BrokerConnectionConfig brokerConnectionConfig =
                new GroupingConfiguration.BrokerConnectionConfig(null, brokerUrl, certificate, username, password);
        addEventForwardTask(senderName, brokerConnectionConfig, topic, eventMap, success, failure);
    }

    public void addLocalPublishTask(@NonNull String senderName, @NonNull String topic, @NonNull Map<String,Object> eventMap, Runnable success, Runnable failure) {
        String brokerUrl = brokerCepService.getBrokerCepProperties().getBrokerUrlForConsumer();
        String username = brokerCepService.getBrokerUsername();
        String password = brokerCepService.getBrokerPassword();
        GroupingConfiguration.BrokerConnectionConfig brokerConnectionConfig =
                new GroupingConfiguration.BrokerConnectionConfig(null, brokerUrl, null, username, password);
        eventForwardingQueue.add(new EventForwardTask(senderName, true, brokerConnectionConfig, topic, eventMap, success, failure));
        log.debug("EventForwarder: {} task in the queue", eventForwardingQueue.size());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (instance==null) instance = this;
        Executors.newFixedThreadPool(1).submit(this);
        log.info("EventForwarder: Starting event publish/forward worker");
    }

    @Override
    public void run() {
        while (true) {
            try {
                processEventForwardTask(eventForwardingQueue.take());
            } catch (Throwable t) {
                log.warn("EventForwarder: Exception thrown in task processing loop: ", t);
            }
        }
    }

    private void processEventForwardTask(EventForwardTask task) {
        String senderName = task.getSenderName();
        String topic = task.getTopic();
        Map<String, Object> eventMap = task.getEventMap();

        // Check if max task processing duration has been exceeded
        long duration = System.currentTimeMillis() - task.getCreation();
        if (properties.getMaxEventForwardDuration()>0 && duration > properties.getMaxEventForwardDuration()) {
            log.error("- Max event publish/forward duration exceeded. Dropping event: subscriber={}, forward-to-groupings={}, topic={}, payload={}",
                    senderName, task.getBrokerConnectionConfig(), topic, eventMap);

            runIfNotNull(task.getFailure());
            return;
        }

        // Process event publish/forward task
        try {
            String brokerUrl = task.getBrokerConnectionConfig().getUrl();
            String username = task.getBrokerConnectionConfig().getUsername();
            String password = task.getBrokerConnectionConfig().getPassword();

            if (task.isLocalPublish()) {
                log.trace("- Publishing event to local broker: subscriber={}, local-broker={}, username={}, password={}, topic={}, retry={}, payload={}",
                        senderName, brokerUrl, username, "passwordEncoded", topic, task.getRetries(), eventMap);
            } else {
                log.debug("- Forwarding event to grouping: subscriber={}, forward-to-grouping={}, url={}, username={}, topic={}, retry={}, payload={}",
                        senderName, task.getBrokerConnectionConfig(), brokerUrl, username, topic, task.getRetries(), eventMap);
            }

            task.newRetry();
            brokerCepService.publishEvent(brokerUrl, username, password, topic, eventMap);
            task.completed();

            if (task.isLocalPublish()) {
                log.debug("- Event published to local broker: subscriber={}, local-broker={}, username={}, topic={}, payload={}, duration={}ms",
                        senderName, brokerUrl, username, topic, eventMap, task.getTotalDuration());
            } else {
                log.debug("- Event forwarded to grouping: subscriber={}, forwarded-to-grouping={}, url={}, username={}, topic={}, payload={}, duration={}ms",
                        senderName, task.brokerConnectionConfig, brokerUrl, username, topic, eventMap, task.getTotalDuration());
            }

            runIfNotNull(task.getSuccess());

        } catch (Exception ex) {
            task.increaseRetries();
            log.error("- Error while sending event: subscriber={}, forward-to-groupings={}, topic={}, retry={}, duration={}ms, payload={}, exception: ",
                    senderName, task.getBrokerConnectionConfig(), topic, task.getRetries()-1, task.getTotalDuration(), eventMap, ex);

            if (properties.getMaxEventForwardRetries()>=0 && task.getRetries() > properties.getMaxEventForwardRetries()) {
                log.error("- Max event publish/forward retries exceeded. Dropping event: subscriber={}, forward-to-groupings={}, topic={}, payload={}",
                        senderName, task.getBrokerConnectionConfig(), topic, eventMap);

                runIfNotNull(task.getFailure());

            } else
            if (properties.getMaxEventForwardDuration()>0 && task.getTotalDuration() > properties.getMaxEventForwardDuration()) {
                log.error("- Max event publish/forward duration exceeded. Dropping event: subscriber={}, forward-to-groupings={}, topic={}, payload={}",
                        senderName, task.getBrokerConnectionConfig(), topic, eventMap);

                runIfNotNull(task.getFailure());

            } else {
                eventForwardingQueue.add(task);
                log.debug("- Event placed back in queue: subscriber={}, forward-to-groupings={}, topic={}, payload={}",
                        senderName, task.getBrokerConnectionConfig(), topic, eventMap);
            }
        }
    }

    protected void runIfNotNull(Runnable r) {
        if (r==null) return;
        r.run();
    }

    @Getter
    @RequiredArgsConstructor
    protected static class EventForwardTask {
        private final String senderName;
        private final boolean localPublish;
        private final GroupingConfiguration.BrokerConnectionConfig brokerConnectionConfig;
        private final String topic;
        private final Map<String,Object> eventMap;
        private final Runnable success;
        private final Runnable failure;
        private final long creation = System.currentTimeMillis();

        private long lastRetryStart;
        private long lastRetryEnd;
        private boolean completed;
        private int retries = 0;

        public void newRetry() {
            if (completed) return;
            lastRetryStart = System.currentTimeMillis();
        }

        public void completed() {
            if (completed) return;
            completed = true;
            lastRetryEnd = System.currentTimeMillis();
        }

        public void increaseRetries() {
            if (completed) return;
            lastRetryEnd = System.currentTimeMillis();
            ++retries;
        }

        public long getLastRetryDuration() {
            return lastRetryEnd - lastRetryStart;
        }

        public long getTotalDuration() {
            return lastRetryEnd - creation;
        }
    }
}
