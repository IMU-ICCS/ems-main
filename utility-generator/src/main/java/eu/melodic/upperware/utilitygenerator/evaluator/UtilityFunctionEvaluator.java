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
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import solver.variables.IntVar;
import solver.variables.RealVar;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static java.lang.String.format;

@Slf4j
public abstract class UtilityFunctionEvaluator {

    boolean isReconfig;
    Collection<Component> actConfiguration;
    EList<MetricVariable> metrics;

    private CacheService<NodeCandidates> cacheService;
    private NodeCandidates nodeCandidates;
    private EList<Variable> variables;

    public abstract double evaluate(Collection<Component> newConfiguration);


    UtilityFunctionEvaluator(ConstraintProblem cp) {

        this.variables = cp.getVariables();
        log.info("Creating Utility Function Evaluator from Constraint Problem");
        log.info("Variables from CP");
        for (Variable v : variables) {
            log.info("{}, type: {}", v.getId(), v.getVariableType());
        }

        this.metrics = cp.getMetricVariables();     // todo convert

        this.isReconfig = !(cp.getSolution().isEmpty());

        if (isReconfig) {
            log.info("isReconfig is false");
            Solution actualSolution = findLastSolution(cp.getSolution()); //assumption: last solution was deployed
            this.actConfiguration = convertActualDeployment(actualSolution.getVariableValue(), getSampleNodeCandidates());
        }

//        this.nodeCandidates = cacheService.load("Ghgh"); //fixme


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

        //return evaluate(convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal));
        //return evaluate(Lists.newArrayList(new Component(findTheCheapestNodeCanidate(nodeCandidates), 1)));
        return evaluate(convertSolutionToNodeCandidatesToTest(newConfigurationInt, newConfigurationReal));

    }

    private Collection<Component> convertSolutionToNodeCandidates(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal) {

        Collection<Component> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {

            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId,
                    newConfigurationInt, newConfigurationReal, variables);

            int provider = getProviderValue(componentId, variables, newConfigurationInt);

            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent)
                    .orElseThrow(() -> new IllegalArgumentException(format("Node Candidates for component %s is not found", componentId)));

            newConfiguration.add(new Component(theCheapest, cardinalitiesForComponent.get(componentId)));

        }
        return newConfiguration;
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
            newConfiguration.add(new Component(theCheapest, cardinalitiesForComponent.get(componentId)));
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
