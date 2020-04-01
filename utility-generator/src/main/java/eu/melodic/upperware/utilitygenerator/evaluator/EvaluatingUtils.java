/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.cache.NodeCandidatePredicates.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
public class EvaluatingUtils {
    public static Collection<ConfigurationElement> convertSolutionToNodeCandidates(Collection<VariableDTO> variables, NodeCandidates nodeCandidates, Collection<VariableValueDTO> solution) {
        log.debug("Converting solution to Node Candidates");

        Collection<ConfigurationElement> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(solution, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Converting solution for component {}", componentId);
            int provider = getProviderValue(componentId, variables, solution);
            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId, solution, variables);
            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent).orElse(null);

            if (isNull(theCheapest)) {
                log.debug("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return Collections.emptyList();
            }
            log.debug("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);

            newConfiguration.add(new ConfigurationElement(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));
        }
        return newConfiguration;
    }

    static Collection<ConfigurationElement> convertDeployedSolutionToNodeCandidates(Collection<VariableDTO> variables, NodeCandidates nodeCandidates, Collection<VariableValueDTO> solution) {
        if (solution == null) {
            return Collections.emptyList();
        }
        return convertSolutionToNodeCandidates(variables, nodeCandidates, solution);
    }

    public static boolean areUnmoveableComponentsMoved(Collection<String> unmoveableComponents, Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {
        return !newConfiguration.stream()
                .filter(component -> unmoveableComponents.contains(component.getId()))
                .allMatch(component -> actConfiguration.stream()
                        .anyMatch(actComponent -> actComponent.equals(component)));
    }

    private static Map<String, Integer> getCardinalitiesForComponent(Collection<VariableValueDTO> newConfiguration, Collection<VariableDTO> variables) {
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
    private static int getProviderValue(String componentId, Collection<VariableDTO> variables, Collection<VariableValueDTO> newConfigurationInt) {
        String provider = getVariableName(componentId, VariableType.PROVIDER, variables);
        return (int) newConfigurationInt.stream()
                .filter(intVar -> provider.equals(intVar.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Variable %s does not exist", provider)))
                .getValue();
    }

    private static Predicate<NodeCandidate>[] makePredicatesFromSolution(String componentId, Collection<VariableValueDTO> solution, Collection<VariableDTO> variables) {
        Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);

        List<VariableValueDTO> variablesForComponent = solution.stream()
                .filter(var -> variableNamesForComponent.contains(var.getName()))
                .collect(Collectors.toList());

        List<Predicate<NodeCandidate>> predicates = new ArrayList<>();

        for (VariableValueDTO var : variablesForComponent) {
            VariableType type = getVariableType(var.getName(), variables);

            switch (type) {
                case RAM:
                    log.debug("Creating getRamPredicate for value {}", var.getValue());
                    predicates.add(getRamPredicate((long) (int) var.getValue()));
                    break;
                case CORES:
                    log.debug("Creating getCoresPredicate for value {}", var.getValue());
                    predicates.add(getCoresPredicate((int) var.getValue()));
                    break;
                case OS:
                    log.debug("Creating getOsPredicate for value {}", var.getValue());
                    predicates.add(getOsPredicate((int) var.getValue()));
                    break;
                case STORAGE:
                    predicates.add(getStoragePredicate((int) var.getValue()));
                    break;
                case LATITUDE:
                    log.debug("Creating getLatitudePredicate for value {}", var.getValue());
                    predicates.add(getLatitudePredicate((int) var.getValue()));
                    break;
                case LONGITUDE:
                    log.debug("Creating getLongitudePredicate for value {}", var.getValue());
                    predicates.add(getLongitudePredicate((int) var.getValue()));
                    break;
                case LOCATION:
                case CARDINALITY:
                case PROVIDER:
                case CPU:
                    break;
            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
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

}
