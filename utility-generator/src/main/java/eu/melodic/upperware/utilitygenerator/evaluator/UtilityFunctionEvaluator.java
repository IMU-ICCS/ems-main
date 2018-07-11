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
import eu.melodic.upperware.utilitygenerator.converter.CurrentConfigConverter;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
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
    private Collection<Element> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;
    private Collection<MetricDTO> metrics;
    private final Predicate<Element> varPredicate = var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName()));


    private UtilityFunction function;

    public UtilityFunctionEvaluator(String cdoPath, String path, List<VariableDTO> variables, List<MetricDTO> metricsDTOs,
            List<Element> deployedSolution, NodeCandidates nodeCandidates) {

        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        this.variables = Objects.requireNonNull(variables, "List of Variables could not be null");
        this.metrics = Objects.requireNonNull(metricsDTOs, "List of Metrics could not be null");


        CamelModelTransformer camelModelTransformer = new CamelModelTransformer(path);

        Collection<Argument> arguments = camelModelTransformer.getArgumentsFromCamelModel();

        Collection<Element> currentConfigArguments = CurrentConfigConverter.convertCurrentConfig(variables, camelModelTransformer.getMetricVariables(), deployedSolution);


        log.info(currentConfigArguments.toString());

        Collection<NodeCandidateAttribute> attributesOfNodeCandidates = camelModelTransformer.getAttributesOfNodeCandidates();


        //pobierz akt node candidates (?)
        String formula = camelModelTransformer.getUtilityFormula();

        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            arguments.add(new Argument(v.getId()));
            log.debug("{}, type: {}", v.getId(), v.getType());
        }
        function = new UtilityFunction(new Expression(formula, arguments.toArray(new Argument[arguments.size()])),
                arguments.toArray(new Argument[arguments.size()]));
//        function.setAttributesOfNodeCandidates(attributesOfNodeCandidates);
//        function.setCurrentConfigs(currentConfigArguments);

        this.maxUtility = 0.0;
    }

    public double evaluate(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal) {
        //printSolutionForDebug(newConfigurationInt, newConfigurationReal);

        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal);


        //evaluate
        double utility = function.evaluateFunction();

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


    private Collection<ConfigurationElement> convertActualDeployment(Collection<Element> deployedSolution) {
        return convertSolutionToNodeCandidates(deployedSolution.stream().map(s -> (IntElement) s).collect(Collectors.toList()), new ArrayList<>());
    }


    private Collection<ConfigurationElement> convertSolutionToNodeCandidates(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal) {

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


    private void printSolution(Collection<Element> solution) {
        log.info("Converting deployed solution:");
        solution.stream()
                .filter(varPredicate)
                .forEach(filteredVar -> log.info("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }

    private void printSolutionForDebug(Collection<IntElement> solutionInt, Collection<RealElement> solutionReal) {
        log.debug("Evaluating solution:");
        Stream.concat(solutionInt.stream(), solutionReal.stream())
                .filter(varPredicate)
                .forEach(filteredVar -> log.debug("{} = {} ", filteredVar.getName(), filteredVar.getValue()));
    }





}
