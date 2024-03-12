/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.collector.netdata;

import gr.iccs.imu.ems.baguette.client.Collector;
import gr.iccs.imu.ems.baguette.client.collector.ClientCollectorContext;
import gr.iccs.imu.ems.common.collector.CollectorContext;
import gr.iccs.imu.ems.common.collector.netdata.NetdataCollectorProperties;
import gr.iccs.imu.ems.util.EventBus;
import gr.iccs.imu.ems.util.GROUPING;
import gr.iccs.imu.ems.util.GroupingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Collects measurements from Netdata agents in a Kubernetes cluster
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class K8sNetdataCollector implements Collector, InitializingBean {
    protected final static Set<String> SENSOR_CONFIG_KEYS_EXCLUDED = Set.of("endpoint", "type", "_containerName");
    protected final static String DEFAULT_NETDATA_DATA_API_PATH = "/api/v2/data";

    private final NetdataCollectorProperties properties;
    private final CollectorContext collectorContext;
    private final TaskScheduler taskScheduler;
    private final EventBus<String, Object, Object> eventBus;
    private final List<ScheduledFuture<?>> scheduledFuturesList = new LinkedList<>();
    private boolean started;
    private List<Map<String, Object>> configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!(collectorContext instanceof ClientCollectorContext))
            throw new IllegalArgumentException("Invalid CollectorContext provided. Expected: ClientCollectorContext, but got "+collectorContext.getClass().getName());
    }

    @Override
    public void start() {
        if (started) return;
        if (configuration!=null)
            doStart();
        started = true;
        log.debug("K8sNetdataCollector: Started");
    }

    @Override
    public void stop() {
        if (!started) return;
        started = false;
        doStop();
        log.debug("K8sNetdataCollector: Stopped");
    }

    private synchronized void doStart() {
        log.debug("K8sNetdataCollector: doStart(): BEGIN: configuration={}", configuration);
        log.trace("K8sNetdataCollector: doStart(): BEGIN: scheduledFuturesList={}", scheduledFuturesList);

        // Get Netdata agent address and port from env. vars
        String netdataAddress = null;
        if (StringUtils.isBlank(netdataAddress)) netdataAddress = System.getenv("NETDATA_ADDRESS");
        if (StringUtils.isBlank(netdataAddress)) netdataAddress = System.getenv("NETDATA_IP");
        if (StringUtils.isBlank(netdataAddress)) netdataAddress = System.getenv("HOST_IP");
        if (StringUtils.isBlank(netdataAddress)) netdataAddress = "127.0.0.1";
        log.trace("K8sNetdataCollector: doStart(): netdataAddress={}", netdataAddress);

        int netdataPort = Integer.parseInt(
                StringUtils.defaultIfBlank(System.getenv("NETDATA_PORT"), "19999").trim());
        final String baseUrl = String.format("http://%s:%d", netdataAddress.trim(), netdataPort);
        log.trace("K8sNetdataCollector: doStart(): baseUrl={}", baseUrl);

        // Process each sensor configuration
        AtomicInteger sensorNum = new AtomicInteger(0);
        configuration.forEach(map -> {
            log.debug("K8sNetdataCollector: doStart(): Sensor-{}: map={}", sensorNum.incrementAndGet(), map);

            // Check if it is a Pull sensor. (Push sensors are ignored)
            if ("true".equalsIgnoreCase( get(map, "pushSensor", "false") )) {
                log.debug("K8sNetdataCollector: doStart(): Sensor-{}: It is a Push sensor. Ignoring this sensor", sensorNum.get());
                return;
            }
            // else it is a Pull sensor

            // Get destination (topic) and component name
            String destinationName = get(map, "name", null);
            log.trace("K8sNetdataCollector: doStart(): Sensor-{}: destination={}", sensorNum.get(), destinationName);
            if (StringUtils.isBlank(destinationName)) {
                log.warn("K8sNetdataCollector: doStart(): Sensor-{}: No destination found in sensor config: {}", sensorNum.get(), map);
                return;
            }

            // Get metric URL
            String url = null;
            String component = null;
            if (map.get("configuration") instanceof Map cfgMap) {
                log.trace("K8sNetdataCollector: doStart(): Sensor-{}: cfgMap={}", sensorNum.get(), cfgMap);

                // Get component name
                component = get(cfgMap, "_containerName", null);
                log.trace("K8sNetdataCollector: doStart(): Sensor-{}: component={}", sensorNum.get(), component);

                // Process 'configuration' map entries, to build metric URL
                Map<String, Object> sensorConfig = (Map<String, Object>) cfgMap;
                String endpoint = get(sensorConfig, "endpoint", DEFAULT_NETDATA_DATA_API_PATH);
                log.trace("K8sNetdataCollector: doStart(): Sensor-{}: endpoint={}", sensorNum.get(), endpoint);

                StringBuilder sb = new StringBuilder(endpoint);
                final AtomicBoolean first = new AtomicBoolean(true);
                sensorConfig.forEach((key, value) -> {
                    if (StringUtils.isNotBlank(key) && ! SENSOR_CONFIG_KEYS_EXCLUDED.contains(key)) {
                        if (value instanceof String valueStr) {
                            sb.append(first.get() ? "?" : "&").append(key).append("=").append(valueStr);
                            first.set(false);
                        }
                    }
                });
                url = baseUrl + sb;
            } else {
                log.warn("K8sNetdataCollector: doStart(): Sensor-{}: No sensor configuration found is spec: {}", sensorNum.get(), map);
                return;
            }
            log.trace("K8sNetdataCollector: doStart(): Sensor-{}: Metric url={}", sensorNum.get(), url);

            // Get interval and configure scheduler
            Duration duration = null;
            if (map.get("interval") instanceof Map intervalMap) {
                log.trace("K8sNetdataCollector: doStart(): Sensor-{}: intervalMap={}", sensorNum.get(), intervalMap);
                long period = Long.parseLong(get(intervalMap, "period", "60"));
                if (period>0) {
                    String unitStr = get(intervalMap, "unit", "SECONDS");
                    TimeUnit unit = StringUtils.isNotBlank(unitStr)
                            ? TimeUnit.valueOf(unitStr.toUpperCase().trim()) : TimeUnit.SECONDS;
                    duration = Duration.of(period, unit.toChronoUnit());
                }
            }
            log.trace("K8sNetdataCollector: doStart(): Sensor-{}: duration-from-spec={}", sensorNum.get(), duration);
            if (duration==null) {
                duration = Duration.of(60, ChronoUnit.SECONDS);
            }
            log.trace("K8sNetdataCollector: doStart(): Sensor-{}: duration={}", sensorNum.get(), duration);

            final String url1 = url;
            final String component1 = component;
            scheduledFuturesList.add( taskScheduler.scheduleAtFixedRate(() -> {
                collectData(url1, destinationName, component1);
            }, duration) );
            log.debug("K8sNetdataCollector: doStart(): Sensor-{}: destination={}, component={}, interval={}, url={}",
                    sensorNum.get(), destinationName, component, duration, url);
        });
        log.trace("K8sNetdataCollector: doStart():  scheduledFuturesList={}", scheduledFuturesList);
        log.debug("K8sNetdataCollector: doStart():  END");
    }

    private String get(Map<String,Object> map, String key, String defaultValue) {
        Object valObj;
        if (!map.containsKey(key) || (valObj = map.get(key))==null) return defaultValue;
        String value = valObj.toString();
        if (StringUtils.isBlank(value)) return defaultValue;
        return value;
    }

    private void collectData(String url, String destination, String component) {
        log.warn("!!!!!!!!   K8sNetdataCollector: collectData():  TASK: url={}, destination={}, component={}", url, destination, component);
    }

    private synchronized void doStop() {
        log.debug("K8sNetdataCollector: doStop():  BEGIN");
        log.trace("K8sNetdataCollector: doStop():  BEGIN: scheduledFuturesList={}", scheduledFuturesList);
        // Cancel all task scheduler futures
        scheduledFuturesList.forEach(future -> future.cancel(true));
        scheduledFuturesList.clear();
        log.debug("K8sNetdataCollector: doStop():  END");
    }

    @Override
    public String getName() {
        return "netdata";
    }

    @Override
    public void setConfiguration(Object config) {
        if (config instanceof List sensorConfigList) {
            configuration = sensorConfigList.stream()
                    .filter(o -> o instanceof Map)
                    .filter(map -> ((Map)map).keySet().stream().allMatch(k->k instanceof String))
                    .toList();
            log.info("Collectors::K8sNetdata: setConfiguration: {}", configuration);

            // If configuration changes while collector running we need to restart it
            if (started) {
                log.warn("Collectors::K8sNetdata: setConfiguration: restarting collector");
                stop();
                start();
                log.info("Collectors::K8sNetdata: setConfiguration: restarted collector");
            }
        } else
            log.warn("Collectors::K8sNetdata: setConfiguration: Ignoring unsupported configuration object: {}", config);
    }

    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
        HashSet<String> topics = new HashSet<>();
        for (String g : GROUPING.getNames()) {
            GroupingConfiguration grp = ((ClientCollectorContext)collectorContext).getGroupings().get(g);
            if (grp!=null)
                topics.addAll(grp.getEventTypeNames());
        }
        log.info("Collectors::K8sNetdata: activeGroupingChanged: New Allowed Topics for active grouping: {} -- {}", newGrouping, topics);
//        processAllowedTopics(topics);
    }
}
