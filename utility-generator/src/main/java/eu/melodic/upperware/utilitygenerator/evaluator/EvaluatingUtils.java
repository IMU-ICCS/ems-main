/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.cache.NodeCandidatePredicates.*;
import static java.lang.String.format;

@Slf4j
public class EvaluatingUtils {


    /* mapping variables from solution and cp model */


    public static Map<String, Integer> getCardinalitiesForComponent(Collection<Element> newConfiguration, Collection<VariableDTO> variables) {
        Map<String, Integer> cardinalitiesForComponent = new HashMap<>();

        Collection<VariableDTO> cardinalities = variables.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .collect(Collectors.toList());

        newConfiguration.forEach(intVar -> cardinalities
                .stream()
                .filter(c -> intVar.getName().equals(c.getId()))
                .findFirst()
                .ifPresent(variable -> cardinalitiesForComponent.put(variable.getComponentId(), (int) intVar.getValue()))); //cardinality is always int
        return cardinalitiesForComponent;
    }

    //provider value is always int
    public static int getProviderValue(String componentId, Collection<VariableDTO> variables, Collection<Element> newConfigurationInt) {
        String provider = getVariableName(componentId, VariableType.PROVIDER, variables);
        return (int) newConfigurationInt.stream()
                .filter(intVar -> provider.equals(intVar.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", provider)))
                .getValue();

    }

    private static VariableType getVariableType(String name, Collection<VariableDTO> variables) {
        return variables.stream()
                .filter(variable -> name.equals(variable.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", name)))
                .getType();
    }

    private static Collection<String> getVariableNames(String componentId, Collection<VariableDTO> variables) {
        return variables.stream()
                .filter(variable -> componentId.equals(variable.getComponentId()))
                .map(VariableDTO::getId)
                .collect(Collectors.toList());
    }

    private static String getVariableName(String componentId, VariableType type, Collection<VariableDTO> variables) {
        return variables.stream()
                .filter(v -> ((componentId.equals(v.getComponentId())) && type.equals(v.getType())))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable with type %s for component %s does not exist", type, componentId)))
                .getId();
    }

    public static Predicate<NodeCandidate>[] makePredicatesFromSolution(String componentId, Collection<Element> solution, Collection<VariableDTO> variables) {

        Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);

        List<Element> variablesForComponent = solution.stream()
                .filter(var -> variableNamesForComponent.contains(var.getName()))
                .collect(Collectors.toList());

        List<Predicate<NodeCandidate>> predicates = new ArrayList<>();


        for (Element var : variablesForComponent) {
            VariableType type = getVariableType(var.getName(), variables);

            switch (type) {
                //int
                case RAM:
                    log.debug("Creating getRamPredicate for value {}", var.getValue());
                    predicates.add(getRamPredicate((long) (int) var.getValue()));
                    break;
                case CARDINALITY:
                    break;
                case PROVIDER:
                    break;
                case CPU:
                    break;
                case CORES:
                    log.debug("Creating getCoresPredicate for value {}", var.getValue());
                    predicates.add(getCoresPredicate((int) var.getValue()));
                    break;
                case OS:
                    log.debug("Creating getOsPredicate for value {}", var.getValue());
                    predicates.add(getOsPredicate((int) var.getValue()));
                    break;

                //real
                case STORAGE:
                    predicates.add(getStoragePredicate((int) var.getValue())); //fixme - to check
                    break;
                case LOCATION:
                    break;

            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
