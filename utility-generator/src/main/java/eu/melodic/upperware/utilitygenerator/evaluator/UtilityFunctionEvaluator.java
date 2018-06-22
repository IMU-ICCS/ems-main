/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.connection.CamelModelTransformer;
import eu.melodic.upperware.utilitygenerator.model.*;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static java.util.Objects.isNull;

@Slf4j
public class UtilityFunctionEvaluator {

    Collection<ConfigurationElement> actConfiguration;
    private Collection<ConfigurationElement> configurationWithMaxUtility;

    private double maxUtility;
    private Collection<Var> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;
    private Collection<MetricDTO> metrics;
    private final Predicate<Var> varPredicate = var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName()));


    private Collection<Argument> arguments;
    private Expression utilityFunction;

    private CamelModelTransformer camelModelTransformer;

    public UtilityFunctionEvaluator(String path, List<VariableDTO> variables, List<MetricDTO> metricsDTOs, List<Var> deployedSolution, NodeCandidates nodeCandidates) {

        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        this.variables = Objects.requireNonNull(variables, "List of Variables could not be null");
        this.metrics = Objects.requireNonNull(metricsDTOs, "List of Metrics could not be null");


        this.camelModelTransformer = new CamelModelTransformer(path);

        arguments = camelModelTransformer.getArgumentsFromCamelModel();

        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            arguments.add(new Argument(v.getId()));
            log.debug("{}, type: {}", v.getId(), v.getType());
        }



        utilityFunction = new Expression(camelModelTransformer.getFormula(), arguments.toArray(new Argument[arguments.size()]));

        this.maxUtility = 0.0;
    }

    public double evaluate(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {
        printSolutionForDebug(newConfigurationInt, newConfigurationReal);

        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal);


        //evaluate
        double utility = 0;

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
        //todo
        return 0.0;
    }

    public void printConfigurationWithMaximumUtility() {
        log.info("Configuration with maximum utility:");
        log.info(configurationWithMaxUtility.toString());
    }


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





}
