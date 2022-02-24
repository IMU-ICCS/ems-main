/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.event.EventMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemResourceMonitor implements Runnable, InitializingBean {
    @Getter @Setter
    private boolean enabled = Boolean.parseBoolean(
            System.getenv().getOrDefault("EMS_SYSMON_ENABLED", "true"));
    @Getter @Setter @Min(1000)
    private long period = Math.max(1000L,Long.parseLong(
            System.getenv().getOrDefault("EMS_SYSMON_PERIOD", "30000")));
    @Getter @Setter @NotBlank
    private String commandStr = System.getenv("EMS_SYSMON_COMMAND");
    @Getter @Setter @NotBlank
    private String systemResourceMetricsTopic = System.getenv("EMS_SYSMON_TOPIC");

    private final BrokerCepService brokerCepService;
    private final TaskScheduler scheduler;
    private ScheduledFuture<?> future;
    @Getter
    private Map<String, Object> latestMeasurements;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!enabled) log.warn("SystemResourceMonitor is disabled");
        else start();
    }

    public void start() {
        if (!enabled) return;
        if (future!=null) {
            log.warn("SystemResourceMonitor is already running");
            return;
        }
        future = scheduler.scheduleAtFixedRate(this, period);
        log.info("SystemResourceMonitor started");
    }

    public void stop() {
        if (!enabled) return;
        if (future==null || future.isCancelled()) {
            log.warn("SystemResourceMonitor is already stopped");
            return;
        }
        future.cancel(true);
        future = null;
        log.info("SystemResourceMonitor stopped");
    }

    public void run() {
        if (!enabled) return;
        StringBuilder result = new StringBuilder();
        try {
            if (StringUtils.isBlank(commandStr)) {
                log.debug("SystemResourceMonitor: Nothing to do. System metrics command is blank: {}", commandStr);
                return;
            }
            log.debug("SystemResourceMonitor: Getting system metrics with command: {}", commandStr);
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(commandStr);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine).append("\n");
            }
            in.close();
            log.debug("SystemResourceMonitor: Script output:\n{}", result);

            processOutput(result.toString());

        } catch (IOException e) {
            log.warn("SystemResourceMonitor: EXCEPTION: ", e);
        }
    }

    @SneakyThrows
    private void processOutput(String result) {
        log.debug("SystemResourceMonitor: processOutput: BEGIN:\n{}", result);
        EventMap event = new EventMap();
        for (String line : result.split("\n")) {
            String[] part = line.split(":", 2);
            String metricName = part[0].trim().toLowerCase();
            double metricValue= Double.parseDouble(part[1].trim());
            event.put(metricName, metricValue);
        }
        this.latestMeasurements = Collections.unmodifiableMap(event);
        log.debug("SystemResourceMonitor: processOutput: Metrics: {}", event);

        if (StringUtils.isBlank(systemResourceMetricsTopic)) {
            log.debug("SystemResourceMonitor: processOutput: END: No metrics topic has been not set. Will not publish metrics event");
            return;
        }

        log.trace("SystemResourceMonitor: processOutput: Will publish metrics event to topic: {}", systemResourceMetricsTopic);
        brokerCepService.publishEvent(null, systemResourceMetricsTopic, event);
        log.debug("SystemResourceMonitor: processOutput: END: Metrics event published to topic: {}", systemResourceMetricsTopic);
    }
}
