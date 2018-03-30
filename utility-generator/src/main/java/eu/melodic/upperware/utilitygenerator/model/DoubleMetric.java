/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model;

public class DoubleMetric extends Metric<Double> {

    private DoubleMetric(MetricType type, String id, Double value) {
        super(type, id, value);
    }

    public static DoubleMetric of(DoubleMetricDTO metricDTO, MetricType type) {
        return new DoubleMetric(type, metricDTO.getName(), metricDTO.getValue());
    }

    public static DoubleMetric of(MetricType type, String id, Double value) {
        return new DoubleMetric(type, id, value);
    }

}
