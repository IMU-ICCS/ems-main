/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
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
import eu.melodic.event.util.GROUPING;
import eu.melodic.event.util.GroupingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collects measurements from Netdata server
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NetdataCollector implements Collector, InitializingBean, Runnable {
    private final NetdataCollectorProperties properties;
    private final CommandExecutor commandExecutor;

    private RestTemplate restTemplate = new RestTemplate();
    private boolean started;
    private Thread runner;
    private boolean running;
    private List<String> allowedTopics;
    private Map<String, String> topicMap;

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

        // start thread
        runner = new Thread(this, "baguette-client-collector-netdata-thread");
        runner.setDaemon(true);
        started = true;
        running = true;
        runner.start();

        log.info("Collectors::Netdata: Started");
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("Collectors::Netdata: Not started");
            return;
        }
        running = false;
        // interrupt sleep
        runner.interrupt();
    }

    public void run() {
        if (!started) return;

        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                // collect data
                collectAndPublishData();

                // sleep for 'delay' millis
                Thread.sleep(properties.getDelay());
            } catch (InterruptedException e) {
                log.warn("Collectors::Netdata: Interrupted");
            } catch (Throwable t) {
                log.warn("Collectors::Netdata: Exception: {}", t);
            }
        }

        synchronized (this) {
            log.info("Collectors::Netdata: Stopped");
            started = false;
            running = false;
        }
    }

    private void collectAndPublishData() {
        log.info("Collectors::Netdata: Collecting data: {}...", properties.getUrl());
        long startTm = System.currentTimeMillis();
        ResponseEntity<HashMap> response = restTemplate.getForEntity(properties.getUrl(), HashMap.class);
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
                    String metricName = ("netdata."+key.toString()+"."+dimKey.toString()).replace(".", "__");
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
            log.info("Collectors::Netdata: Collecting data...ok");
            log.info("Collectors::Netdata:     Metrics: extracted={}, published={}, failed={}",
                    countSuccess+countErrors, countSuccess, countErrors);
            log.info("Collectors::Netdata:     Durations: rest-call={}, extract+publish={}, total={}",
                    callEndTm-startTm, endTm-callEndTm, endTm-startTm);
        } else {
            log.warn("Collectors::Netdata: Collecting data...failed: Http Status: {}", response.getStatusCode());
        }
    }
}
