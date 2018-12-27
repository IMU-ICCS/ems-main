/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class VariableConverter extends ArgumentConverter {

    private Collection<VariableDTO> variablesFromConstraintProblem;
    private String formula;

    @Override
    public Collection<VariableValueDTO> convertToElements(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertVariablesForFunction(solution);
    }

    private Collection<VariableValueDTO> convertVariablesForFunction(Collection<VariableValueDTO> solution) {
        return solution.stream().filter(element -> isInFormula(formula, element.getName()) && isVariable(element)).collect(Collectors.toList());
    }

    private boolean isVariable(VariableValueDTO variableValueDTO) {
        return variablesFromConstraintProblem.stream().anyMatch(v -> v.getId().equals(variableValueDTO.getName()));
    }

}
