/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.converter.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.converter.NodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.converter.VariableConverter;
import eu.melodic.upperware.utilitygenerator.converter.camel.FromCamelModelConverter;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.utils.Printer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToArgument;
import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToConstants;

@Slf4j
public class UtilityFunctionEvaluator {

    private double maxUtility;

    private UtilityFunction function;
    private NodeCandidatesConverter nodeCandidatesConverter;
    private VariableConverter variableConverter;
    private Collection<String> unmoveableComponents;
    private Collection<ConfigurationElement> deployedConfiguration;

    private Printer printer;

    public UtilityFunctionEvaluator(String camelModelFilePath, boolean readFromFile, Collection<VariableDTO> variablesFromConstraintProblem,
            Collection<MetricDTO> metricsFromConstraintProblem, Collection<Element> deployedSolution, NodeCandidates nodeCandidates) {

        Objects.requireNonNull(variablesFromConstraintProblem, "List of Variables could not be null");
        Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        Objects.requireNonNull(metricsFromConstraintProblem, "List of Metrics could not be null");
        variablesFromConstraintProblem.forEach(v -> log.info("Variables from Constraint Problem: {}, {}, {}", v.getId(), v.getType(), v.getComponentId()));

        this.maxUtility = 0.0;
        this.variableConverter = new VariableConverter(variablesFromConstraintProblem);

        MetricsConverter metricsConverter = new MetricsConverter(metricsFromConstraintProblem);

        FromCamelModelConverter fromCamelModelConverter = new FromCamelModelConverter(camelModelFilePath, readFromFile);
        String formula = fromCamelModelConverter.getUtilityFunctionFormula();
        log.info("Formula of the utility function: {}", formula);

        this.unmoveableComponents = fromCamelModelConverter.getUnmoveableComponents();
        log.info("Unmoveable components: {}", unmoveableComponents.toString());

        Collection<NodeCandidateAttribute> attributesOfNodeCandidates = fromCamelModelConverter.getAttributesOfNodeCandidates();
        log.info("Attributes of Node Candidates: {}", attributesOfNodeCandidates);

        Collection<NodeCandidateAttribute> listOfAttributesOfNodeCandidates = fromCamelModelConverter.getListOfAttributesOfNodeCandidates();
        log.info("Attributes of list of Node Candidates: {}", listOfAttributesOfNodeCandidates);

        if (!listOfAttributesOfNodeCandidates.isEmpty()) {
            log.warn("Flag on candidates is not supported in Utility Generator");
        }

        this.nodeCandidatesConverter = new NodeCandidatesConverter(attributesOfNodeCandidates, listOfAttributesOfNodeCandidates, nodeCandidates, variablesFromConstraintProblem);


        Collection<Element> metrics = metricsConverter.convertMetrics(formula);
        log.info("metrics: {}", metrics);

        Collection<Element> allConstants = new ArrayList<>(metrics);

        if (deployedSolution != null) { // for configuration? how to get values of current config arguments?
            deployedConfiguration = nodeCandidatesConverter.convertSolutionToNodeCandidates(deployedSolution);
            Collection<Element> currentConfigAttributesOfNodeCandidates = nodeCandidatesConverter.convertCurrentConfigAttributesOfNodeCandidates(fromCamelModelConverter.getCurrentConfigAttributesOfNodeCandidates(), deployedSolution);
            log.info("CurrentConfigAttributesOfNodeCandidates {}", currentConfigAttributesOfNodeCandidates);

            allConstants.addAll(currentConfigAttributesOfNodeCandidates);
        } else {
            deployedConfiguration = null;
            log.info("It is the initial deployment. Setting values of attributes of Node Candidates to default values");
            allConstants.addAll(nodeCandidatesConverter.setDefaultValuesOfAttributes(fromCamelModelConverter.getCurrentConfigAttributesOfNodeCandidates()));
        }

        this.function = new UtilityFunction(formula, convertToConstants(allConstants));

        this.printer = new Printer(variablesFromConstraintProblem);
        printer.printVariablesFromConstraintProblem();

        fromCamelModelConverter.endWorkWithCamelModel();
    }

    public double evaluate(Collection<Element> solution) {
        printer.printSolution(solution);
        Collection<ConfigurationElement> newConfiguration = nodeCandidatesConverter.convertSolutionToNodeCandidates(solution);

        if (newConfiguration == null) {
            log.info("No Node Candidate for the evaluated solution, returning 0");
            return 0;
        } else if (deployedConfiguration != null && EvaluatingUtils.areUnmoveableComponentsMoved(unmoveableComponents, deployedConfiguration, newConfiguration)) {
            log.info("Proposed solution moves the unmoveable component, returning 0");
            return 0;
        }

        Collection<Element> attributeNodeCandidates = nodeCandidatesConverter.convertAttributesOfNodeCandidates(newConfiguration);
        Collection<Element> variablesForFunction = variableConverter.convertVariablesForFunction(solution, function.getFormula());

        double utility = function.evaluateFunction(Stream.concat(convertToArgument(attributeNodeCandidates).stream(), convertToArgument(variablesForFunction).stream()).collect(Collectors.toList()));

        maxUtility = utility > maxUtility ? utility : maxUtility;
        return utility;
    }
}
