/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO;

import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;

public class MetricDTOFactory {

    public static MetricDTO createMetricDTO(CpMetric metric) {
        if (metric.getValue() instanceof IntegerValueUpperware) {
            return new IntMetricDTO(metric.getId(), ((IntegerValueUpperware) metric.getValue()).getValue());
        } else if (metric.getValue() instanceof DoubleValueUpperware) {
            return new DoubleMetricDTO(metric.getId(), ((DoubleValueUpperware) metric.getValue()).getValue());
        } else if (metric.getValue() instanceof LongValueUpperware) {
            return new IntMetricDTO(metric.getId(), (int) ((LongValueUpperware) metric.getValue()).getValue());
        } else {
            return new FloatMetricDTO(metric.getId(), ((FloatValueUpperware) metric.getValue()).getValue());
        }
    }
}
