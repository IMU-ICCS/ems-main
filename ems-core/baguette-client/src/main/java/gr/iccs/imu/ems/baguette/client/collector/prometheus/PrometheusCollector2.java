/*
 * Copyright (C) 2017-2023 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.collector.prometheus;

import gr.iccs.imu.ems.baguette.client.Collector;
import gr.iccs.imu.ems.common.collector.AbstractEndpointCollector;
import gr.iccs.imu.ems.common.collector.CollectorContext;
import gr.iccs.imu.ems.common.collector.prometheus.PrometheusCollectorProperties;
import gr.iccs.imu.ems.util.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Collects measurements from a Prometheus exporter endpoint
 */
@Slf4j
@Component
public class PrometheusCollector2 extends AbstractEndpointCollector<String> implements Collector {
    protected PrometheusCollectorProperties properties;
    private List<Map<String, Serializable>> configurations = List.of();
    private final List<ScheduledFuture<?>> scrapingTasks = new LinkedList<>();

    @SuppressWarnings("unchecked")
    public PrometheusCollector2(PrometheusCollectorProperties properties, CollectorContext collectorContext, TaskScheduler taskScheduler, EventBus<String,Object,Object> eventBus) {
        super("PrometheusCollector2", properties, collectorContext, taskScheduler, eventBus);
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("Collectors::Prometheus2: properties: {}", properties);
        super.afterPropertiesSet();
    }

    protected ResponseEntity<String> getData(String url) {
        return null;
    }

    protected void processData(String data, String nodeAddress, ProcessingStats stats) {
    }

    public synchronized void start() {
        super.start();
        runner.cancel(true);
    }

    @Override
    public String getName() {
        return "prometheus";
    }

    @Override
    public void setConfiguration(Object config) {
        if (config instanceof List sensorConfigList) {
            configurations = sensorConfigList.stream()
                    .filter(o -> o instanceof Map)
                    .toList();
            applyNewConfigurations();
        }
    }

    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
        log.info("Collectors::Prometheus2: activeGroupingChanged: New Allowed Topics for active grouping: {} -- !! Not used !!", newGrouping);
    }

    private void applyNewConfigurations() {
        log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: {}", configurations);
        if (configurations==null) return;

        // Cancel previous tasks
        if (! scrapingTasks.isEmpty()) {
            log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: Cancelling previous scraping tasks: {}", scrapingTasks);
            List<ScheduledFuture<?>> list = new ArrayList<>(scrapingTasks);
            scrapingTasks.clear();
            list.forEach(task -> task.cancel(true));
            log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: Cancelled previous scraping tasks: {}", scrapingTasks);
        }

        // Create new scraping tasks
        log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: Starting new scraping tasks: configurations: {}", configurations);
        Instant startInstant = Instant.now();
        configurations.forEach(config -> {
            if (checkConfig(config)) {
                String destination = config.getOrDefault("name", "").toString();
                String prometheusMetric = getPrometheusMetric(config);
                String url = getUrlPattern(config);
                Duration delay = getDelay(config);
                Duration period = getInterval(config);
                Instant startsAt = startInstant.plus(delay);

                scrapingTasks.add(taskScheduler.scheduleAtFixedRate(
                        () -> scrapeEndpoint(url, prometheusMetric, destination), startsAt, period));
                log.info("Collectors::Prometheus2: Added monitoring task: prometheus-metric={}, destination={}, url={}, starts-at={}, period={}",
                        prometheusMetric, destination, url, startsAt, period);
            } else
                log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: Skipped sensor: {}", config);
        });
        log.info("!!!!!!!!!!!!!    Collectors::Prometheus2: applyNewConfigurations: Started new scraping tasks: {}", scrapingTasks);
    }

    private boolean checkConfig(Map<String, Serializable> config) {
        List<String> errors = new ArrayList<>();
        String type = config.getOrDefault("type", "").toString();
        if (! getName().equalsIgnoreCase(type)) errors.add(String.format("Type mismatch. Expected '%s' but found '%s'", getName(), type));

        String push = config.getOrDefault("push", "").toString();
        if (! "false".equalsIgnoreCase(push)) errors.add(String.format("Not a Pull sensor. Expected '%s' but found '%s'", false, push));

        String destination = config.getOrDefault("name", "").toString();
        if (StringUtils.isBlank(destination)) errors.add("No destination (name) provided");

        if (config.get("configuration") instanceof Map configMap) {
            int port = Integer.parseInt( configMap.getOrDefault("port", "0").toString() );
            if (port<=0) errors.add("No or invalid port provided: "+port);

            String prometheusMetric = configMap.getOrDefault("metric", "").toString();
            if (StringUtils.isBlank(prometheusMetric)) errors.add("No prometheus metric provided");
        } else
            errors.add("No 'configuration' sub-map found in sensor spec: "+config);

        // If no errors found return true
        if (errors.isEmpty()) return true;

        // Print errors and return false
        log.warn("Collectors::Prometheus2: checkConfig: Sensor specification has errors: spec={}, errors={}", config, errors);
        return false;
    }

    private String getPrometheusMetric(Map<String, Serializable> config) {
        if (config.get("configuration") instanceof Map configMap) {
            String prometheusMetric = configMap.getOrDefault("metric", "").toString();
            if (StringUtils.isNotBlank(prometheusMetric))
                return prometheusMetric;
        }
        return null;
    }

    private String getUrlPattern(Map<String, Serializable> config) {
        if (config.get("configuration") instanceof Map configMap) {
            int port = Integer.parseInt( configMap.getOrDefault("port", "0").toString() );
            String endpoint = configMap.getOrDefault("endpoint", "/").toString();
            return "http://%s:"+port+endpoint;
        }
        return null;
    }

    private Duration getDelay(Map<String, Serializable> config) {
        if (config.get("configuration") instanceof Map configMap) {
            long delay = Long.parseLong(configMap.getOrDefault("delay", "0").toString());
            if (delay>=0)
                return Duration.ofSeconds(delay);
        }
        return Duration.ofSeconds(0);
    }

    private Duration getInterval(Map<String, Serializable> config) {
        if (config.get("interval") instanceof Map intervalMap) {
            long period = Long.parseLong(intervalMap.getOrDefault("period", "60").toString());
            ChronoUnit unit = ChronoUnit.valueOf(intervalMap.getOrDefault("unit", "SECONDS").toString().toUpperCase());
            if (period>0)
                return Duration.of(period, unit);
        }
        return Duration.ofSeconds(60);
    }

    private void scrapeEndpoint(String url, String prometheusMetric, String destination) {
        log.warn("!!!!!!!!!   TODO:  Scraping Prometheus endpoints for sensor: url-pattern={}, prometheusMetric={}, destination={}",
                url, prometheusMetric, destination);
    }
}
