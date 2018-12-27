/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTOFactory;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class MetricsConverter extends ArgumentConverter {

    private Collection<MetricDTO> metricsFromConstraintProblem;
    private String function;

    @Override
    public Collection<VariableValueDTO> convertToElements(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> configuration) {
        return metricsFromConstraintProblem.stream()
                .filter(m -> isInFormula(function, m.getName()))
                .map(VariableValueDTOFactory::createElement)
                .collect(Collectors.toList());
    }
}
