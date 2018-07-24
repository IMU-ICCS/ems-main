/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.converter.CurrentConfigConverter;
import eu.melodic.upperware.utilitygenerator.converter.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.converter.NodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.converter.VariableConverter;
import eu.melodic.upperware.utilitygenerator.converter.camel.FromCamelModelConverter;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.utils.Printer;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.converter.ConvertingUtils.*;

@Slf4j
public class UtilityFunctionEvaluator {

    private double maxUtility;

    private UtilityFunction function;
    private NodeCandidatesConverter nodeCandidatesConverter;
    private VariableConverter variableConverter;


    private Collection<MetricDTO> metricsFromConstraintProblem;
    private Collection<VariableDTO> variablesFromConstraintProblem;
    private Collection<Element> deployedSolution;


    private NodeCandidates nodeCandidates;


    private Printer printer;

    public UtilityFunctionEvaluator(String cdoPath, String path, Collection<VariableDTO> variablesFromConstraintProblem, Collection<MetricDTO> metricsFromConstraintProblem,
            Collection<Element> deployedSolution, NodeCandidates nodeCandidates) {

        this.variablesFromConstraintProblem = Objects.requireNonNull(variablesFromConstraintProblem, "List of Variables could not be null");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        this.metricsFromConstraintProblem = Objects.requireNonNull(metricsFromConstraintProblem, "List of Metrics could not be null");
        this.deployedSolution = deployedSolution;

        this.maxUtility = 0.0;
        this.variableConverter = new VariableConverter();

        CurrentConfigConverter currentConfigConverter = new CurrentConfigConverter(variablesFromConstraintProblem);
        MetricsConverter metricsConverter = new MetricsConverter(metricsFromConstraintProblem);

        FromCamelModelConverter fromCamelModelConverter = new FromCamelModelConverter(path);
        String formula = fromCamelModelConverter.getUtilityFunctionFormula();
        log.info("Formula of utility function: {}", formula);

        Collection<NodeCandidateAttribute> attributesOfNodeCandidates = fromCamelModelConverter.getAttributesOfNodeCandidates();
        log.info("attributes of node candidates {}", attributesOfNodeCandidates);
        Collection<NodeCandidateAttribute> listOfAttributesOfNodeCandidates = fromCamelModelConverter.getListOfAttributesOfNodeCandidates();
        log.info("attributes of list of node candidates {}", listOfAttributesOfNodeCandidates);

        this.nodeCandidatesConverter = new NodeCandidatesConverter(attributesOfNodeCandidates, listOfAttributesOfNodeCandidates, nodeCandidates, variablesFromConstraintProblem);

        Collection<Element> currentConfigAttributesOfNodeCandidates = nodeCandidatesConverter.convertCurrentConfigAttributesOfNodeCandidates(fromCamelModelConverter.getCurrentConfigAttributesOfNodeCandidates(), deployedSolution);
        log.info("currentConfigAttributesOfNodeCandidates {}", currentConfigAttributesOfNodeCandidates);

        Collection<Element> currentConfigArguments = currentConfigConverter.convertCurrentConfig(fromCamelModelConverter.getCurrentConfigMetricVariablesUsedInFunction(), deployedSolution);
        log.info("current Config Arguments {} ", currentConfigArguments);

        Collection<Element> metrics = metricsConverter.convertMetrics(formula);
        log.info("metrics: {}", metrics);

        Collection<Constant> listsOfAttributesOfNodeCandidates = convertArrayToConstants(nodeCandidatesConverter.convertListOfAttributesOfNodeCandidates());
        log.info("list of att of node candidates {}", listOfAttributesOfNodeCandidates);

        Collection<Element> allConstants = currentConfigAttributesOfNodeCandidates;
        allConstants.addAll(currentConfigArguments);
        allConstants.addAll(metrics);

        this.function = new UtilityFunction(formula, Stream.concat(convertToConstants(allConstants).stream(), listsOfAttributesOfNodeCandidates.stream()).collect(Collectors.toList()));

        this.printer = new Printer(variablesFromConstraintProblem);
        printer.printVariablesFromConstraintProblem();
    }

    public double evaluate(Collection<Element> solution) {

        Collection<Element> attributeNodeCandidates = nodeCandidatesConverter.convertAttributesOfNodeCandidates(solution, function.getFormula());

        Collection<Element> variablesForFunction = variableConverter.convertVariablesForFunction(solution, function.getFormula());

        double utility = function.evaluateFunction(concatLists(attributeNodeCandidates, variablesForFunction));

        maxUtility = utility> maxUtility? maxUtility: utility;
        return utility;
    }

    //todo
    public void printConfigurationWithMaximumUtility() {
        printer.printConfigurationWithMaximumUtility();
    }

    private Collection<Argument> concatLists(Collection<Element> first, Collection<Element> second){
        return Stream.concat(convertToArgument(first).stream(), convertToArgument(second).stream()).collect(Collectors.toList());
    }

}
