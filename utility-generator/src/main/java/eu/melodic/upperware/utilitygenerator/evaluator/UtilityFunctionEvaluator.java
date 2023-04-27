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
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.PerformanceMetric;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.MetricsConverter;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.PerformanceMetricConverter;
import eu.melodic.upperware.utilitygenerator.evaluator.template_function_evaluator_utils.TemplateNodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.facade.pm.PMFacade;
import eu.melodic.upperware.utilitygenerator.facade.pm.TextPMFacadeImpl;
import eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidatesConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction;
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.*;
import java.util.function.Function;
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
    private final String applicationId;
    private final Collection<MetricDTO> metricsFromConstraintProblem;
    private final PerformanceMetricConverter performanceMetricConverter;
    //private final Map<String,UtilityFunction> performanceMetricsFormulas;
    private final PMFacade facade;

    // TODO old constructor signature, just here to prevent older artifacts from failing - remove later
    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates,
                                    MelodicSecurityProperties melodicSecurityProperties, JWTService jwtService) {
        this(camelModelFilePath, cpModelFilePath, readFromFile, nodeCandidates);
    }

    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates) {

        log.info("Creating of the UtilityFunctionEvaluator");
        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        FromCamelModelExtractor fromCamelModelExtractor = new FromCamelModelExtractor(camelModelFilePath, readFromFile);
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, readFromFile);

        this.unmoveableComponents = fromCamelModelExtractor.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", this.unmoveableComponents.toString());
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        Collection<PerformanceMetric> performanceMetrics = fromCamelModelExtractor.getPerformanceMetrics();
        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(this.variablesFromConstraintProblem, nodeCandidates, constraintProblemExtractor.extractActualConfiguration());

        String formula = prepareUtilityFunction(fromCamelModelExtractor);
        log.info("Formula of the utility function: {}", formula);
        fromCamelModelExtractor.setUtilityFunctionFormula(formula);
        NodeCandidatesConverter nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelExtractor, nodeCandidates, this.variablesFromConstraintProblem);

        Map<String,UtilityFunction> performanceMetricsFormulas = new HashMap<>();
        for (PerformanceMetric metric: performanceMetrics){
            UtilityFunction performanceFormula = new UtilityFunction(metric.getFormula(),
                    createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, metric.getFormula()), nodeCandidatesConverter));
            performanceMetricsFormulas.put(metric.getName(), performanceFormula);

        }
        this.performanceMetricConverter = new PerformanceMetricConverter(performanceMetrics, performanceMetricsFormulas, formula);

        this.converters = createConverters(formula, nodeCandidatesConverter);
        this.function = new UtilityFunction(formula, createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, formula), nodeCandidatesConverter));



        metricsFromConstraintProblem = constraintProblemExtractor.extractMetrics();
        applicationId = camelModelFilePath;

        fromCamelModelExtractor.endWorkWithCamelModel();
        constraintProblemExtractor.endWorkWithCPModel();
        facade = new TextPMFacadeImpl();
    }

    // used only from tests
    public UtilityFunctionEvaluator(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates,
                                    List<Map.Entry<TemplateProvider.AvailableTemplates, Double>> utilityComponents) {
        checkWeightsOfUtilityComponents(utilityComponents);

        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");

        FromCamelModelExtractor fromCamelModelExtractor = new FromCamelModelExtractor(camelModelFilePath, readFromFile);
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, readFromFile);

        this.unmoveableComponents = fromCamelModelExtractor.getUnmoveableComponentNames();
        log.info("Unmoveable components: {}", this.unmoveableComponents.toString());
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        Collection<PerformanceMetric> performanceMetrics = fromCamelModelExtractor.getPerformanceMetrics();
        this.deployedConfiguration = convertDeployedSolutionToNodeCandidates(this.variablesFromConstraintProblem, nodeCandidates, constraintProblemExtractor.extractActualConfiguration());

        String formula = TemplateProvider.getTemplate(variablesFromConstraintProblem, utilityComponents);
        log.info("Formula of the utility function: {}", formula);
        fromCamelModelExtractor.setUtilityFunctionFormula(formula);
        NodeCandidatesConverter nodeCandidatesConverter = new NodeCandidatesConverter(fromCamelModelExtractor, nodeCandidates, this.variablesFromConstraintProblem);

        this.converters = createConverters(formula, nodeCandidatesConverter);
        this.function = new UtilityFunction(formula, createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, formula), nodeCandidatesConverter));

        Map<String,UtilityFunction> performanceMetricsFormulas = new HashMap<>();
        for (PerformanceMetric metric: performanceMetrics){
            UtilityFunction performanceFormula = new UtilityFunction(metric.getFormula(),
                    createConstantValuesForOneReasoning(new MetricsConverter(constraintProblemExtractor, metric.getFormula()), nodeCandidatesConverter));
            performanceMetricsFormulas.put(metric.getName(), performanceFormula);

        }
        this.performanceMetricConverter = new PerformanceMetricConverter(performanceMetrics, performanceMetricsFormulas, formula);
        metricsFromConstraintProblem = constraintProblemExtractor.extractMetrics();
        applicationId = camelModelFilePath;

        fromCamelModelExtractor.endWorkWithCamelModel();
        constraintProblemExtractor.endWorkWithCPModel();
        facade = new TextPMFacadeImpl();
    }

    // used only from tests
    public UtilityFunctionEvaluator(String cpModelFilePath, NodeCandidates nodeCandidates, List<Map.Entry<TemplateProvider.AvailableTemplates, Double>> utilityComponents) {
        checkWeightsOfUtilityComponents(utilityComponents);

        this.nodeCandidates = Objects.requireNonNull(nodeCandidates, "List of Node Candidates is null");
        ConstraintProblemExtractor constraintProblemExtractor = new ConstraintProblemExtractor(cpModelFilePath, true);
        this.unmoveableComponents = Collections.emptyList();
        this.deployedConfiguration = Collections.emptyList();
        this.variablesFromConstraintProblem = constraintProblemExtractor.extractVariables();
        String utilityFormula = TemplateProvider.getTemplate(variablesFromConstraintProblem, utilityComponents);
        log.info("Formula of the utility function: {}", utilityFormula);
        TemplateNodeCandidatesConverter nodeCandidatesConverter
                = new TemplateNodeCandidatesConverter(nodeCandidates, this.variablesFromConstraintProblem);

        this.converters = createConverters(utilityFormula, nodeCandidatesConverter);
        this.function = new UtilityFunction(utilityFormula, new ArrayList<>());

        metricsFromConstraintProblem = constraintProblemExtractor.extractMetrics();
        this.performanceMetricConverter = new PerformanceMetricConverter(new HashSet<>(), new HashMap<>(), utilityFormula);

        applicationId = ""; // TODO no app-ID here?

        constraintProblemExtractor.endWorkWithCPModel();
        facade = new TextPMFacadeImpl();
    }

    private Collection<ArgumentConverter> createConverters(String formula, NodeCandidatesConverter nodeCandidatesConverter) {
        Collection<ArgumentConverter> argConverters = new ArrayList<>();
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
            log.debug("No Node Candidate for the evaluated solution, returning 0");
            return 0;
        } else if (!this.deployedConfiguration.isEmpty() && EvaluatingUtils.areUnmoveableComponentsMoved(this.unmoveableComponents, this.deployedConfiguration, newConfiguration)) {
            log.info("Proposed solution moves the unmoveable component, returning 0");
            return 0;
        }

        if (performanceMetricConverter.getPerformanceMetrics().isEmpty()) {
            log.info("No performance metrics found - skipping PM call");
        }
        else if (metricsFromConstraintProblem.isEmpty()) {
            log.info("No metrics in Constraint Problem - skipping PM call");
        }
        else {
            //Collection<MetricDTO> metrics = filterPerformanceMetrics();
            List<String> performanceMetricsNames = performanceMetricConverter.getPerformanceMetrics().stream().map(PerformanceMetric::getName).collect(Collectors.toList());
            Map<String, Double> predictionResult = facade.callPmPredictionText(solution, applicationId, variablesFromConstraintProblem, metricsFromConstraintProblem,performanceMetricsNames);
            Map<String,Double> performanceIndicators = new HashMap<>();
            if(predictionResult == null || predictionResult.isEmpty()) {
                log.warn("PM returned empty prediction, using predefined formula instead");
                for (PerformanceMetric metric: performanceMetricConverter.getPerformanceMetrics()){
                    log.info("Calculating for performance metric value: {}, using formula {}", metric.getName(), metric.getFormula());
                    UtilityFunction performanceIndicatorFunction = performanceMetricConverter.getPerformanceMetricsFormulas().get(metric.getName());
                    Collection<Argument> allArgumentsToPerfMetric = this.converters.stream()
                            .map(converter -> converter.convertToArguments(solution, newConfiguration))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                    double performanceMetricEstimation = performanceIndicatorFunction.evaluateFunction(allArgumentsToPerfMetric);
                    log.debug("Calculating for performance metric: {}, the resulted value: {} will be placed under name {}", metric.getName(), performanceMetricEstimation, metric.getPerformanceIndicatorName());
                    performanceIndicators.put(metric.getPerformanceIndicatorName(), performanceMetricEstimation);

                }
            }
            else {
                log.info("PM returned predictions, injecting them into utility function");

                for (PerformanceMetric metric: performanceMetricConverter.getPerformanceMetrics()){
                    log.debug("Converting value {} from performance metric {} to performance indicator {})",
                            predictionResult.get(metric.getName()), metric.getName(), metric.getPerformanceIndicatorName());
                    performanceIndicators.put(metric.getPerformanceIndicatorName(), predictionResult.get(metric.getName()));
                }

            }
            log.debug("Performance indicators list:{}", performanceIndicators.toString());
            performanceMetricConverter.setPerformanceIndicatorValues(performanceIndicators);
            log.info("Performance indicator values: {}", performanceMetricConverter.getPerformanceIndicatorValues().toString());
            this.converters.add(performanceMetricConverter);
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

    private void checkWeightsOfUtilityComponents(List<Map.Entry<TemplateProvider.AvailableTemplates, Double>> utilityComponents) {
        if (utilityComponents.stream().map(Map.Entry::getValue).reduce(0.0, Double::sum) > 1.0
                || utilityComponents.stream().map(Map.Entry::getValue).anyMatch(weight -> weight < 0)) {
            throw new RuntimeException("Sum of weights must be smaller or equal to 1 and non-negative!");
        }
    }
}
