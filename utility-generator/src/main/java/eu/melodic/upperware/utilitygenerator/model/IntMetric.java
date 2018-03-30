/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model;

public class IntMetric extends Metric<Integer> {

    private IntMetric(MetricType type, String id, Integer value) {
        super(type, id, value);
    }

    public static IntMetric of(IntMetricDTO metricDTO, MetricType type) {
        return new IntMetric(type, metricDTO.getName(), metricDTO.getValue());
    }

    public static IntMetric of(MetricType type, String id, Integer value) {
        return new IntMetric(type, id, value);
    }
}
