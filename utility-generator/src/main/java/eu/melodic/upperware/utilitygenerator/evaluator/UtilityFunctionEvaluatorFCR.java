/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample;
import eu.melodic.upperware.utilitygenerator.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countVirtualMachines;
import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;


@Slf4j
public class UtilityFunctionEvaluatorFCR extends UtilityFunctionEvaluator {

    private static final double ALPHA = 7;

    //hardcoded for Release 1.5
    private static final double COST_WEIGHT = 0.5;
    private static final double NOM_RESPONSE_TIME = 500;
    private static final double MAX_RESPONSE_TIME = 1000;

    private static final String METRIC_AVG_RESPONSE_TIME = "METRIC_TR_AVG";

    private double beta;
    private IntMetric avgResponseTime;
    private BetaDistribution responseUtilityFunction;
    private CostUtilityFunction costUtilityFunction;


    /* constructors */

    public UtilityFunctionEvaluatorFCR(List<VariableDTO> variables, List<MetricDTO> metricDTOs,
            List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        super(variables, deployedSolution, nodeCandidates);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = new CostUtilityFunctionExample(isReconfig);
        log.info("Utility function was created");

    }




    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {
        double nextAvgResponseTime = estimateNextAvgResponseTime(avgResponseTime.getValue(), newConfiguration);
        return (1 - COST_WEIGHT) * evaluateResponseUtilityFunction(nextAvgResponseTime / MAX_RESPONSE_TIME)
                + COST_WEIGHT * costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
    }


    /* response time utility function */
    private double evaluateResponseUtilityFunction(double x) {
        double result = responseUtilityFunction.density(x);

        double max = responseUtilityFunction.density((ALPHA - 1) / (ALPHA + beta - 2));

        double normalizedResult = normalize(0, max, result);

        log.debug("Response Utility Function value = {}", normalizedResult);
        return normalizedResult;
    }

    private double estimateNextAvgResponseTime(int avgResponseTime,
            Collection<ConfigurationElement> newConfig) {

        double nextAvgResponseTime;

        if (isReconfig) {
            nextAvgResponseTime = (countVirtualMachines(actConfiguration) * avgResponseTime)
                    / countVirtualMachines(newConfig);
        } else { //FIXME - for configuration - how to estimate?
            nextAvgResponseTime = avgResponseTime / countVirtualMachines(newConfig);
        }

        return nextAvgResponseTime;

    }

    private void getAndAssignMetrics(List<MetricDTO> metricDTOS) {

        Optional<MetricDTO> optionalMetric = metricDTOS.stream()
                .filter(metric -> metric.getName().equals(METRIC_AVG_RESPONSE_TIME))
                .findAny();

        Integer valueRT = 0;

        if (optionalMetric.isPresent()) {
            valueRT = ((IntMetricDTO) optionalMetric.get()).getValue();
            log.info("Get metric: AVG Response Time = {}", valueRT);
        } else {
            log.warn("Metric needed for FCR utility function: {} does not exist, setting value to 0", METRIC_AVG_RESPONSE_TIME);
        }
        this.avgResponseTime = new IntMetric(MetricType.AVG_RESPONSE_TIME, METRIC_AVG_RESPONSE_TIME, valueRT);

        this.beta = ALPHA * (MAX_RESPONSE_TIME / NOM_RESPONSE_TIME - 1);
        this.responseUtilityFunction = new BetaDistribution(ALPHA, beta);

    }

    /* for tests */

    public UtilityFunctionEvaluatorFCR(List<MetricDTO> metricDTOs, Collection<ConfigurationElement> actConfiguration,
            boolean isReconfig, CostUtilityFunction costUtilityFunction) {
        super(actConfiguration, isReconfig);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = costUtilityFunction;
        log.info("Utility function was created");
    }


}
