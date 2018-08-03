/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import camel.metric.impl.MetricVariableImpl;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.ElementFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.converter.camel.MappingTypeUtils.getVariableType;
import static eu.melodic.upperware.utilitygenerator.model.function.ElementFactory.createElement;

@Slf4j
@AllArgsConstructor
public class CurrentConfigConverter {

    private Collection<VariableDTO> variablesFromConstraintProblem;

    public Collection<Element> convertCurrentConfig(Collection<MetricVariableImpl> variablesFromCamel, Collection<Element> deployedSolution) {
        return deployedSolution.stream()
                .filter(var -> isActualValueOfVariableUsedInFormula(getMatchingVariable(var.getName()), variablesFromCamel))
                .map(actVar -> ElementFactory.createElementWithNewName(getVariableName(actVar, variablesFromCamel, variablesFromConstraintProblem), actVar))
                .collect(Collectors.toList());
    }

    //todo
    public Collection<Element> setDefaultValuesOfAttributes(Collection<MetricVariableImpl> variablesFromCamel) {
        return variablesFromCamel.stream().map(v -> createElement(v.getName(), 1.0)).collect(Collectors.toList());
    }

    private String getVariableName(Element variable, Collection<MetricVariableImpl> variablesFromCamel, Collection<VariableDTO> variables) {
        VariableDTO matchingVariable = variables.stream()
                .filter(variableDTO -> variableDTO.getId().equals(variable.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Variable from solution " + variable.getName() + " does not match with variable from Constraint Problem"));
        return variablesFromCamel.stream()
                .filter(variableFromCamel -> (variableFromCamel.getComponent().getName().equals(matchingVariable.getComponentId())
                        && (matchingVariable.getType().equals(getVariableType(variableFromCamel)))))
                .findAny().orElseThrow(() -> new IllegalStateException("Variable with name " + matchingVariable.getId() + " does not match with variable from Camel"))
                .getName();
    }

    private boolean isActualValueOfVariableUsedInFormula(VariableDTO variableFromConstraintProblem, Collection<MetricVariableImpl> variablesFromCamel) {
        return variablesFromCamel.stream()
                .anyMatch(variableFromCamel ->
                        variableFromCamel.getComponent().getName().equals(variableFromConstraintProblem.getComponentId())
                                && (variableFromConstraintProblem.getType().equals(getVariableType(variableFromCamel))));
    }

    private VariableDTO getMatchingVariable(String name) {
        return variablesFromConstraintProblem.stream()
                .filter(variable -> variable.getId().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Variable with name " + name + "does not match with variables from Constraint Problem"));
    }

}

