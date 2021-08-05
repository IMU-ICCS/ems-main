/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.control.ControlServiceCoordinator;
import eu.melodic.event.control.properties.ControlServiceProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmsInfoServiceImpl implements IEmsInfoService {

    private final AtomicLong currentServerMetricsVersion = new AtomicLong(0);
    private final AtomicLong currentClientMetricsVersion = new AtomicLong(0);
    private Map<String,Object> currentServerMetrics;
    private Map<String,Object> currentClientMetrics;

    private final ApplicationContext applicationContext;
    private final ControlServiceProperties properties;
    private final ControlServiceCoordinator controlServiceCoordinator;
    private final BaguetteServer baguetteServer;

    private final BuildInfoProvider buildInfoProvider;
    private final SystemInfoProvider systemInfoProvider;
    private final BrokerCepService brokerCepService;

    @Override
    public void clearServerMetricValues() {
        log.debug("clearServerMetricValues(): BEGIN");
        synchronized (currentServerMetricsVersion) {
            systemInfoProvider.clearMetricValues();
            brokerCepService.clearBrokerCepStatistics();
            currentServerMetrics = null;
        }
        log.debug("clearServerMetricValues(): END");
    }

    @Override
    public Map<String, Object> getServerMetricValues() {
        log.debug("getServerMetricValues(): BEGIN");
        updateServerMetricValues(false);
        log.debug("getServerMetricValues(): END: {}", currentServerMetrics);
        return currentServerMetrics;
    }

    public Map<String,Object> getServerMetricValuesFor(@NonNull String key) {
        log.debug("getServerMetricValuesFor(): BEGIN: key={}", key);
        return (Map<String,Object>) getServerMetricValues().get(key);
    }

    // ------------------------------------------------------------------------

    @Override
    public void clearClientMetricValues() {
        log.debug("clearClientMetricValues(): BEGIN");
        synchronized (currentClientMetricsVersion) {
            currentClientMetrics = null;
            controlServiceCoordinator.clientCommandSend("*", "CLEAR-STATS");
        }
        log.debug("clearClientMetricValues(): END");
    }

    @Override
    public Map<String,Object> getClientMetricValues() {
        log.debug("getClientMetricValues(): BEGIN");
        updateClientMetricValues();
        log.debug("getClientMetricValues(): END: {}", currentClientMetrics);
        return currentClientMetrics;
    }

    @Override
    public Map<String,Object> getClientMetricValues(@NonNull String clientId) {
        log.debug("getClientMetricValues(): BEGIN: clientId={}", clientId);
        return (Map<String,Object>) getClientMetricValues().get(clientId);
    }

    // ------------------------------------------------------------------------

    protected void updateServerMetricValues(boolean includeStaticInfo) {
        log.debug("updateServerMetricValues(): BEGIN: includeStaticInfo={}", includeStaticInfo);
        if (currentServerMetrics!=null) {
            long timestamp = (long) currentServerMetrics.get(".timestamp");
            log.trace("updateServerMetricValues(): stored-timestamp: {}", timestamp);
            if (System.currentTimeMillis() - timestamp < properties.getMetricsUpdateInterval()) {
                log.debug("updateServerMetricValues(): STOP: Retry in {}ms",
                        timestamp+properties.getMetricsUpdateInterval()-System.currentTimeMillis());
                return;
            }
        }

        long timestamp = System.currentTimeMillis();
        log.trace("updateServerMetricValues(): new-timestamp: {}", timestamp);

        Map<String,Object> metrics = new LinkedHashMap<>();

        // Collect System and JVM metrics
        metrics.put(SYSTEM_INFO_PROVIDER, systemInfoProvider.getMetricValues());

        // Collect EMS build info
        if (includeStaticInfo)
            metrics.put(BUILD_INFO_PROVIDER, buildInfoProvider.getMetricValues());

        // Collect Broker-CEP metrics
        metrics.put(BROKER_CEP_INFO_PROVIDER, brokerCepService.getBrokerCepStatistics());

        log.debug("updateServerMetricValues(): Collected server metrics: {}", metrics);

        synchronized (currentServerMetricsVersion) {
            log.trace("updateServerMetricValues(): IN-SYNC-BLOCK");
            if (currentServerMetrics==null || (long)currentServerMetrics.get(".timestamp") < timestamp) {
                long version = currentServerMetricsVersion.getAndIncrement();
                log.trace("updateServerMetricValues(): NEW-VERSION: {}", version);
                metrics.put(".version", version);
                metrics.put(".timestamp", timestamp);
                this.currentServerMetrics = Collections.unmodifiableMap(metrics);
                log.trace("updateServerMetricValues(): NEW currentServerMetrics: {}", currentServerMetrics);
            }
            log.debug("updateServerMetricValues(): END");
        }
    }

    protected void updateClientMetricValues() {
        log.debug("updateClientMetricValues(): BEGIN");
        if (currentClientMetrics!=null) {
            long timestamp = (long) currentClientMetrics.get(".timestamp");
            log.trace("updateClientMetricValues(): stored-timestamp: {}", timestamp);
            if (System.currentTimeMillis() - timestamp < properties.getMetricsClientUpdateInterval()) {
                log.debug("updateClientMetricValues(): STOP: Retry in {}ms",
                        timestamp+properties.getMetricsClientUpdateInterval()-System.currentTimeMillis());
                return;
            }
        }

        long timestamp = System.currentTimeMillis();
        log.trace("updateClientMetricValues(): new-timestamp: {}", timestamp);

        Map<String,Object> clientMetrics = new LinkedHashMap<>();

        // Collecting EMS clients' metrics
        List<String> clientIds = controlServiceCoordinator.clientList();
        log.trace("updateClientMetricValues(): active-baguette-clients: {}", clientIds);
        for (String clientId : clientIds.stream().map(s->s.split(" ")[0]).collect(Collectors.toList())) {
            log.trace("updateClientMetricValues(): Requesting metrics from client: {}", clientId);
            Object o = baguetteServer.readFromClient(clientId, "SHOW-STATS");
            log.trace("updateClientMetricValues(): Metrics from client: {}, metrics: {}", clientId, o);
            if (o instanceof Map) {
                clientMetrics.put(clientId, o);
                log.trace("updateClientMetricValues(): client-metrics: id={}, Client metrics ADDED in results map", clientId);
            }
        }
        log.debug("updateClientMetricValues(): Collected client metrics: {}", clientMetrics);

        synchronized (currentClientMetricsVersion) {
            log.trace("updateClientMetricValues(): IN-SYNC-BLOCK");
            if (currentClientMetrics==null || (long)currentClientMetrics.get(".timestamp") < timestamp) {
                long version = currentClientMetricsVersion.getAndIncrement();
                log.trace("updateClientMetricValues(): NEW-VERSION: {}", version);
                clientMetrics.put(".version", version);
                clientMetrics.put(".timestamp", timestamp);
                this.currentClientMetrics = Collections.unmodifiableMap(clientMetrics);
                log.trace("updateServerMetricValues(): NEW currentClientMetrics: {}", currentClientMetrics);
            }
            log.debug("updateClientMetricValues(): END");
        }
    }
}
