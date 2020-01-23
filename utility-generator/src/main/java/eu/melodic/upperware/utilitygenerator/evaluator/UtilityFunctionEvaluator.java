/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.cdo.camel_model.FromCamelModelExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.ConstraintProblemExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.dlms.DLMSConverter;
import eu.melodic.upperware.utilitygenerator.evaluator.template_function_evaluator_utils.TemplateNodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.melodic.upperware.utilitygenerator.reconfiguration_penalty.PenaltyConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.*;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertDeployedSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolutionToNodeCandidates;
import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.createUtilityFunctionCostFormula;
import static eu.melodic.upperware.utilitygenerator.utils.Printer.printSolution;

@Slf4j
public class UtilityFunctionEvaluator {

    private UtilityFunction function;
    private Collection<String> unmoveableComponents;
    private Collection<ConfigurationElement> deployedConfiguration;
    private Collection<VariableDTO> variablesFromConstraintProblem;
    private NodeCandidates nodeCandidates;
    private Collection<ArgumentConverter> converters;

    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties,
                                    MelodicSecurityProperties melodicSecurityProperties, PenaltyFunctionProperties penaltyFunctionProperties, JWTService jwtService) {

        Objects.requireNonNull(properties.getUtilityGenerator().getDlmsControllerUrl(), "Utility Generator properties with DLMS Controller URL does not exist");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        FromCamelModelExtractor fromCamelModelExtractor = new FromCamelModelExtractor(camelModelFilePath, readFromFile);
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, readFromFile);

        this.unmoveableComponents = fromCamelModelExtractor.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", this.unmoveableComponents.toString());
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(this.variablesFromConstraintProblem, nodeCandidates, constraintProblemExtractor.extractActualConfiguration());

        String formula = prepareUtilityFunction(fromCamelModelExtractor);
        log.info("Formula of the utility function: {}", formula);
        fromCamelModelExtractor.setUtilityFunctionFormula(formula);
        NodeCandidatesConverter nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelExtractor, nodeCandidates, this.variablesFromConstraintProblem);

        this.converters = createConverters(properties, fromCamelModelExtractor, formula, nodeCandidatesConverter, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
        this.function = new UtilityFunction(formula, createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, formula), nodeCandidatesConverter));

        fromCamelModelExtractor.endWorkWithCamelModel();
        constraintProblemExtractor.endWorkWithCPModel();
    }

    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties,
                                    MelodicSecurityProperties melodicSecurityProperties, PenaltyFunctionProperties penaltyFunctionProperties, JWTService jwtService,
                                    List<TemplateProvider.AvailableTemplates> templates, List<Double> templateWeights) {
        Objects.requireNonNull(properties.getUtilityGenerator().getDlmsControllerUrl(), "Utility Generator properties with DLMS Controller URL does not exist");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        FromCamelModelExtractor fromCamelModelExtractor = new FromCamelModelExtractor(camelModelFilePath, readFromFile);
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, readFromFile);

        this.unmoveableComponents = fromCamelModelExtractor.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", this.unmoveableComponents.toString());
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(this.variablesFromConstraintProblem, nodeCandidates, constraintProblemExtractor.extractActualConfiguration());

        String formula = TemplateProvider.getTemplate(variablesFromConstraintProblem, templates, templateWeights);
        log.info("Formula of the utility function: {}", formula);
        fromCamelModelExtractor.setUtilityFunctionFormula(formula);
        NodeCandidatesConverter nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelExtractor, nodeCandidates, this.variablesFromConstraintProblem);

        this.converters = createConverters(properties, fromCamelModelExtractor, formula, nodeCandidatesConverter, melodicSecurityProperties, jwtService, penaltyFunctionProperties);
        this.function = new UtilityFunction(formula, createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, formula), nodeCandidatesConverter));

        fromCamelModelExtractor.endWorkWithCamelModel();
        constraintProblemExtractor.endWorkWithCPModel();
    }

    public UtilityFunctionEvaluator( String cpModelFilePath, NodeCandidates nodeCandidates, List<TemplateProvider.AvailableTemplates> templates, List<Double> templateWeights) {

        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, true);
        this.unmoveableComponents = Collections.emptyList();
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        String utilityFormula = TemplateProvider.getTemplate(variablesFromConstraintProblem, templates, templateWeights);
        log.info("Formula of the utility function: {}", utilityFormula);
        TemplateNodeCandidatesConverter nodeCandidatesConverter
                = new TemplateNodeCandidatesConverter(nodeCandidates, this.variablesFromConstraintProblem);

        this.converters = createConverters(utilityFormula, nodeCandidatesConverter);
        this.function = new UtilityFunction(utilityFormula, new ArrayList<>());

        constraintProblemExtractor.endWorkWithCPModel();
    }

    private Collection<ArgumentConverter> createConverters(UtilityGeneratorProperties properties, FromCamelModelExtractor fromCamelModelExtractor, String formula, NodeCandidatesConverter nodeCandidatesConverter,
                                                           MelodicSecurityProperties melodicSecurityProperties, JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        Collection<ArgumentConverter> argConverters = new ArrayList<>();
        argConverters.add(new DLMSConverter(properties.getUtilityGenerator().getDlmsControllerUrl(), fromCamelModelExtractor, this.deployedConfiguration, melodicSecurityProperties, jwtService));
        argConverters.add(new PenaltyConverter(fromCamelModelExtractor, this.deployedConfiguration, penaltyFunctionProperties));
        argConverters.add(new VariableConverter(this.variablesFromConstraintProblem, formula));
        argConverters.add(nodeCandidatesConverter);
        return argConverters;
    }

    private Collection<ArgumentConverter> createConverters( String formula, TemplateNodeCandidatesConverter nodeCandidatesConverter) {
        Collection<ArgumentConverter> argConverters = new ArrayList<>();
        argConverters.add(new VariableConverter(this.variablesFromConstraintProblem, formula));
        argConverters.add(nodeCandidatesConverter);
        return argConverters;
    }

    public double evaluate(Collection<VariableValueDTO> solution) {
        printSolution(variablesFromConstraintProblem, solution);
        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(this.variablesFromConstraintProblem, this.nodeCandidates, solution);

        if (newConfiguration.isEmpty()) {
            log.info("No Node Candidate for the evaluated solution, returning 0");
            return 0;
        } else if (!this.deployedConfiguration.isEmpty() && EvaluatingUtils.areUnmoveableComponentsMoved(this.unmoveableComponents, this.deployedConfiguration, newConfiguration)) {
            log.info("Proposed solution moves the unmoveable component, returning 0");
            return 0;
        }

        Collection<Argument> allArguments = this.converters.stream()
                .map(converter -> converter.convertToArguments(solution, newConfiguration))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return function.evaluateFunction(allArguments);
    }

    private String prepareUtilityFunction(FromCamelModelExtractor fromCamelModelExtractor) {
        String utilityFunctionFormula = fromCamelModelExtractor.getUtilityFunctionFormula();
        if ("".equals(utilityFunctionFormula)){
            return createUtilityFunctionCostFormula(this.variablesFromConstraintProblem);
        }
        return utilityFunctionFormula;
    }

    private Collection<Argument> createConstantValuesForOneReasoning(MetricsConverter metricsConverter, NodeCandidatesConverter nodeCandidatesConverter) {
        Collection<Argument> arguments = metricsConverter.convertToArguments(Collections.emptyList(), this.deployedConfiguration);
        arguments.addAll(nodeCandidatesConverter.convertCurrentConfigAttributesOfNodeCandidates(this.deployedConfiguration));
        return arguments;
    }
}
