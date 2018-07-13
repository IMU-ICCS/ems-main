/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utils;

import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Getter
public class Printer {

    private List<VariableDTO> variables;
    private final Predicate<Element> varPredicate = var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName()));


    public void printSolution(Collection<Element> solution) {
        log.info("Converting deployed solution:");
        solution.stream()
                .filter(varPredicate)
                .forEach(filteredVar -> log.info("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    public void printSolutionForDebug(Collection<IntElement> solutionInt, Collection<RealElement> solutionReal) {
        log.debug("Evaluating solution:");
        Stream.concat(solutionInt.stream(), solutionReal.stream())
                .filter(varPredicate)
                .forEach(filteredVar -> log.debug("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    public void printVariablesFromConstraintProblem(){

        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            log.debug("{}, type: {}", v.getId(), v.getType());
        }

    }

    //todo
    public void printConfigurationWithMaximumUtility() {
        log.info("Configuration with maximum utility:");
    }
}
