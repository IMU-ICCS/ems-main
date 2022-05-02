/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.common.misc.SystemResourceMonitor;
import eu.melodic.event.control.ControlServiceCoordinator;
import eu.melodic.event.control.properties.ControlServiceProperties;
import eu.melodic.event.control.properties.InfoServiceProperties;
import eu.melodic.event.control.properties.StaticResourceProperties;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.FunctionDefinition;
import eu.melodic.event.util.GROUPING;
import eu.melodic.event.util.NetUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private final ControlServiceProperties controlServiceProperties;
    private final InfoServiceProperties infoServiceProperties;
    private final ControlServiceCoordinator controlServiceCoordinator;
    private final StaticResourceProperties staticResourceProperties;

    private final BuildInfoProvider buildInfoProvider;
    private final SystemInfoProvider systemInfoProvider;
    private final BrokerCepService brokerCepService;
    private final SystemResourceMonitor systemResourceMonitor;

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
            if (System.currentTimeMillis() - timestamp < infoServiceProperties.getMetricsUpdateInterval()) {
                log.debug("updateServerMetricValues(): STOP: Retry in {}ms",
                        timestamp + infoServiceProperties.getMetricsUpdateInterval() - System.currentTimeMillis());
                return;
            }
        }

        long timestamp = System.currentTimeMillis();
        log.trace("updateServerMetricValues(): new-timestamp: {}", timestamp);

        Map<String,Object> metrics = new LinkedHashMap<>();

        metrics.put("ip-address", controlServiceCoordinator.getServerIpAddress());
        metrics.put("public-ip-address", NetUtil.getPublicIpAddress());
        metrics.put("default-ip-address", NetUtil.getDefaultIpAddress());
        metrics.put("reference", controlServiceCoordinator.getReference());

        // Collect JVM and System resource metrics for EMS server
        Map<String,Object> systemInfo = new LinkedHashMap<>();
        systemInfo.put("jmx-resource-metrics", systemInfoProvider.getMetricValues());
        systemInfo.put("system-resource-metrics", systemResourceMonitor.getLatestMeasurements());
        metrics.put(SYSTEM_INFO_PROVIDER, systemInfo);

        // Collect EMS build info
        if (includeStaticInfo)
            metrics.put(BUILD_INFO_PROVIDER, buildInfoProvider.getMetricValues());

        // Collect Control Service metrics
        Map<String,Object> controlServiceInfo = new LinkedHashMap<>();
        controlServiceInfo.put("current-ems-state", controlServiceCoordinator.getCurrentEmsState());
        controlServiceInfo.put("current-ems-state-message", controlServiceCoordinator.getCurrentEmsStateMessage());
        controlServiceInfo.put("current-ems-state-change-timestamp", controlServiceCoordinator.getCurrentEmsStateChangeTimestamp());
        controlServiceInfo.put("current-camel-model-id", controlServiceCoordinator.getCurrentCamelModelId());
        controlServiceInfo.put("current-cp-model-id", controlServiceCoordinator.getCurrentCpModelId());
        if (controlServiceProperties!=null && infoServiceProperties!=null) {
            controlServiceInfo.put("prop-ip-setting", controlServiceProperties.getIpSetting());
            controlServiceInfo.put("prop-esb-url", controlServiceProperties.getEsbUrl());
            controlServiceInfo.put("prop-metasolver-config-url", controlServiceProperties.getMetasolverConfigurationUrl());
            controlServiceInfo.put("prop-metrics-update-interval", infoServiceProperties.getMetricsUpdateInterval());
            controlServiceInfo.put("prop-metrics-client-update-interval", infoServiceProperties.getMetricsClientUpdateInterval());
            controlServiceInfo.put("prop-metrics-stream-event-name", infoServiceProperties.getMetricsStreamEventName());
            controlServiceInfo.put("prop-metrics-stream-update-interval", infoServiceProperties.getMetricsStreamUpdateInterval());
            controlServiceInfo.put("prop-executionware", controlServiceProperties.getExecutionware().toString());
            controlServiceInfo.put("prop-preload-camel-model", controlServiceProperties.getPreloadCamelModel());
            controlServiceInfo.put("prop-preload-cp-model", controlServiceProperties.getPreloadCpModel());
            controlServiceInfo.put("prop-upperware-grouping", controlServiceProperties.getUpperwareGrouping());
            controlServiceInfo.put("prop-tc-load-file", controlServiceProperties.getTcLoadFile());
            controlServiceInfo.put("prop-tc-save-file", controlServiceProperties.getTcSaveFile());

            Map<String,Object> debugFlags = new LinkedHashMap<>();
            debugFlags.put("event-debug-enabled",  controlServiceProperties.isEventDebugEnabled());
            debugFlags.put("exit-allowed",  controlServiceProperties.isExitAllowed());
            debugFlags.put("print-build-info",  controlServiceProperties.isPrintBuildInfo());
            debugFlags.put("skip-translation",  controlServiceProperties.isSkipTranslation());
            debugFlags.put("skip-broker-cep-init",  controlServiceProperties.isSkipBrokerCep());
            debugFlags.put("skip-baguette-server-init",  controlServiceProperties.isSkipBaguette());
            debugFlags.put("skip-mvv-retrieve",  controlServiceProperties.isSkipMvvRetrieve());
            debugFlags.put("skip-metasolver-configuration",  controlServiceProperties.isSkipMetasolver());
            debugFlags.put("skip-esb-notification",  controlServiceProperties.isSkipEsbNotification());
            controlServiceInfo.put("prop-debug-flags",debugFlags);
        }
        if (staticResourceProperties!=null) {
            Map<String,Object> staticResourceCfg = new LinkedHashMap<>();
            staticResourceCfg.put("favicon-context",  staticResourceProperties.getFaviconContext());
            staticResourceCfg.put("favicon-path",  staticResourceProperties.getFaviconPath());
            staticResourceCfg.put("resource-context",  staticResourceProperties.getResourceContext());
            staticResourceCfg.put("resource-path",  staticResourceProperties.getResourcePath());
            staticResourceCfg.put("resource-redirect",  staticResourceProperties.getResourceRedirect());
            staticResourceCfg.put("resource-redirects",  staticResourceProperties.getResourceRedirects());
            staticResourceCfg.put("logs-context",  staticResourceProperties.getLogsContext());
            staticResourceCfg.put("logs-path",  staticResourceProperties.getLogsPath());
            controlServiceInfo.put("prop-static-resource", staticResourceCfg);
        }
        metrics.put(CONTROL_INFO_PROVIDER, controlServiceInfo);

        // Collect Broker-CEP metrics
        metrics.put(BROKER_CEP_INFO_PROVIDER, brokerCepService.getBrokerCepStatistics());

        // Collect Baguette-Client metrics and topology
        Map<String,Object> baguetteServerInfo = new LinkedHashMap<>();
        baguetteServerInfo.put("active-clients-list", controlServiceCoordinator.clientList());
        baguetteServerInfo.put("active-clients-map", controlServiceCoordinator.clientMap());
        baguetteServerInfo.put("passive-clients-list", controlServiceCoordinator.passiveClientList());
        baguetteServerInfo.put("passive-clients-map", controlServiceCoordinator.passiveClientMap());
        metrics.put(BAGUETTE_SERVER_INFO_PROVIDER, baguetteServerInfo);

        // Destinations per grouping and min/max grouping
        Map<String,Object> translatorInfo = new LinkedHashMap<>();
        metrics.put(TRANSLATOR_INFO_PROVIDER, translatorInfo);
        String camelModelId = controlServiceCoordinator.getCurrentCamelModelId();
        if (StringUtils.isNotBlank(camelModelId)) {
            TranslationContext _TC = controlServiceCoordinator.getTranslationContextOfCamelModel(camelModelId);
            Set<String> groupings = _TC.G2T.keySet();
            ArrayList<String> orderedGroupings = new ArrayList<>(groupings);
            orderedGroupings.sort((o1, o2) -> {
                GROUPING g1 = GROUPING.valueOf(o1);
                GROUPING g2 = GROUPING.valueOf(o2);
                return g1.compareTo(g2);
            });
            translatorInfo.put("camel-model-id", camelModelId);
            translatorInfo.put("groupings", orderedGroupings);
            translatorInfo.put("actions-per-event", _TC.E2A);
            translatorInfo.put("slo", _TC.SLO);
            translatorInfo.put("monitors", _TC.MONS);
            translatorInfo.put("rules-per-grouping", _TC.G2R);
            translatorInfo.put("destinations-per-grouping", _TC.G2T);
            translatorInfo.put("composite-metric-variables", _TC.CMVAR);
            translatorInfo.put("metric-variable-values", _TC.MVV);
            translatorInfo.put("metric-variable-values-for-CP", _TC.MVV_CP);
            translatorInfo.put("destination-connections", _TC.getTopicConnections());
            translatorInfo.put("function-definitions", _TC.FUNC.stream()
                    .map(FunctionDefinition::toString).collect(Collectors.toList()));
        }

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
        // Not really needed check, since clients PUSH their statistics to server
        if (currentClientMetrics!=null) {
            long timestamp = (long) currentClientMetrics.get(".timestamp");
            log.trace("updateClientMetricValues(): stored-timestamp: {}", timestamp);
            if (System.currentTimeMillis() - timestamp < infoServiceProperties.getMetricsClientUpdateInterval()) {
                log.debug("updateClientMetricValues(): STOP: Retry in {}ms",
                        timestamp + infoServiceProperties.getMetricsClientUpdateInterval() - System.currentTimeMillis());
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
            /*log.trace("updateClientMetricValues(): Requesting metrics from client: {}", clientId);
            Object o = baguetteServer.readFromClient(clientId, "GET-STATS", org.slf4j.event.Level.DEBUG);
            log.trace("updateClientMetricValues(): Metrics from client: {}, metrics: {}", clientId, o);
            if (o instanceof Map) {
                clientMetrics.put(clientId, o);
                log.trace("updateClientMetricValues(): client-metrics: id={}, Client metrics ADDED in results map", clientId);
            }*/

            log.trace("updateClientMetricValues(): Retrieving cached statistics of client: id={}", clientId);
            ClientShellCommand csc = ClientShellCommand.getActiveById(clientId);
            log.trace("updateClientMetricValues(): CSC of client: id={}, CSC={}", clientId, csc);
            if (csc!=null) {
                if (csc.getClientStatistics()!=null) {
                    clientMetrics.put(clientId, csc.getClientStatistics());
                    log.trace("updateClientMetricValues(): client-metrics: id={}, Client metrics ADDED in results map", clientId);
                } else {
                    log.debug("updateClientMetricValues(): No client statistics available: client-id={}", clientId);
                }
            } else {
                log.warn("updateClientMetricValues(): CSC NOT FOUND: client-id={}", clientId);
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
