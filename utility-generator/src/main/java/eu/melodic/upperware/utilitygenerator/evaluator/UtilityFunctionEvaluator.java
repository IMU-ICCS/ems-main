/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.converter.*;
import eu.melodic.upperware.utilitygenerator.converter.camel.FromCamelModelConverter;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.melodic.upperware.utilitygenerator.utils.Printer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToArgument;
import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.convertToConstants;
import static eu.melodic.upperware.utilitygenerator.converter.NodeCandidatesConverter.convertCurrentConfigAttributesOfNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertDeployedSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolutionToNodeCandidates;

@Slf4j
public class UtilityFunctionEvaluator {

    private UtilityFunction function;
    private Collection<String> unmoveableComponents;
    private Collection<ConfigurationElement> deployedConfiguration;
    private Collection<VariableDTO> variablesFromConstraintProblem;
    private NodeCandidates nodeCandidates;

    private Collection<ArgumentConverter> converters;

    private Printer printer;

    public UtilityFunctionEvaluator(String camelModelFilePath, boolean readFromFile, Collection<VariableDTO> variablesFromConstraintProblem,
            Collection<MetricDTO> metricsFromConstraintProblem, Collection<Element> deployedSolution, UtilityGeneratorProperties properties, NodeCandidates nodeCandidates) {

        this.variablesFromConstraintProblem = Objects.requireNonNull(variablesFromConstraintProblem, "List of Variables could not be null");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        Objects.requireNonNull(metricsFromConstraintProblem, "List of Metrics could not be null");
        variablesFromConstraintProblem.forEach(v -> log.info("Variables from Constraint Problem: {}, {}, {}", v.getId(), v.getType(), v.getComponentId()));

        FromCamelModelConverter fromCamelModelConverter = new FromCamelModelConverter(camelModelFilePath, readFromFile);
        String formula = fromCamelModelConverter.getUtilityFunctionFormula();
        log.info("Formula of the utility function: {}", formula);

        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(variablesFromConstraintProblem, nodeCandidates, deployedSolution);

        this.unmoveableComponents = fromCamelModelConverter.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", unmoveableComponents.toString());

        Collection<NodeCandidateAttribute> attributesOfNodeCandidates = fromCamelModelConverter.getAttributesOfNodeCandidates();
        log.info("Attributes of Node Candidates: {}", attributesOfNodeCandidates);

        Collection<NodeCandidateAttribute> listOfAttributesOfNodeCandidates = fromCamelModelConverter.getListOfAttributesOfNodeCandidates();
        log.info("Attributes of list of Node Candidates: {}", listOfAttributesOfNodeCandidates);

        if (!listOfAttributesOfNodeCandidates.isEmpty()) {
            log.warn("Flag on candidates is not supported in Utility Generator");
        }
        converters = new ArrayList<>();
        converters.add(new VariableConverter(variablesFromConstraintProblem, formula));
        converters.add(new DLMSConverter(properties.getUtilityGenerator().getDlmsControllerUrl(), fromCamelModelConverter.getListOfDlmsUtilityAttributes(), deployedConfiguration));
        converters.add(new NodeCandidatesConverter(attributesOfNodeCandidates, listOfAttributesOfNodeCandidates, nodeCandidates, variablesFromConstraintProblem));

        this.function = new UtilityFunction(formula, convertToConstants(createConstants(fromCamelModelConverter, metricsFromConstraintProblem, formula)));

        this.printer = new Printer(variablesFromConstraintProblem);
        printer.printVariablesFromConstraintProblem();

        fromCamelModelConverter.endWorkWithCamelModel();
    }

    public double evaluate(Collection<Element> solution) {
        printer.printSolution(solution);
        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(variablesFromConstraintProblem, nodeCandidates, solution);

        if (newConfiguration == null) {
            log.info("No Node Candidate for the evaluated solution, returning 0");
            return 0;
        } else if (deployedConfiguration != null && EvaluatingUtils.areUnmoveableComponentsMoved(unmoveableComponents, deployedConfiguration, newConfiguration)) {
            log.info("Proposed solution moves the unmoveable component, returning 0");
            return 0;
        }

        Collection<Element> allArguments = new ArrayList<>();
        converters.forEach(converter -> allArguments.addAll(converter.convertToElements(solution, newConfiguration)));
        return function.evaluateFunction(convertToArgument(allArguments));
    }

    private Collection<Element> createConstants(FromCamelModelConverter fromCamelModelConverter, Collection<MetricDTO> metricsFromConstraintProblem, String formula) {
        MetricsConverter metricsConverter = new MetricsConverter(metricsFromConstraintProblem, formula);
        Collection<Element> metrics = metricsConverter.convertToElements(Collections.emptyList(), deployedConfiguration);
        log.info("Metrics: {}", metrics);

        Collection<Element> currentConfigAttributesOfNodeCandidates = convertCurrentConfigAttributesOfNodeCandidates(fromCamelModelConverter.getCurrentConfigAttributesOfNodeCandidates(), deployedConfiguration);
        log.info("Current config attributes of Node Candidates: {}", currentConfigAttributesOfNodeCandidates);
        Collection<Element> allConstants = new ArrayList<>(metrics);
        allConstants.addAll(currentConfigAttributesOfNodeCandidates);
        return allConstants;
    }

}
