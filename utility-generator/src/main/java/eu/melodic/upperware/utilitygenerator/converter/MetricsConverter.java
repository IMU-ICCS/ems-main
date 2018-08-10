/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import camel.metric.Metric;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.ElementFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class MetricsConverter {

    private Collection<MetricDTO> metricsFromConstraintProblem;

    public Collection<Element> convertMetrics(String function) {
        return metricsFromConstraintProblem.stream()
                .filter(m -> isInFormula(function, m.getName()))
                .map(ElementFactory::createElement)
                .collect(Collectors.toList());
    }

    //todo
    public Collection<Element> setDefaultValuesOfAttributes(Collection<Metric> metricsUsedInFunction) {
        return metricsUsedInFunction.stream()
                .map(m -> ElementFactory.createElement(m.getName(), 1.0))
                .collect(Collectors.toList());
    }
}
