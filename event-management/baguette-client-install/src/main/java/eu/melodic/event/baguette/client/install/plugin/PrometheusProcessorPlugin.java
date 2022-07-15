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
        log.debug("PrometheusProcessorPlugin: processBeforeInstallation: task-counter={}, task={}", taskCounter, task);

        StringBuilder prometheusConf = new StringBuilder("# Generated on: ").append(new Date()).append("\n\n");

        prometheusConf.append("# update_every: 5\n");
        prometheusConf.append("# autodetection_retry: 0\n");
        prometheusConf.append("# priority: 70000\n");

        prometheusConf.append("\njobs:\n");
        for (Monitor monitor : task.getTranslationContext().MON) {
            String componentName = monitor.getComponent();
            String metricName = monitor.getMetric();
            log.trace("PrometheusProcessorPlugin: Task #{}: MONITOR: component={}, metric={}", taskCounter, componentName, metricName);
            if (monitor.getSensor().isPullSensor()) {
                Map<String, String> config = monitor.getSensor().getPullSensor().getConfiguration().stream()
                        .collect(Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue));
                log.trace("PrometheusProcessorPlugin: Task #{}: MONITOR with PULL SENSOR: config: {}", taskCounter, config);
                String prometheusMetricName = config.get("prometheus.metricName");
                String prometheusEndpoint = config.get("prometheus.endpoint");
                prometheusConf.append("  - name: ").append(prometheusMetricName).append("\n");
                prometheusConf.append("    url: '").append(prometheusEndpoint).append("'\n");
            }
        }
        log.debug("PrometheusProcessorPlugin: Task #{}: Netdata Prometheus configuration: \n{}", taskCounter, prometheusConf);

        task.getNodeRegistryEntry().getPreregistration().put("NETDATA_PROMETHEUS_CONF", prometheusConf.toString());
    }

    @Override
    public void processAfterInstallation(ClientInstallationTask task, long taskCounter, boolean success) {
        log.debug("PrometheusProcessorPlugin: processAfterInstallation: task-counter={}, success={}, task={}", taskCounter, success, task);
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
