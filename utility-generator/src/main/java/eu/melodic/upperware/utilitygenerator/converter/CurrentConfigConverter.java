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
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.getVariableType;
import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
public class CurrentConfigConverter {


    public static Collection<Element> convertCurrentConfig(Collection<VariableDTO> variablesFromConstraintProblem,
            Collection<MetricVariableImpl> variablesFromCamel, Collection<Element> deployedSolution, String formula){
        return variablesFromCamel.stream()
                .filter(m -> isInFormula(formula, m.getName()))
                .map(actVar -> new IntElement(actVar.getName(), getVariableValue(actVar, deployedSolution, variablesFromConstraintProblem))).collect(Collectors.toList());
    }

    private static int getVariableValue(camel.metric.impl.MetricVariableImpl metric, Collection<Element> deployedSolution, Collection<VariableDTO> variables){

        log.info("getVariableValue: for = {} , component {}, annotations: {}", metric.getName(), metric.getComponent().getName(), metric.getMetricTemplate().getAttribute().getAnnotations().get(0).getId());


        VariableDTO matchingVariable = variables.stream()
                .filter(variable -> variable.getType().equals(getVariableType(metric)) && variable.getComponentId().equals(metric.getComponent().getName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Variable with name " + metric.getName() + " does not match with variable from Constraint Problem"));

        return (int) deployedSolution.stream()
                .filter(solutionElement -> solutionElement.getName().equals(matchingVariable.getId()))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("Variable with name " + matchingVariable.getId() + "does not match with deployed solution")).getValue();
    }


}

