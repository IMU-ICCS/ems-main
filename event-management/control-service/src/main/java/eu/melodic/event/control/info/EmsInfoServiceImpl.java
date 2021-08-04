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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmsInfoServiceImpl implements IEmsInfoService {

    private final AtomicLong currentMetricsVersion = new AtomicLong(0);
    private final AtomicLong currentClientMetricsVersion = new AtomicLong(0);
    private Map<String,Object> currentMetrics;
    private Map<String,Object> currentClientMetrics;

    private final ApplicationContext applicationContext;
    private final ControlServiceProperties properties;
    private final ControlServiceCoordinator controlServiceCoordinator;
    private final BaguetteServer baguetteServer;

    private final BuildInfoProvider buildInfoProvider;
    private final SystemInfoProvider systemInfoProvider;
    private final BrokerCepService brokerCepService;

    @Override
    public synchronized void clearMetricValues() {
        log.debug("clearMetricValues(): BEGIN");
        systemInfoProvider.clearMetricValues();
        brokerCepService.clearBrokerCepStatistics();
        currentMetrics = null;
        log.debug("clearMetricValues(): END");
    }

    @Override
    public synchronized void clearClientMetricValues() {
        log.debug("clearClientMetricValues(): BEGIN");
        currentClientMetrics = null;
        controlServiceCoordinator.clientCommandSend("*", "CLEAR-STATS");
        log.debug("clearClientMetricValues(): END");
    }

    @Override
    public Map<String, Object> getMetricValues() {
        updateMetricValues(false);
        return currentMetrics;
    }

    public Map<String,Object> getMetricValuesFor(String key) {
        return (Map<String,Object>) getMetricValues().get(key);
    }

    @Override
    public Map<String,Object> getClientMetricValues() {
        updateClientMetricValues();
        return currentClientMetrics;
    }

    @Override
    public Map<String,Object> getClientMetricValues(@NonNull String clientId) {
        return (Map<String,Object>) getClientMetricValues().get(clientId);
    }

    // ------------------------------------------------------------------------

    protected void updateMetricValues(boolean includeStaticInfo) {
        if (currentMetrics!=null) {
            long timestamp = (long) currentMetrics.get(".timestamp");
            if (System.currentTimeMillis() - timestamp < properties.getMetricsUpdateInterval())
                return;
        }

        long timestamp = System.currentTimeMillis();

        Map<String,Object> metrics = new LinkedHashMap<>();

        // Collect System and JVM metrics
        metrics.put(SYSTEM_INFO_PROVIDER, systemInfoProvider.getMetricValues());

        // Collect EMS build info
        if (includeStaticInfo)
            metrics.put(BUILD_INFO_PROVIDER, buildInfoProvider.getMetricValues());

        // Collect Broker-CEP metrics
        metrics.put(BROKER_CEP_INFO_PROVIDER, brokerCepService.getBrokerCepStatistics());

        synchronized (currentMetricsVersion) {
            if (currentMetrics==null || (long)currentMetrics.get(".timestamp") < timestamp) {
                long version = currentMetricsVersion.getAndIncrement();
                metrics.put(".version", version);
                metrics.put(".timestamp", timestamp);
                this.currentMetrics = metrics;
            }
        }
    }

    protected void updateClientMetricValues() {
        if (currentClientMetrics!=null) {
            long timestamp = (long) currentClientMetrics.get(".timestamp");
            if (System.currentTimeMillis() - timestamp < properties.getMetricsClientUpdateInterval())
                return;
        }

        long timestamp = System.currentTimeMillis();

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
            }
        }
        log.debug("updateClientMetricValues(): client-metrics: {}", clientIds);

        synchronized (currentClientMetricsVersion) {
            if (currentClientMetrics==null || (long)currentClientMetrics.get(".timestamp") < timestamp) {
                long version = currentClientMetricsVersion.getAndIncrement();
                clientMetrics.put(".version", version);
                clientMetrics.put(".timestamp", timestamp);
                this.currentClientMetrics = clientMetrics;
            }
        }
    }
}
