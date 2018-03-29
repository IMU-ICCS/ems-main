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

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countVirtualMachines;
import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertToIntMetric;


@Slf4j
public class UtilityFunctionEvaluatorFCR extends UtilityFunctionEvaluator {

    private static final double ALPHA = 7;

    //hardcoded for Release 1.5
    private static final double COST_WEIGHT = 0.5;
    private static final double NOM_RESPONSE_TIME = 700;
    private static final double MAX_RESPONSE_TIME = 1000;

    private static final String METRIC_AVG_RESPONSE_TIME = "METRIC_TR_AVG";

    private double beta;
    private IntMetric avgResponseTime;
    private BetaDistribution responseUtilityFunction;
    private CostUtilityFunction costUtilityFunction;


    /* constructors */

    public UtilityFunctionEvaluatorFCR(List<VariableDTO> variables, List<Var> deployedSolution,
            NodeCandidates nodeCandidates, List<MetricDTO> metricDTOs) {
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

        this.avgResponseTime = convertToIntMetric(metricDTOS, METRIC_AVG_RESPONSE_TIME, MetricType.AVG_RESPONSE_TIME, 0);

        if (this.avgResponseTime.getValue() > MAX_RESPONSE_TIME) {
            log.info("Setting AVG Response Time to MAX_RESPONSE_TIME={}", MAX_RESPONSE_TIME);
            this.avgResponseTime = new IntMetric(MetricType.AVG_RESPONSE_TIME, METRIC_AVG_RESPONSE_TIME, (int) MAX_RESPONSE_TIME);
        }
        this.beta = ALPHA * (MAX_RESPONSE_TIME / NOM_RESPONSE_TIME - 1);
        this.responseUtilityFunction = new BetaDistribution(ALPHA, beta);

    }

    /* for tests */

    public UtilityFunctionEvaluatorFCR(Collection<ConfigurationElement> actConfiguration,
            boolean isReconfig, CostUtilityFunction costUtilityFunction, List<MetricDTO> metricDTOs) {
        super(actConfiguration, isReconfig);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = costUtilityFunction;
        log.info("Utility function was created");
    }


}
