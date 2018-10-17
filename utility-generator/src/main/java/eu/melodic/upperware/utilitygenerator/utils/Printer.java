/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utils;

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Getter
public class Printer {

    private Collection<VariableDTO> variables;

    public void printSolution(Collection<Element> solution) {
        log.debug("Solution:");
        solution.stream()
                .filter(var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName())))
                .forEach(filteredVar -> log.debug("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    public void printSolutionForDebug(Collection<Element> solutionInt, Collection<Element> solutionReal) {
        printSolution(Stream.concat(solutionInt.stream(), solutionReal.stream()).collect(Collectors.toList()));
    }

    public void printVariablesFromConstraintProblem() {
        log.debug("Variables from Constraint Problem:");
        variables.forEach(v -> log.debug("{}, type: {}", v.getId(), v.getType()));
    }

    public void printConfiguration(Collection<ConfigurationElement> configuration) {
        CollectionUtils.emptyIfNull(configuration).forEach(el -> log.debug(el.toString()));
    }
}
