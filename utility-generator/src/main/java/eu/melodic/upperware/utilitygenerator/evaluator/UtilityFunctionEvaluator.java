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
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.model.*;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static java.util.Objects.isNull;

@Slf4j
public abstract class UtilityFunctionEvaluator {

    boolean isReconfig;
    Collection<Component> actConfiguration;

    private double maxUtility;
    private Collection<Component> configurationWithMaxUtility;
    private Collection<Var> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;

    public abstract double evaluate(Collection<Component> newConfiguration);


    UtilityFunctionEvaluator(List<VariableDTO> variables, NodeCandidates nodeCandidates) {

        this.variables = variables;
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        log.debug("Creating Utility Function Evaluator from Constraint Problem");
        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            log.debug("{}, type: {}", v.getId(), v.getType());
        }

        //this.isReconfig = !(cp.getSolution().isEmpty());

        this.isReconfig = false; //todo get actualDeployment
        if (isReconfig) {
            //Solution actualSolution = findLastSolution(cp.getSolution()); //assumption: last solution was deployed
            //this.actConfiguration = convertActualDeployment(actualSolution.getVariableValue(),getSampleNodeCandidates());
        }

        this.maxUtility = 0.0;
    }

    public double evaluate(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {

        log.debug("Evaluating solution:");
        printSolutionForDebug(newConfigurationInt, newConfigurationReal);

        Collection<Component> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal);

        if (isNull(newConfiguration)) {
            log.debug("Returning utility value = 0");
            return 0;
        }

        double utility = evaluate(newConfiguration);

        if (utility >= maxUtility) {
            maxUtility = utility;
            configurationWithMaxUtility = newConfiguration;
            solutionWithMaxUtility = convertSolution(newConfigurationInt, newConfigurationReal);
            log.debug("Actualized configuration with Max Utility");
        }
        return utility;

        //return evaluate(Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidates), 1)));
        //return evaluate(convertSolutionToNodeCandidatesToTest(newConfigurationInt, newConfigurationReal));

    }

    public double evaluateActualSolution() {
        return evaluate(actConfiguration);
    }

    public void printConfigurationWithMaximumUtility() {


        log.info("Solution with maximum utility:");
        printSolution(solutionWithMaxUtility);
        log.info("Configuration with maximum utility:");
        log.info(configurationWithMaxUtility.toString());

    }


    private Collection<Component> convertSolutionToNodeCandidates(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {

        log.debug("Converting solution to Node Candidates");

        Collection<Component> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {

            log.debug("Converting solution for component {}", componentId);

            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId,
                    newConfigurationInt, newConfigurationReal, variables);

            int provider = getProviderValue(componentId, variables, newConfigurationInt);

            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent)
                    .orElse(null);

            if (isNull(theCheapest)) {
                log.warn("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return null;
            }

            log.debug("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);

            newConfiguration.add(new Component(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));

        }
        return newConfiguration;
    }


    private void printSolution(Collection<Var> solution) {
        solution.stream()
                .filter(var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName())))
                .forEach(filteredVar -> log.info("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    private void printSolutionForDebug(Collection<IntVar> solutionInt, Collection<RealVar> solutionReal) {
        Collection<Var> c = new ArrayList<>(solutionInt);
        c.addAll(solutionReal);
        c.stream()
                .filter(var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName())))
                .forEach(filteredVar -> log.debug("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }





    /* ------------------------------------ only for tests - to delete later  -------------------*/

    private Collection<Component> convertSolutionToNodeCandidatesToTest(Collection<IntVar> newConfigurationInt, RealVar[] newConfigurationReal) {
        Collection<Component> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Filtering NC for component: {}", componentId);
            String providerId = getVariableName(componentId, VariableType.PROVIDER, variables);
            Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);
            Collection<IntVar> filteredIntVar = newConfigurationInt.stream()
                    .filter(v -> variableNamesForComponent.contains(v.getName())).collect(Collectors.toList());

            NodeCandidate theCheapest = findTheCheapestNodeCanidate(nodeCandidates);
            newConfiguration.add(new Component(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));
        }
        return newConfiguration;
    }

    UtilityFunctionEvaluator(Collection<Component> actConfiguration, boolean isReconfig) {

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
