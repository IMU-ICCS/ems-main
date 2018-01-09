/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.Collection;
import java.util.Map;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countVirtualMachines;
import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;

@Slf4j
public class UtilityFunctionEvaluatorFCR extends UtilityFunctionEvaluator {

    private static final double ALPHA = 7;
    private double beta;

    private double maxResponseTime;
    private double costWeight;
    private double avgResponseTime;

    private BetaDistribution responseUtilityFunction;

    private CostUtilityFunction costUtilityFunction;



  /* constructors */

    public UtilityFunctionEvaluatorFCR(ConstraintProblem cp, Map<MetricType, Metric[]> metrics) {

        super(cp);
        getAndAssignMetrics(metrics);
        this.costUtilityFunction = new CostUtilityFunctionExample(isReconfig);
    }


    @Override
    public double evaluate(Collection<Component> newConfiguration) {
        double nextAvgResponseTime = estimateNextAvgResponseTime(avgResponseTime, newConfiguration);
        return (1 - costWeight) * evaluateResponseUtilityFunction(nextAvgResponseTime / maxResponseTime)
                + costWeight * costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
    }


    /* response time utility function */
    private double evaluateResponseUtilityFunction(double x) {
        //log.info("evaluateResponseUtilityFunction: x = " + x);
        double result = responseUtilityFunction.density(x);
        //log.info("evaluateResponseUtilityFunction: result = " + result);

        double max = responseUtilityFunction.density((ALPHA - 1) / (ALPHA + beta - 2));

        return normalize(0, max, result);
    }

    private double estimateNextAvgResponseTime(double avgResponseTime,
            Collection<Component> newConfig) {

        double nextAvgResponseTime;

        if (isReconfig) {
            nextAvgResponseTime = (countVirtualMachines(actConfiguration) * avgResponseTime)
                    / countVirtualMachines(newConfig);
        } else { //FIXME - for configuration - how to estimate?
            nextAvgResponseTime = avgResponseTime / countVirtualMachines(newConfig);
        }

        //log.info("estimate Time: {}", nextAvgResponseTime);
        return nextAvgResponseTime;

    }


    /* utils for constructors */
    private void getAndAssignMetrics(Map<MetricType, Metric[]> metrics) {
        this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME)[0].getValue();
        this.costWeight = metrics.get(MetricType.COST_WEIGHT)[0].getValue();
        this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME)[0].getValue();
        double nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME)[0].getValue();

        this.beta = ALPHA * (this.maxResponseTime / nomResponseTime - 1);
        this.responseUtilityFunction = new BetaDistribution(ALPHA, beta);
    }

  /* for tests */

    public UtilityFunctionEvaluatorFCR(Map<MetricType, Metric[]> metrics, Collection<Component> actConfiguration,
            boolean isReconfig, CostUtilityFunction costUtilityFunction) {

        super(actConfiguration, isReconfig);
        this.costUtilityFunction = costUtilityFunction;
        getAndAssignMetrics(metrics);
    }


}
