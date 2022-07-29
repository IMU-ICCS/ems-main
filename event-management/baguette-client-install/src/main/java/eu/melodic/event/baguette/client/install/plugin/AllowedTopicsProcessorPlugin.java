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
import eu.melodic.event.util.EmsConstant;
import eu.melodic.event.util.StrUtil;
import eu.melodic.models.interfaces.ems.KeyValuePair;
import eu.melodic.models.interfaces.ems.Monitor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Installation context processor plugin for generating 'allowed-topics' setting
 * used in baguette-client[.yml/.properties] config. file.
 * It set the 'COLLECTOR_ALLOWED_TOPICS' variable in pre-registration context.
 */
@Slf4j
@Data
@Service
public class AllowedTopicsProcessorPlugin implements InstallationContextProcessorPlugin {
    @Override
    public void processBeforeInstallation(ClientInstallationTask task, long taskCounter) {
        log.debug("AllowedTopicsProcessorPlugin: Task #{}: processBeforeInstallation: BEGIN", taskCounter);
        log.trace("AllowedTopicsProcessorPlugin: Task #{}: processBeforeInstallation: BEGIN: task={}", taskCounter, task);

        StringBuilder sbAllowedTopics = new StringBuilder();
        Set<String> addedTopicsSet = new HashSet<>();

        boolean first = true;
        for (Monitor monitor : task.getTranslationContext().MON) {
            try {
                log.trace("AllowedTopicsProcessorPlugin: Task #{}: Processing monitor: {}", taskCounter, monitor);

                String metricName = monitor.getMetric();
                if (!addedTopicsSet.contains(metricName)) {
                    if (first) first = false;
                    else sbAllowedTopics.append(", ");

                    sbAllowedTopics.append(metricName);
                    addedTopicsSet.add(metricName);
                }

                // Get sensor configuration (as a list of KeyValuePair's)
                List<KeyValuePair> sensorConfig = null;
                if (monitor.getSensor().isPullSensor()) {
                    // Pull Sensor
                    sensorConfig = monitor.getSensor().getPullSensor().getConfiguration();
                } else {
                    // Push Sensor
                    Map<String, Object> props = monitor.getSensor().getPushSensor().getAdditionalProperties();
                    if (props!=null && props.get("configuration") instanceof List) {
                        List list = (List) props.get("configuration");
                        if (list.size()>0 && list.get(0) instanceof KeyValuePair) {
                            sensorConfig = list;
                        }
                    }
                }

                // Process Destination aliases, if specified in configuration
                if (sensorConfig!=null) {
                    Optional<String> aliases = sensorConfig.stream()
                            //.filter(pair -> EmsConstant.COLLECTOR_DESTINATION_ALIASES.equals(pair.getKey()))
                            .filter(pair -> StrUtil.compareNormalized(pair.getKey(), EmsConstant.COLLECTOR_DESTINATION_ALIASES))
                            .map(KeyValuePair::getValue)
                            .findAny();

                    if (aliases.isPresent() && StringUtils.isNotBlank(aliases.get())) {
                        for (String alias : aliases.get().trim().split(EmsConstant.COLLECTOR_DESTINATION_ALIASES_DELIMITERS)) {
                            if (!(alias=alias.trim()).isEmpty()) {
                                if (!alias.equals(metricName)) {
                                    sbAllowedTopics.append(", ");
                                    sbAllowedTopics.append(alias).append(":").append(metricName);
                                }
                            }
                        }
                    }
                }

                log.trace("AllowedTopicsProcessorPlugin: Task #{}: MONITOR: metric={}, allowed-topics={}",
                        taskCounter, metricName, sbAllowedTopics);

            } catch (Exception e) {
                log.error("AllowedTopicsProcessorPlugin: Task #{}: EXCEPTION while processing monitor. Skipping it: {}\n",
                        taskCounter, monitor, e);
            }
        }

        String allowedTopics = sbAllowedTopics.toString();
        log.debug("AllowedTopicsProcessorPlugin: Task #{}: Allowed-Topics configuration for collectors: \n{}", taskCounter, allowedTopics);

        task.getNodeRegistryEntry().getPreregistration().put(EmsConstant.COLLECTOR_ALLOWED_TOPICS_VAR, allowedTopics);
        log.debug("AllowedTopicsProcessorPlugin: Task #{}: processBeforeInstallation: END", taskCounter);
    }

    @Override
    public void processAfterInstallation(ClientInstallationTask task, long taskCounter, boolean success) {
        log.debug("AllowedTopicsProcessorPlugin: Task #{}: processAfterInstallation: success={}", taskCounter, success);
        log.trace("AllowedTopicsProcessorPlugin: Task #{}: processAfterInstallation: success={}, task={}", taskCounter, success, task);
    }

    @Override
    public void start() {
        log.info("AllowedTopicsProcessorPlugin: start()");
    }

    @Override
    public void stop() {
        log.info("AllowedTopicsProcessorPlugin: stop()");
    }
}
