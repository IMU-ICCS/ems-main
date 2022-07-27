/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.plugin;

import eu.melodic.event.baguette.client.install.ClientInstallationTask;
import eu.melodic.event.baguette.client.install.InstallationContextProcessorPlugin;
import eu.melodic.models.interfaces.ems.KeyValuePair;
import eu.melodic.models.interfaces.ems.Monitor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Installation context processor plugin for generating Netdata configuration for collecting metrics from prometheus exporters
 */
@Slf4j
@Data
@Service
public class PrometheusProcessorPlugin implements InstallationContextProcessorPlugin {
    @Override
    public void processBeforeInstallation(ClientInstallationTask task, long taskCounter) {
        log.debug("PrometheusProcessorPlugin: Task #{}: processBeforeInstallation: BEGIN", taskCounter);
        log.trace("PrometheusProcessorPlugin: Task #{}: processBeforeInstallation: BEGIN: task={}", taskCounter, task);

        StringBuilder prometheusConf = new StringBuilder("# Generated on: ").append(new Date()).append("\n\n");

        prometheusConf.append("# update_every: 5\n");
        prometheusConf.append("# autodetection_retry: 0\n");
        prometheusConf.append("# priority: 70000\n");

        prometheusConf.append("\njobs:\n");
        for (Monitor monitor : task.getTranslationContext().MON) {
            try {
                log.trace("PrometheusProcessorPlugin: Task #{}: Processing monitor: {}", taskCounter, monitor);
                String componentName = monitor.getComponent();
                String metricName = monitor.getMetric();

                log.trace("PrometheusProcessorPlugin: Task #{}: MONITOR: component={}, metric={}", taskCounter, componentName, metricName);
                if (monitor.getSensor().isPullSensor()) {
                    if (monitor.getSensor().getPullSensor().getConfiguration()!=null) {
                        Map<String, String> config = monitor.getSensor().getPullSensor().getConfiguration().stream()
                                .filter(pair->pair.getKey()!=null && pair.getValue()!=null)
                                .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue));
                        log.trace("PrometheusProcessorPlugin: Task #{}: MONITOR with PULL SENSOR: config: {}", taskCounter, config);
                        String prometheusJobName = config.get("prometheus.Job-Name");
                        String prometheusEndpoint = config.get("prometheus.Endpoint");
                        if (StringUtils.isNotBlank(prometheusJobName) && StringUtils.isNotBlank(prometheusEndpoint)) {
                            prometheusConf.append("  - name: ").append(prometheusJobName).append("\n");
                            prometheusConf.append("    url: '").append(prometheusEndpoint).append("'\n");
                            log.trace("PrometheusProcessorPlugin: Task #{}: Extracted Prometheus config: metricName={}, endpoint={}",
                                    taskCounter, prometheusJobName, prometheusEndpoint);
                        }
                    }
                }

            } catch (Exception e) {
                log.error("PrometheusProcessorPlugin: Task #{}: EXCEPTION while processing monitor. Skipping it: {}\n", taskCounter, monitor, e);
            }
        }
        log.debug("PrometheusProcessorPlugin: Task #{}: Netdata Prometheus configuration: \n{}", taskCounter, prometheusConf);

        task.getNodeRegistryEntry().getPreregistration().put("NETDATA_PROMETHEUS_CONF", prometheusConf.toString());
        log.debug("PrometheusProcessorPlugin: Task #{}: processBeforeInstallation: END", taskCounter);
    }

    @Override
    public void processAfterInstallation(ClientInstallationTask task, long taskCounter, boolean success) {
        log.debug("PrometheusProcessorPlugin: Task #{}: processAfterInstallation: success={}", taskCounter, success);
        log.trace("PrometheusProcessorPlugin: Task #{}: processAfterInstallation: success={}, task={}", taskCounter, success, task);
    }

    @Override
    public void start() {
        log.info("PrometheusProcessorPlugin: start()");
    }

    @Override
    public void stop() {
        log.info("PrometheusProcessorPlugin: stop()");
    }
}
