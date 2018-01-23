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
    List<MetricDTO> metrics;

    private double maxUtility;
    private Collection<Component> configurationWithMaxUtility;
    private Collection<SolutionVariable> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;

    public abstract double evaluate(Collection<Component> newConfiguration);


    UtilityFunctionEvaluator(List<VariableDTO> variables, NodeCandidates nodeCandidates) {

        this.variables = variables;
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        log.info("Creating Utility Function Evaluator from Constraint Problem");
        log.info("Variables from CP");
        for (VariableDTO v : variables) {
            log.info("{}, type: {}", v.getId(), v.getType());
        }

        //this.metrics = cp.getMetricVariables();     // todo convert

        //this.isReconfig = !(cp.getSolution().isEmpty());

        this.isReconfig = false; //todo get actualDeployment
        if (isReconfig) {
            //Solution actualSolution = findLastSolution(cp.getSolution()); //assumption: last solution was deployed
            //this.actConfiguration = convertActualDeployment(actualSolution.getVariableValue(),getSampleNodeCandidates());
        }

        this.maxUtility = 0.0;
    }

    public double evaluate(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal) {

        log.info("Evaluating solution:");
        int i = 0;
        for (IntVar var : newConfigurationInt) {         //todo: better print and print real variables
            if (i < variables.size()) {
                log.info("{} value = {}", var.getName(), var.getValue());
            }
            i++;
        }

        Collection<Component> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt,
                newConfigurationReal);

        if (isNull(newConfiguration)){
            log.info("Returning utility value = 0");
            return 0;
        }

        double utility = evaluate(newConfiguration);

        if (utility >= maxUtility){
            maxUtility = utility;
            configurationWithMaxUtility = newConfiguration;
            solutionWithMaxUtility = convertSolution(newConfigurationInt);
            log.info("Actualized configuration with Max Utility");
        }
        return utility;

        //return evaluate(Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidates), 1)));
        //return evaluate(convertSolutionToNodeCandidatesToTest(newConfigurationInt, newConfigurationReal));

    }

    public double evaluateActualSolution() {
        return evaluate(actConfiguration);
    }

    private Collection<Component> convertSolutionToNodeCandidates(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal) {

        log.info("Converting solution to Node Candidates");

        Collection<Component> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {

            log.info("Converting solution for component {}", componentId);

            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId,
                    newConfigurationInt, newConfigurationReal, variables);

            int provider = getProviderValue(componentId, variables, newConfigurationInt);

            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent)
                    .orElse(null);

            if (isNull(theCheapest)){
                log.warn("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return null;
            }

            log.info("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);

            newConfiguration.add(new Component(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));

        }
        return newConfiguration;
    }

    public void printConfigurationWithMaximumUtility() {

        log.info("Solution with maximum utility: {}", solutionWithMaxUtility);
        log.info("Configuration with maximum utility: {}", configurationWithMaxUtility);

    }




  /* ------------------------------------ only for tests - to delete later  -------------------*/

    private Collection<Component> convertSolutionToNodeCandidatesToTest(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal) {
        Collection<Component> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        List<NodeCandidate> nodeCandidates = getSampleNodeCandidates();

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.info("Filtering NC for component: {}", componentId);
            String providerId = getVariableName(componentId, VariableType.PROVIDER, variables);
            Collection<String> variableNamesForComponent = getVariableNames(componentId, variables);
            Collection<IntVar> filteredIntVar = Arrays.stream(newConfigurationInt)
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
