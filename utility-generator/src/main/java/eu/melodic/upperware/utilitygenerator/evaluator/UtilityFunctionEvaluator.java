/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.model.*;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static java.util.Objects.isNull;

@Slf4j
public abstract class UtilityFunctionEvaluator {

    private static final String NOT_RECONFIGURABLE_SUFIX = "_CTITRRR";
    boolean isReconfig;
    Collection<ConfigurationElement> actConfiguration;

    private double maxUtility;
    private Collection<ConfigurationElement> configurationWithMaxUtility;
    private Collection<Var> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;

    private final Predicate<Var> varPredicate = var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName()));


    UtilityFunctionEvaluator(List<VariableDTO> variables, List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        this.variables = Objects.requireNonNull(variables, "List of Variables could not be null");

        log.debug("Creating Utility Function Evaluator from Constraint Problem");
        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            log.debug("{}, type: {}", v.getId(), v.getType());
        }

        this.isReconfig = isReconfig(deployedSolution);
        if (isReconfig) {
            printSolution(deployedSolution);
            this.actConfiguration = convertActualDeployment(deployedSolution);
        }

        this.maxUtility = 0.0;
    }

    public abstract double evaluate(Collection<ConfigurationElement> newConfiguration);

    public double evaluate(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {
        printSolutionForDebug(newConfigurationInt, newConfigurationReal);

        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal);

        if (isNull(newConfiguration)) {
            log.debug("Returning utility value = 0");
            return 0;
        }

        if (checkIfNotReconfigurableComponentsAreNotChanged(newConfiguration)){
            log.info("This solution changes not reconfigurable components, returning 0");
            return 0;
        }

        double utility = evaluate(newConfiguration);

        if (utility > maxUtility) {
            maxUtility = utility;
            configurationWithMaxUtility = newConfiguration;
            solutionWithMaxUtility = convertSolution(newConfigurationInt, newConfigurationReal);
            log.debug("Actualized configuration with Max Utility");
        }
        log.debug("Utility = {} ", utility);
        return utility;

    }

    public double evaluateActualSolution() {
        return evaluate(actConfiguration);
    }

    public void printConfigurationWithMaximumUtility() {
        log.info("Configuration with maximum utility:");
        log.info(configurationWithMaxUtility.toString());
    }


    //fixme - RealVar
    private Collection<ConfigurationElement> convertActualDeployment(Collection<Var> deployedSolution) {
        return convertSolutionToNodeCandidates(deployedSolution.stream().map(s -> (IntVar) s).collect(Collectors.toList()), new ArrayList<>());
    }


    private Collection<ConfigurationElement> convertSolutionToNodeCandidates(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {

        log.debug("Converting solution to Node Candidates");

        Collection<ConfigurationElement> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Converting solution for component {}", componentId);

            int provider = getProviderValue(componentId, variables, newConfigurationInt);

            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId, newConfigurationInt, newConfigurationReal, variables);

            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent).orElse(null);

            if (isNull(theCheapest)) {
                log.debug("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return null;
            }

            log.debug("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);

            newConfiguration.add(new ConfigurationElement(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));

        }
        return newConfiguration;
    }


    private void printSolution(Collection<Var> solution) {
        log.info("Converting deployed solution:");
        solution.stream()
                .filter(varPredicate)
                .forEach(filteredVar -> log.info("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    private void printSolutionForDebug(Collection<IntVar> solutionInt, Collection<RealVar> solutionReal) {
        log.debug("Evaluating solution:");
        Stream.concat(solutionInt.stream(), solutionReal.stream())
                .filter(varPredicate)
                .forEach(filteredVar -> log.debug("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    private boolean isReconfig(List<Var> deployedSolution) {
        return deployedSolution != null;
    }


    private boolean checkIfNotReconfigurableComponentsAreNotChanged(Collection<ConfigurationElement> newConfiguration){

        return this.actConfiguration.stream()
                .filter(component -> component.getId().endsWith(NOT_RECONFIGURABLE_SUFIX))
                .allMatch(component -> newConfiguration.stream()
                        .anyMatch(newComponent ->
                                newComponent.getId().equals(component.getId())
                                && newComponent.getNodeCandidate().equals(component.getNodeCandidate()
                                )
                        )
                );

    }





    /* ------------------------------------ only for tests - to delete later  -------------------*/

    private Collection<ConfigurationElement> convertSolutionToNodeCandidatesToTest(Collection<IntVar> newConfigurationInt,
            RealVar[] newConfigurationReal) {
        Collection<ConfigurationElement> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Filtering NC for component: {}", componentId);
            String providerId = getVariableName(componentId, VariableType.PROVIDER, variables);
            Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);
            Collection<IntVar> filteredIntVar = newConfigurationInt.stream()
                    .filter(v -> variableNamesForComponent.contains(v.getName())).collect(Collectors.toList());

            NodeCandidate theCheapest = findTheCheapestNodeCanidate(nodeCandidates);
            newConfiguration.add(new ConfigurationElement(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));
        }
        return newConfiguration;
    }

    UtilityFunctionEvaluator(Collection<ConfigurationElement> actConfiguration, boolean isReconfig) {

        this.isReconfig = isReconfig;
        if (isReconfig) {
            this.actConfiguration = actConfiguration;
        }
    }

    private List<NodeCandidate> getSampleNodeCandidates() {
        File file = new File(getClass().getClassLoader().getResource("test/nodeCandidates.json").getFile());
        try {
            return new ObjectMapper().readValue(file, new TypeReference<List<NodeCandidate>>() {
            });
        } catch (IOException e) {
            System.out.println(e);
        }
        return Collections.emptyList();
    }

}
