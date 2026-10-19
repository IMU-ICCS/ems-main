/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.util.client;

import gr.iccs.imu.ems.api.client.HasMetricsFilter;
import gr.iccs.imu.ems.api.client.IMetricsFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricFilterHelper implements HasMetricsFilter, InitializingBean {
    private final Set<IMetricsFilter> metricsFilters;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("MetricFilterHelper has been initialized");
    }

    public Map<IMetricsFilter, IMetricsFilter.Context> runPrePublishMetricFor(AtomicBoolean canPublish, String metricName, double metricValue, long timestamp, String nodeAddress) {
        if (metricsFilters.isEmpty())
            return Collections.emptyMap();
        return runPrePublishMetricFor(canPublish, metricsFilters, metricName, metricValue, timestamp, nodeAddress);
    }
}
