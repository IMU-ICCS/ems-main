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
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.utils.Printer;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.List;
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


    private Printer printer;

    public UtilityFunctionEvaluator(String cdoPath, String path, List<VariableDTO> variables, Collection<MetricDTO> metricsDTOs,
            Collection<Element> deployedSolution, NodeCandidates nodeCandidates) {

        Objects.requireNonNull(variables, "List of Variables could not be null");
        Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        Objects.requireNonNull(metricsDTOs, "List of Metrics could not be null");

        this.maxUtility = 0.0;
        this.variableConverter = new VariableConverter();

        CurrentConfigConverter currentConfigConverter = new CurrentConfigConverter(variables);
        MetricsConverter metricsConverter = new MetricsConverter(metricsDTOs);
        FromCamelModelConverter fromCamelModelConverter = new FromCamelModelConverter(path);
        String formula = fromCamelModelConverter.getUtilityFormula();
        log.info("Formula of utility function: {}", formula);

        //todo - maybe current config may be a part of metrics?
        Collection<Element> currentConfigArguments = currentConfigConverter.convertCurrentConfig(fromCamelModelConverter.getCurrentConfigMetricVariables(), deployedSolution, formula);
        Collection<Element> metrics = metricsConverter.convertMetrics(formula);

        this.function = new UtilityFunction(formula, convertToConstants(Stream.concat(metrics.stream(), currentConfigArguments.stream()).collect(Collectors.toList())));
        this.nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelConverter.getAttributesOfNodeCandidates(), nodeCandidates, variables);

        this.printer = new Printer(variables);
        printer.printVariablesFromConstraintProblem();
    }

    public double evaluate(Collection<Element> solution) {

        Collection<Element> attributeNodeCandidates = nodeCandidatesConverter.convertAttributesOfNodeCandidates(solution, function.getFormula());
        Collection<Element> variablesForFunction = variableConverter.convertVariablesForFunction(solution, function.getFormula());

        double utility = function.evaluateFunction(concatLists(attributeNodeCandidates, variablesForFunction));

        maxUtility = utility> maxUtility? maxUtility: utility;
        return utility;
    }

    public double evaluateActualSolution() {
        //todo
        return 0.0;
    }

    //todo
    public void printConfigurationWithMaximumUtility() {
        printer.printConfigurationWithMaximumUtility();
    }

    private Collection<Argument> concatLists(Collection<Element> first, Collection<Element> second){
        return Stream.concat(convertToArgument(first).stream(), convertToArgument(second).stream()).collect(Collectors.toList());
    }

}
