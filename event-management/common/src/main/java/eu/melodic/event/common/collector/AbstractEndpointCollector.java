/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.common.collector;

import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.common.misc.EventConstant;
import eu.melodic.event.common.recovery.RecoveryConstant;
import eu.melodic.event.util.EmsConstant;
import eu.melodic.event.util.EventBus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * Abstract collector:
 * Collects measurements from http server endpoint
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEndpointCollector<T> implements InitializingBean, Runnable, EventBus.EventConsumer<String,Object,Object> {
    public final static String ABSTRACT_ENDPOINT_COLLECTION_START = "ABSTRACT_ENDPOINT_COLLECTION_START";
    public final static String ABSTRACT_ENDPOINT_COLLECTION_END = "ABSTRACT_ENDPOINT_COLLECTION_END";
    public final static String ABSTRACT_ENDPOINT_CONN_OK = "ABSTRACT_ENDPOINT_CONN_OK";
    public final static String ABSTRACT_ENDPOINT_CONN_ERROR = "ABSTRACT_ENDPOINT_CONN_ERROR";
    public final static String ABSTRACT_ENDPOINT_NODE_OK = "ABSTRACT_ENDPOINT_NODE_OK";
    public final static String ABSTRACT_ENDPOINT_NODE_FAILED = "ABSTRACT_ENDPOINT_NODE_FAILED";

    protected final String collectorId;
    protected final AbstractEndpointCollectorProperties properties;
    protected final CollectorContext collectorContext;
    protected final TaskScheduler taskScheduler;
    protected final EventBus<String,Object,Object> eventBus;

    protected boolean started;
    protected ScheduledFuture<?> runner;
    protected List<String> allowedTopics;
    protected Map<String, String> topicMap;

    protected Map<String, Integer> errorsMap = new HashMap<>();
    protected Map<String,ScheduledFuture<?>> ignoredNodes = new HashMap<>();

    protected enum COLLECTION_RESULT { IGNORED, OK, ERROR }

    @Override
    public void afterPropertiesSet() {
        log.debug("Collectors::{}: properties: {}", collectorId, properties);
        this.allowedTopics = properties.getAllowedTopics()==null
                ? null
                : properties.getAllowedTopics().stream()
                .map(s -> s.split(":")[0])
                .collect(Collectors.toList());
        this.topicMap = properties.getAllowedTopics()==null
                ? null
                : properties.getAllowedTopics().stream()
                        .map(s -> s.split(":", 2))
                        .collect(Collectors.toMap(a -> a[0], a -> a.length>1 ? a[1]: ""));
    }

    public synchronized void start() {
        // check if already running
        if (started) {
            log.warn("Collectors::{}: Already started", collectorId);
            return;
        }

        // check parameters
        if (properties==null || !properties.isEnable()) {
            log.warn("Collectors::{}: Collector not enabled", collectorId);
            return;
        }
        if (properties.getDelay()<0) properties.setDelay(0);

        log.info("Collectors::{}: configuration: {}", collectorId, properties);

        // Subscribe for SELF-HEALING plugin GIVE_UP events
        eventBus.subscribe(RecoveryConstant.SELF_HEALING_RECOVERY_COMPLETED, this);
        eventBus.subscribe(RecoveryConstant.SELF_HEALING_RECOVERY_GIVE_UP, this);
        eventBus.subscribe(EventConstant.EVENT_CLIENT_CONFIG_UPDATED, this);

        // Schedule collection execution
        errorsMap.clear();
        ignoredNodes.clear();
        runner = taskScheduler.scheduleWithFixedDelay(this, properties.getDelay());
        started = true;

        log.info("Collectors::{}: Started", collectorId);
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("Collectors::{}: Not started", collectorId);
            return;
        }

        // Unsubscribe from SELF-HEALING plugin GIVE_UP events
        eventBus.unsubscribe(EventConstant.EVENT_CLIENT_CONFIG_UPDATED, this);
        eventBus.unsubscribe(RecoveryConstant.SELF_HEALING_RECOVERY_COMPLETED, this);
        eventBus.unsubscribe(RecoveryConstant.SELF_HEALING_RECOVERY_GIVE_UP, this);

        // Cancel collection execution
        started = false;
        runner.cancel(true);
        runner = null;
        ignoredNodes.values().stream().filter(Objects::nonNull).forEach(task -> task.cancel(true));
        log.info("Collectors::{}: Stopped", collectorId);
    }

    @Override
    public void onMessage(String topic, Object message, Object sender) {
        log.trace("Collectors::{}: onMessage: BEGIN: topic={}, message={}, sender={}", collectorId, topic, message, sender);

        String nodeAddress = (message!=null) ? message.toString() : null;
        log.trace("Collectors::{}: nodeAddress={}", collectorId, nodeAddress);

        if (RecoveryConstant.SELF_HEALING_RECOVERY_COMPLETED.equals(topic)) {
            log.info("Collectors::{}: Resuming collection from Node: {}", collectorId, nodeAddress);
            ignoredNodes.remove(nodeAddress);
        } else
        if (RecoveryConstant.SELF_HEALING_RECOVERY_GIVE_UP.equals(topic)) {
            log.info("Collectors::{}: Giving up collection from Node: {}", collectorId, nodeAddress);
            ignoredNodes.put(nodeAddress, null);
        } else
        if (EventConstant.EVENT_CLIENT_CONFIG_UPDATED.equals(topic)) {
            log.info("Collectors::{}: Client configuration updated. Purging nodes without recovery task from ignore list: Old ignore list nodes: {}", collectorId, ignoredNodes.keySet());
            List<String> nodesToPurge = ignoredNodes.entrySet().stream().filter(e -> e.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toList());
            nodesToPurge.forEach(node -> {
                ignoredNodes.remove(node);
                log.info("Collectors::{}: Client configuration updated. Node purged from ignore list: {}", collectorId, node);
            });
        } else
            log.warn("Collectors::{}: onMessage: Event from unexpected topic received. Ignoring it: {}", collectorId, topic);
    }

    public void run() {
        if (!started) return;

        log.trace("Collectors::{}: run(): BEGIN", collectorId);
        if (log.isTraceEnabled()) {
            log.trace("Collectors::{}: run(): errors-map={}", collectorId, errorsMap);
            log.trace("Collectors::{}: run(): ignored-nodes={}", collectorId, ignoredNodes.keySet());
        }

        // collect data from local node
        if (! properties.isSkipLocal()) {
            log.info("Collectors::{}: Collecting metrics from local node...", collectorId);
            collectAndPublishData("");
        } else {
            log.debug("Collectors::{}: Collection from local node is disabled", collectorId);
        }

        // if Aggregator, collect data from nodes without client
        log.trace("Collectors::{}: Nodes without clients in Zone: {}", collectorId, collectorContext.getNodesWithoutClient());
        log.trace("Collectors::{}: Is Aggregator: {}", collectorId, collectorContext.isAggregator());
        if (collectorContext.isAggregator()) {
            if (collectorContext.getNodesWithoutClient().size()>0) {
                log.info("Collectors::{}: Collecting metrics from remote nodes (without EMS client): {}", collectorId,
                        collectorContext.getNodesWithoutClient());
                for (Object nodeAddress : collectorContext.getNodesWithoutClient()) {
                    // collect data from remote node
                    collectAndPublishData(nodeAddress.toString());
                }
            } else
                log.debug("Collectors::{}: No remote nodes (without EMS client)", collectorId);
        }

        log.trace("Collectors::{}: run(): END", collectorId);
    }

    private COLLECTION_RESULT collectAndPublishData(@NonNull String nodeAddress) {
        if (ignoredNodes.containsKey(nodeAddress)) {
            log.info("Collectors::{}:   Node is in ignore list: {}", collectorId, nodeAddress);
            return COLLECTION_RESULT.IGNORED;
        }
        try {
            sendEvent(ABSTRACT_ENDPOINT_COLLECTION_START, nodeAddress);
            _collectAndPublishData(nodeAddress);
            sendEvent(ABSTRACT_ENDPOINT_COLLECTION_END, nodeAddress);

            //if (Optional.ofNullable(errorsMap.put(nodeAddress, 0)).orElse(0)>0) sendEvent(ABSTRACT_ENDPOINT_CONN_OK, nodeAddress);
            sendEvent(ABSTRACT_ENDPOINT_CONN_OK, nodeAddress);
            sendEvent(ABSTRACT_ENDPOINT_NODE_OK, nodeAddress);
            errorsMap.put(nodeAddress, 0);
            return COLLECTION_RESULT.OK;
        } catch (Throwable t) {
            int errors = errorsMap.compute(nodeAddress, (k, v) -> Optional.ofNullable(v).orElse(0) + 1);
            int errorLimit = properties.getErrorLimit();
            log.warn("Collectors::{}:     Exception while collecting metrics from node: {}, #errors={}, exception: {}",
                    collectorId, nodeAddress, errors, getExceptionMessages(t));
            log.debug("Collectors::{}: Exception while collecting metrics from node: {}, #errors={}\n", collectorId, nodeAddress, errors, t);

            sendEvent(ABSTRACT_ENDPOINT_CONN_ERROR, nodeAddress, "errors="+errors);

            if (errorLimit<=0 || errors >= errorLimit) {
                log.warn("Collectors::{}: Too many consecutive errors occurred while attempting to collect metrics from node: {}, num-of-errors={}", collectorId, nodeAddress, errors);
                log.warn("Collectors::{}: Pausing collection from Node: {}", collectorId, nodeAddress);
                ignoredNodes.put(nodeAddress, null);
                sendEvent(ABSTRACT_ENDPOINT_NODE_FAILED, nodeAddress);
            }
            return COLLECTION_RESULT.ERROR;
        }
    }

    private String getExceptionMessages(Throwable t) {
        StringBuilder sb = new StringBuilder();
        while (t!=null) {
            sb.append(" -> ").append(t.getClass().getName()).append(": ").append(t.getMessage());
            t = t.getCause();
        }
        return sb.substring(4);
    }

    private void sendEvent(String topic, String nodeAddress, String...extra) {
        Map<String,String> message = new HashMap<>();
        message.put("address", nodeAddress);
        for (String e : extra) {
            String[] s = e.split("[:=]", 2);
            if (s.length==2 && StringUtils.isNotBlank(s[0]))
                message.put(s[0].trim(), s[1]);
        }
        eventBus.send(topic, message, getClass().getName());
    }

    protected abstract ResponseEntity<T> getData(String url);
    protected abstract void processData(T data, String nodeAddress, ProcessingStats stats);

    private void _collectAndPublishData(String nodeAddress) {
        String url;
        if (StringUtils.isBlank(nodeAddress)) {
            // Local node data collection URL
            url = properties.getUrl();
            if (StringUtils.isBlank(url))
                url = String.format(properties.getUrlOfNodesWithoutClient(), "127.0.0.1");
        } else {
            // Remote node data collection URL
            url = String.format(properties.getUrlOfNodesWithoutClient(), nodeAddress);
        }
        log.info("Collectors::{}:   Collecting data from url: {}", collectorId, url);

        log.debug("Collectors::{}: Collecting data: {}...", collectorId, url);
        long startTm = System.currentTimeMillis();
        ResponseEntity<T> response = getData(url);
        long callEndTm = System.currentTimeMillis();
        log.trace("Collectors::{}: ...response: {}", collectorId, response);

        if (response.getStatusCode()==HttpStatus.OK) {
            T data = response.getBody();
            ProcessingStats stats = new ProcessingStats();

            log.trace("Collectors::{}: Processing data started: data: {}", collectorId, data);
            processData(data, nodeAddress, stats);
            log.trace("Collectors::{}: Processing data completed: stats: {}", collectorId, stats);

            long endTm = System.currentTimeMillis();
            log.debug("Collectors::{}: Collecting data...ok", collectorId);
            //log.info("Collectors::{}:     Metrics: extracted={}, published={}, failed={}", collectorId,
            //        stats.countSuccess + stats.countErrors, stats.countSuccess, stats.countErrors);
            if (log.isInfoEnabled())
                log.info("Collectors::{}:     Publish statistics: {}", collectorId, stats);
            log.debug("Collectors::{}:     Durations: rest-call={}, extract+publish={}, total={}", collectorId,
                    callEndTm-startTm, endTm-callEndTm, endTm-startTm);
        } else {
            log.warn("Collectors::{}: Collecting data...failed: Http Status: {}", collectorId, response.getStatusCode());
        }
    }

    protected CollectorContext.PUBLISH_RESULT publishMetricEvent(String metricName, double metricValue, long timestamp, String nodeAddress) {
        EventMap event = new EventMap(metricValue, 1, timestamp);
        return publishMetricEvent(metricName, event, nodeAddress);
    }

    protected CollectorContext.PUBLISH_RESULT publishMetricEvent(String metricName, EventMap event, String nodeAddress) {
        boolean createTopic = properties.isCreateTopic();
        try {
            String originalTopic = metricName;
            boolean createDestination = (createTopic || allowedTopics!=null && allowedTopics.contains(metricName));
            if (topicMap!=null) {
                String targetTopic = topicMap.get(metricName);
                if (targetTopic!=null && !targetTopic.isEmpty())
                    metricName = targetTopic;
            }
            event.setEventProperty(EmsConstant.EVENT_PROPERTY_SOURCE_ADDRESS, nodeAddress);
            event.getEventProperties().put(EmsConstant.EVENT_PROPERTY_EFFECTIVE_DESTINATION, metricName);
            event.getEventProperties().put(EmsConstant.EVENT_PROPERTY_ORIGINAL_DESTINATION, originalTopic);
            log.debug("Collectors::{}:    Publishing metric: {}: {}", collectorId, metricName, event.getMetricValue());
            CollectorContext.PUBLISH_RESULT result = collectorContext.sendEvent(null, metricName, event, createDestination);
            log.trace("Collectors::{}:    Publishing metric: {}: {} -> result: {}", collectorId, metricName, event.getMetricValue(), result);
            return result;
        } catch (Exception e) {
            log.warn("Collectors::{}:    Publishing metric failed: ", collectorId, e);
            return CollectorContext.PUBLISH_RESULT.ERROR;
        }
    }

    protected void updateStats(CollectorContext.PUBLISH_RESULT publishResult, ProcessingStats stats) {
        if (publishResult==CollectorContext.PUBLISH_RESULT.SENT) stats.countSuccess++;
        else if (publishResult==CollectorContext.PUBLISH_RESULT.SKIPPED) stats.countSkipped++;
        else if (publishResult==CollectorContext.PUBLISH_RESULT.ERROR) stats.countErrors++;
    }

    protected static class ProcessingStats {
        public int countSuccess;
        public int countErrors;
        public int countSkipped;

        public int getCountTotal() {
            return countSuccess+countSkipped+countErrors;
        }

        public String toString() {
            return String.format("extracted: %d, published: %d, skipped: %d, failed: %d", getCountTotal(), countSuccess, countSkipped, countErrors);
        }
    }
}
