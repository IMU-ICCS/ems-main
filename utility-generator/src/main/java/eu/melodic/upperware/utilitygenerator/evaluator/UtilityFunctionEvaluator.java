/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.camel_model.FromCamelModelExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.ConstraintProblemExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.dlms.DLMSConverter;
import eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.utils.Printer;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.*;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertDeployedSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.convertToConstants;
import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.createUtilityFunctionCostFormula;

@Slf4j
public class UtilityFunctionEvaluator {

    private UtilityFunction function;
    private Collection<String> unmoveableComponents;
    private Collection<ConfigurationElement> deployedConfiguration;
    private Collection<VariableDTO> variablesFromConstraintProblem;
    private NodeCandidates nodeCandidates;

    private Collection<ArgumentConverter> converters;

    private Printer printer;

    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties) {

        Objects.requireNonNull(properties.getUtilityGenerator().getDlmsControllerUrl(), "Utility Generator properties with DLMS Controller URL does not exist");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        FromCamelModelExtractor fromCamelModelExtractor = new FromCamelModelExtractor(camelModelFilePath, readFromFile);

        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, readFromFile);
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        Collection<VariableValueDTO> actualConfiguration = constraintProblemExtractor.extractActualConfiguration();
        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(variablesFromConstraintProblem, nodeCandidates, actualConfiguration);

        NodeCandidatesConverter nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelExtractor, nodeCandidates, variablesFromConstraintProblem);

        String formula = prepareUtilityFunction(fromCamelModelExtractor, nodeCandidatesConverter);
        this.function = new UtilityFunction(formula);
        this.function.setConstants(convertToConstants(createConstants(constraintProblemExtractor, nodeCandidatesConverter, formula)));


        converters = new ArrayList<>();
        converters.add(new DLMSConverter(properties.getUtilityGenerator().getDlmsControllerUrl(), fromCamelModelExtractor, deployedConfiguration));
        converters.add(new VariableConverter(variablesFromConstraintProblem, formula));
        converters.add(nodeCandidatesConverter);


        log.info("Formula of the utility function: {}", formula);
        this.printer = new Printer(variablesFromConstraintProblem);
        printer.printVariablesFromConstraintProblem();

        this.unmoveableComponents = fromCamelModelExtractor.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", unmoveableComponents.toString());

        fromCamelModelExtractor.endWorkWithCamelModel();
        constraintProblemExtractor.endWorkWithCPModel();
    }

    public double evaluate(Collection<VariableValueDTO> solution) {
        printer.printSolution(solution);
        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(variablesFromConstraintProblem, nodeCandidates, solution);

        if (newConfiguration.isEmpty()) {
            log.info("No Node Candidate for the evaluated solution, returning 0");
            return 0;
        } else if (!deployedConfiguration.isEmpty() && EvaluatingUtils.areUnmoveableComponentsMoved(unmoveableComponents, deployedConfiguration, newConfiguration)) {
            log.info("Proposed solution moves the unmoveable component, returning 0");
            return 0;
        }

        Collection<Argument> allArguments = converters.stream()
                .map(converter -> converter.convertToArguments(solution, newConfiguration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return function.evaluateFunction(allArguments);
    }

    private String prepareUtilityFunction(FromCamelModelExtractor fromCamelModelExtractor, NodeCandidatesConverter nodeCandidatesConverter) {
        Optional<String> utilityFunctionFormula = fromCamelModelExtractor.getUtilityFormula();
        String formula;
        if (utilityFunctionFormula.isPresent()) {
            formula = utilityFunctionFormula.get();
        } else {
            log.warn("Optimisation requirement is not defined in the Camel Model, default utility function which optimises the cost will be created.");
            nodeCandidatesConverter.createCostAttributesForAllComponents();
            formula = createUtilityFunctionCostFormula(variablesFromConstraintProblem, nodeCandidatesConverter.getOneNodeCandidateAttributes());
        }
        return formula;
    }

    private Collection<Argument> createConstants(ConstraintProblemExtractor constraintProblemExtractor, NodeCandidatesConverter nodeCandidatesConverter, String formula) {
        MetricsConverter metricsConverter = new MetricsConverter(constraintProblemExtractor, formula);
        Collection<Argument> metrics = metricsConverter.convertToArguments(Collections.emptyList(), deployedConfiguration);
        Collection<Argument> currentConfigAttributesOfNodeCandidates = nodeCandidatesConverter.convertCurrentConfigAttributesOfNodeCandidates(deployedConfiguration);

        Collection<Argument> allConstants = new ArrayList<>(metrics);
        allConstants.addAll(currentConfigAttributesOfNodeCandidates);
        return allConstants;
    }
}
