/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.collector.netdata;

import eu.melodic.event.baguette.client.Collector;
import eu.melodic.event.baguette.client.CommandExecutor;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * Collects measurements from Netdata server
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NetdataCollector implements Collector, InitializingBean, Runnable {
    public final static String NETDATA_COLLECTION_START = "NETDATA_COLLECTION_START";
    public final static String NETDATA_COLLECTION_END = "NETDATA_COLLECTION_END";
    public final static String NETDATA_CONN_OK = "NETDATA_CONN_OK";
    public final static String NETDATA_CONN_ERROR = "NETDATA_CONN_ERROR";
    public final static String NETDATA_CONN_ERROR_TEMP = "NETDATA_CONN_ERROR_TEMP";
    public final static String NETDATA_NODE_PAUSED = "NETDATA_NODE_PAUSED";
    public final static String NETDATA_NODE_RESUMED = "NETDATA_NODE_RESUMED";

    private final NetdataCollectorProperties properties;
    private final CommandExecutor commandExecutor;
    private final TaskScheduler taskScheduler;
    private final EventBus<String,Object,Object> eventBus;

    private RestTemplate restTemplate = new RestTemplate();
    private boolean started;
    private ScheduledFuture<?> runner;
    private List<String> allowedTopics;
    private Map<String, String> topicMap;

    private Map<String, Integer> errorsMap = new HashMap<>();
    private Map<String,ScheduledFuture<?>> ignoredNodes = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        log.debug("Collectors::Netdata: properties: {}", properties);
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

        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
        HashSet<String> topics = new HashSet<>();
        for (String g : GROUPING.getNames()) {
            GroupingConfiguration grp = commandExecutor.getGroupings().get(g);
            if (grp!=null)
                topics.addAll(grp.getEventTypeNames());
        }
        log.warn("Collectors::Netdata: activeGroupingChanged: New Allowed Topics for active grouping: {} -- {}", newGrouping, topics);
        List<String> tmpList = new ArrayList<>(topics);
        Map<String,String> tmpMap = null;
        if (properties.getAllowedTopics()!=null) {
            tmpMap = properties.getAllowedTopics().stream()
                    .map(s -> s.split(":", 2))
                    .collect(Collectors.toMap(a -> a[0], a -> a.length>1 ? a[1]: ""));
        }
        log.warn("Collectors::Netdata: activeGroupingChanged: New Allowed Topics -- Topics Map: {} -- {}", tmpList, tmpMap);
        synchronized (this) {
            this.allowedTopics = tmpList;
            this.topicMap = tmpMap;
        }
    }

    public synchronized void start() {
        // check if already running
        if (started) {
            log.warn("Collectors::Netdata: Already started");
            return;
        }

        // check parameters
        if (properties==null || !properties.isEnable()) {
            log.warn("Collectors::Netdata: Collector not enabled");
            return;
        }
        if (properties.getDelay()<0) properties.setDelay(0);
        if (StringUtils.isBlank(properties.getUrl())) {
            String url = "http://127.0.0.1:19999/api/v1/allmetrics?format=json";
            log.debug("Collectors::Netdata: URL not specified. Assuming {}", url);
            properties.setUrl(url);
        }

        log.info("Collectors::Netdata: configuration: {}", properties);

        // Schedule collection execution
        errorsMap.clear();
        ignoredNodes.clear();
        runner = taskScheduler.scheduleWithFixedDelay(this, properties.getDelay());
        started = true;

        log.info("Collectors::Netdata: Started");
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("Collectors::Netdata: Not started");
            return;
        }

        // Cancel collection execution
        started = false;
        runner.cancel(true);
        runner = null;
        ignoredNodes.values().forEach(task -> task.cancel(true));
        log.info("Collectors::Netdata: Stopped");
    }

    public void run() {
        if (!started) return;

        log.trace("Collectors::Netdata: run(): BEGIN");
        if (log.isTraceEnabled()) {
            log.trace("Collectors::Netdata: run(): errors-map={}", errorsMap);
            log.trace("Collectors::Netdata: run(): ignored-nodes={}", ignoredNodes.keySet());
        }

        // collect data from local node
        log.info("Collectors::Netdata: Collecting metrics from local node...");
        collectAndPublishData("");

        // if Aggregator, collect data from nodes without client
        log.trace("Collectors::Netdata: Nodes without clients in Zone: {}",
                commandExecutor.getClientConfiguration()!=null
                        ? commandExecutor.getClientConfiguration().getNodesWithoutClient() : null);
        log.trace("Collectors::Netdata: Is Aggregator: {}", commandExecutor.isAggregator());
        if (commandExecutor.isAggregator()) {
            if (commandExecutor.getClientConfiguration().getNodesWithoutClient().size()>0) {
                log.info("Collectors::Netdata: Collecting metrics from remote nodes (without EMS client): {}",
                        commandExecutor.getClientConfiguration().getNodesWithoutClient());
                for (Serializable nodeAddress : commandExecutor.getClientConfiguration().getNodesWithoutClient()) {
                    // collect data from remote node
                    collectAndPublishData(nodeAddress.toString());
                }
            } else
                log.debug("Collectors::Netdata: No remote nodes (without EMS client)");
        }

        log.trace("Collectors::Netdata: run(): END");
    }

    private void collectAndPublishData(@NonNull String nodeAddress) {
        if (ignoredNodes.containsKey(nodeAddress)) {
            log.debug("Collectors::Netdata: Node is in ignore list: {}", nodeAddress);
            return;
        }
        try {
            sendEvent(NETDATA_COLLECTION_START, nodeAddress);
            _collectAndPublishData(nodeAddress);
            sendEvent(NETDATA_COLLECTION_END, nodeAddress);

            if (Optional.ofNullable(errorsMap.put(nodeAddress, 0)).orElse(0)>0)
                sendEvent(NETDATA_CONN_OK, nodeAddress);
        } catch (Throwable t) {
            int errors = errorsMap.compute(nodeAddress, (k, v) -> Optional.ofNullable(v).orElse(0) + 1);
            int errorLimit = properties.getErrorLimit();
            int pausePeriod = properties.getPausePeriod();
            log.warn("Collectors::Netdata:     Exception while collecting metrics from node: {}, #errors={}, exception: {}",
                    nodeAddress, errors, getExceptionMessages(t));
            log.debug("Collectors::Netdata: Exception while collecting metrics from node: {}, #errors={}\n", nodeAddress, errors, t);

            sendEvent(NETDATA_CONN_ERROR_TEMP, nodeAddress, "errors="+errors);

            if (errorLimit>0 && pausePeriod>0) {
                if (errors >= errorLimit) {
                    log.warn("Collectors::Netdata: Too many consecutive errors occurred while attempting to collect metrics from node: {}, num-of-errors={}", nodeAddress, errors);
                    log.warn("Collectors::Netdata: Will pause metrics collection from node for {} seconds: {}", pausePeriod, nodeAddress);
                    ignoredNodes.put(nodeAddress, taskScheduler.schedule(() -> {
                        errorsMap.put(nodeAddress, 0);
                        ignoredNodes.remove(nodeAddress);
                        log.info("Collectors::Netdata: Resumed metrics collection from node: {}", nodeAddress);
                        sendEvent(NETDATA_NODE_RESUMED, nodeAddress);
                    }, Instant.now().plusSeconds(pausePeriod)));

                    sendEvent(NETDATA_CONN_ERROR, nodeAddress);
                    sendEvent(NETDATA_NODE_PAUSED, nodeAddress);
                }
            } else
                log.debug("Collectors::Netdata: Metrics collection pausing is disabled");
        }
    }

    private String getExceptionMessages(Throwable t) {
        StringBuilder sb = new StringBuilder();
        while (t!=null) {
            sb.append(" -> ").append(t.getClass().getName()).append(": ").append(t.getMessage());
            t = t.getCause();
        }
        return sb.toString().substring(4);
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
        log.info("Collectors::Netdata:   Collecting data from url: {}", url);

        log.debug("Collectors::Netdata: Collecting data: {}...", url);
        long startTm = System.currentTimeMillis();
        ResponseEntity<HashMap> response = restTemplate.getForEntity(url, HashMap.class);
        long callEndTm = System.currentTimeMillis();
        log.trace("Collectors::Netdata: ...response: {}", response);
        if (response.getStatusCode()==HttpStatus.OK) {
            Map dataMap = response.getBody();
            boolean createTopic = properties.isCreateTopic();
            int countSuccess = 0;
            int countErrors = 0;
            log.trace("Collectors::Netdata: ...keys: {}", dataMap.keySet());
            for (Object key : dataMap.keySet()) {
                log.trace("Collectors::Netdata: ...Loop-1: key={}", key);
                if (key==null) continue;
                Map keyData = (Map)dataMap.get(key);
                log.trace("Collectors::Netdata: ...Loop-1: key-data={}", keyData);
                long timestamp = Long.parseLong( keyData.get("last_updated").toString() );
                Map dimensionsMap = (Map)keyData.get("dimensions");

                log.trace("Collectors::Netdata: ...Loop-1: ...dimensions-keys: {}", dimensionsMap.keySet());
                for (Object dimKey : dimensionsMap.keySet()) {
                    log.trace("Collectors::Netdata: ...Loop-1: ...dimensions-key: {}", dimKey);
                    if (dimKey==null) continue;
                    String metricName = ("netdata."+ key + "."+ dimKey).replace(".", "__");
                    log.trace("Collectors::Netdata: ...Loop-1: ...metric-name: {}", metricName);
                    Map dimData = (Map)dimensionsMap.get(dimKey);
                    Object valObj = dimData.get("value");
                    log.trace("Collectors::Netdata: ...Loop-1: ...metric-value: {}", valObj);
                    if (valObj!=null) {
                        double metricValue = Double.parseDouble(valObj.toString());
                        log.trace("Collectors::Netdata:           {} = {}", metricName, metricValue);
                        try {
                            boolean createDestination = (createTopic || allowedTopics!=null && allowedTopics.contains(metricName));
                            if (topicMap!=null) {
                                String targetTopic = topicMap.get(metricName);
                                if (targetTopic!=null && !targetTopic.isEmpty())
                                    metricName = targetTopic;
                            }
                            EventMap event = new EventMap(metricValue, 1, timestamp);
                            event.setEventProperty(EmsConstant.EVENT_PROPERTY_SOURCE_ADDRESS, nodeAddress);
                            log.debug("Collectors::Netdata:     {}: {}", metricName, metricValue);
                            if (commandExecutor.sendEvent(null, metricName, event, createDestination))
                                countSuccess++;
                        } catch (Exception e) {
                            log.warn("Collectors::Netdata: Publishing netdata metric failed: ", e);
                            countErrors++;
                        }
                    }
                }

                if (Thread.currentThread().isInterrupted()) break;
            }
            long endTm = System.currentTimeMillis();
            log.debug("Collectors::Netdata: Collecting data...ok");
            log.info("Collectors::Netdata:     Metrics: extracted={}, published={}, failed={}",
                    countSuccess+countErrors, countSuccess, countErrors);
            log.debug("Collectors::Netdata:     Durations: rest-call={}, extract+publish={}, total={}",
                    callEndTm-startTm, endTm-callEndTm, endTm-startTm);
        } else {
            log.warn("Collectors::Netdata: Collecting data...failed: Http Status: {}", response.getStatusCode());
        }
    }
}
