/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
@AllArgsConstructor
public class VariableConverter extends ArgumentConverter{

    private Collection<VariableDTO> variablesFromConstraintProblem;
    private String formula;

    @Override
    public Collection<Element> convertToElements(Collection<Element> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertVariablesForFunction(solution);
    }

    private Collection<Element> convertVariablesForFunction(Collection<Element> solution) {
        return solution.stream().filter(element -> isInFormula(formula, element.getName()) && isVariable(element)).collect(Collectors.toList());
    }

    private boolean isVariable(Element element) {
        return variablesFromConstraintProblem.stream().anyMatch(v -> v.getId().equals(element.getName()));
    }

}
