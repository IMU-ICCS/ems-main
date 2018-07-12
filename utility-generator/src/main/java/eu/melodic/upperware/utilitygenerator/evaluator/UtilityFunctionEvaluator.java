/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.converter.CamelModelConverter;
import eu.melodic.upperware.utilitygenerator.converter.CurrentConfigConverter;
import eu.melodic.upperware.utilitygenerator.converter.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToArgument;
import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToConstants;
import static eu.melodic.upperware.utilitygenerator.converter.NodeCandidatesConverter.convertAttributesOfNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.converter.NodeCandidatesConverter.convertSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolution;
import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
public class UtilityFunctionEvaluator {

    Collection<ConfigurationElement> actConfiguration;
    private Collection<ConfigurationElement> configurationWithMaxUtility;

    private double maxUtility;
    private Collection<Element> solutionWithMaxUtility;

    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;
    private final Predicate<Element> varPredicate = var -> variables.stream().anyMatch(v -> v.getId().equals(var.getName()));

    private Collection<NodeCandidateAttribute> attributesOfNodeCandidates;
    private UtilityFunction function;

    public UtilityFunctionEvaluator(String cdoPath, String path, List<VariableDTO> variables, List<MetricDTO> metricsDTOs,
            List<Element> deployedSolution, NodeCandidates nodeCandidates) {

        this.maxUtility = 0.0;
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        this.variables = Objects.requireNonNull(variables, "List of Variables could not be null");
        Objects.requireNonNull(metricsDTOs, "List of Metrics could not be null");


        CamelModelConverter camelModelConverter = new CamelModelConverter(path);

        String formula = camelModelConverter.getUtilityFormula();
        log.info("Formula of utility function: {}", formula);

        Collection<Element> currentConfigArguments = CurrentConfigConverter.convertCurrentConfig(variables, camelModelConverter.getCurrentConfigMetricVariables(), deployedSolution, formula);
        Collection<Element> metrics = MetricsConverter.convertMetrics(metricsDTOs, camelModelConverter.getRawMetric(), formula);
        log.info("currentConfig {}", currentConfigArguments.toString());
        log.info("metrics {}", metrics);

        this.attributesOfNodeCandidates = camelModelConverter.getAttributesOfNodeCandidates();
        this.function = new UtilityFunction(formula, convertToConstants(Stream.concat(metrics.stream(), currentConfigArguments.stream()).collect(Collectors.toList())));


        printVariablesFromConstraintProblem();
    }

    public double evaluate(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal) {
        //printSolutionForDebug(newConfigurationInt, newConfigurationReal);

        //that two methods can be one
        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(variables, newConfigurationInt, newConfigurationReal, nodeCandidates);
        Collection<Element> attributeNodeCandidates = convertAttributesOfNodeCandidates(attributesOfNodeCandidates, newConfiguration, function.getFormula());

        Collection<Element> variablesForFunction = newConfigurationInt.stream()
                .filter(element -> isInFormula(function.getFormula(), element.getName()))
                .map(element -> new IntElement(element.getName(), element.getValue()))
                .collect(Collectors.toList());

        //evaluate
        double utility = function.evaluateFunction(Stream.concat(convertToArgument(attributeNodeCandidates).stream(), convertToArgument(variablesForFunction).stream()).collect(Collectors.toList())); //todo - variables from solution

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
        return convertSolutionToNodeCandidates(variables, deployedSolution.stream().map(s -> (IntElement) s).collect(Collectors.toList()), new ArrayList<>(), nodeCandidates);
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

    private void printVariablesFromConstraintProblem(){

        log.debug("Variables from Constraint Problem:");
        for (VariableDTO v : variables) {
            log.debug("{}, type: {}", v.getId(), v.getType());
        }

    }





}
