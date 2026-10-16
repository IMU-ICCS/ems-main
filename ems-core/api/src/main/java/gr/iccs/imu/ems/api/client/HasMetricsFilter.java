/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.api.client;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface HasMetricsFilter {
//    default void addMetricsFilter(IMetricsFilter metricsFilter) {}
//    default void removeMetricsFilter(IMetricsFilter metricsFilter) {}
//    default List<IMetricsFilter> getMetricsFilters() {
//        return Collections.emptyList();
//    }

    default Map<IMetricsFilter, IMetricsFilter.Context> runPrePublishMetricFor(AtomicBoolean canPublish, Set<IMetricsFilter> metricsFilters, String metricName, double metricValue, String nodeAddress) {
        canPublish.set(true);
        return metricsFilters.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        processor -> {
                            IMetricsFilter.Context context = processor.prePublishMetric(metricName, metricValue, nodeAddress);
                            if (! context.canPublish()) canPublish.set(false);
                            return context;
                        }
                ));
    }

    default void runPostPublishMetricFor(Map<IMetricsFilter, IMetricsFilter.Context> metricsFiltersWithContexts, String metricName, double metricValue, String nodeAddress) {
        metricsFiltersWithContexts.forEach((processor,context) ->
                processor.postPublishMetric(metricName, metricValue, nodeAddress, context));
    }

    default void runNotPublishedMetricFor(Map<IMetricsFilter, IMetricsFilter.Context> metricsFiltersWithContexts, String metricName, double metricValue, String nodeAddress) {
        metricsFiltersWithContexts.forEach((processor,context) ->
                processor.notPublishedMetric(metricName, metricValue, nodeAddress, context));
    }
}
