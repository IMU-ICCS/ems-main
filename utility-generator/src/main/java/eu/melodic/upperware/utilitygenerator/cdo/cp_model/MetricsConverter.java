/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentFactory;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.isInFormula;

@Slf4j
public class MetricsConverter extends ArgumentConverter {

    private Collection<MetricDTO> metricsFromConstraintProblem;
    private String function;

    public MetricsConverter(ConstraintProblemExtractor constraintProblemExtractor, String function) {
        this.metricsFromConstraintProblem = constraintProblemExtractor.extractMetrics();
        this.function = function;
    }

    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> configuration) {
        return metricsFromConstraintProblem.stream()
                .filter(m -> isInFormula(function, m.getName()))
                .map(ArgumentFactory::createArgument)
                .collect(Collectors.toList());
    }
}
