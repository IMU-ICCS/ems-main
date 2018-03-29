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
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction;
import eu.melodic.upperware.utilitygenerator.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countNumberOfCores;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertToDoubleMetric;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertToIntMetric;

@Slf4j
public class UtilityFunctionEvaluatorCETraffic extends UtilityFunctionEvaluator {

    private CostUtilityFunction costUtilityFunction;

    /* hardcoded for release 1.5 */
    private static final String METRIC_SIMULATIONS_LEFT = "METRIC_SimulationLeftNumber";
    private static final String METRIC_ET_PERCENTILE = "METRIC_ETPercentile,";
    private static final String METRIC_REMAINING_SIMULATION_TIME = "METRIC_RemainingSimulationTimeMetric";

    private IntMetric simulationLeftNumber;
    private DoubleMetric ETPercentile;
    private IntMetric remainingSimulationTime;


    public UtilityFunctionEvaluatorCETraffic(List<VariableDTO> variables, List<MetricDTO> metricDTOs, List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        super(variables, deployedSolution, nodeCandidates);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = new CostUtilityFunctionFraction();
    }


    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {

        if (estimateTimeToComplete(newConfiguration)<= remainingSimulationTime.getValue()){
            return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
        }
        else {
            log.warn("Solution does not allow to complete all simulations by the deadline");
            return 0.0;
        }
    }

    private void getAndAssignMetrics(List<MetricDTO> metricDTOS) {

        this.remainingSimulationTime = convertToIntMetric(metricDTOS, METRIC_REMAINING_SIMULATION_TIME, MetricType.REMAINING_SIMULATION_TIME, 0);
        this.simulationLeftNumber = convertToIntMetric(metricDTOS, METRIC_SIMULATIONS_LEFT, MetricType.SIMULATIONS_LEFT, 0);
        this.ETPercentile = convertToDoubleMetric(metricDTOS, METRIC_ET_PERCENTILE, MetricType.ET_PERCENTILE, 0.0);
    }


    private double estimateTimeToComplete(Collection<ConfigurationElement> newConfiguration){
        double result = simulationLeftNumber.getValue()/countNumberOfCores(newConfiguration) * ETPercentile.getValue();
        log.debug("Estimated time to complete all tasks is {}", result);
        return result;
    }

    /* for tests*/

    public UtilityFunctionEvaluatorCETraffic(Collection<ConfigurationElement> actConfiguration, boolean isReconfig,
            CostUtilityFunction costUtilityFunction) {
        super(actConfiguration, isReconfig);
        this.costUtilityFunction = costUtilityFunction;
    }
}
