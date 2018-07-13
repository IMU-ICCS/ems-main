/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import camel.metric.RawMetric;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class MetricsConverter {

    private List<MetricDTO> metricsFromConstraintProblem;

    public Collection<Element> convertMetrics(Collection<RawMetric> metricsFromCamel, String function){

        Collection<Element> metricsForUtilityFunction = new ArrayList<>();

        //todo - add not only IntElements make a function for adding
        metricsFromCamel.stream()
                .filter(m -> isInFormula(function, m.getName()))
                .forEach(metric -> metricsForUtilityFunction.add(new IntElement(metric.getName(), getMetricValue(metric))));

        return metricsForUtilityFunction;
    }

    private int getMetricValue(RawMetric metricFromCamel){
        return (int) metricsFromConstraintProblem.stream()
                .filter(m-> m.getName().equals(metricFromCamel.getName()))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("Metric with name: " + metricFromCamel.getName() + "does not match with any metric from Constraint Problem")).getValue();
    }

}
