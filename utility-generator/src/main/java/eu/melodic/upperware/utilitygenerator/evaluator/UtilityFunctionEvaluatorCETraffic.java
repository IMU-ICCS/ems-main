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
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countNumberOfCores;
import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertToDoubleMetric;

@Slf4j
public class UtilityFunctionEvaluatorCETraffic extends UtilityFunctionEvaluator {

    private CostUtilityFunction costUtilityFunction;

    /* hardcoded for release 1.5 */
    private static final String METRIC_MINIMUM_CORES = "METRIC_MinimumCores";
    private DoubleMetric minimumCores;


    public UtilityFunctionEvaluatorCETraffic(List<VariableDTO> variables, UtilityGeneratorProperties properties, List<Var> deployedSolution, NodeCandidates nodeCandidates,
            List<MetricDTO> metricDTOs) {
        super(variables, properties, deployedSolution, nodeCandidates);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = new CostUtilityFunctionFraction();
    }


    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {

        if (isReconfig && minimumCores.getValue() > (double) countNumberOfCores(newConfiguration)){
            log.warn("Solution does not allow to complete all simulations by the deadline");
            return 0.0;
        } else {
            return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
        }
    }

    private void getAndAssignMetrics(List<MetricDTO> metricDTOS) {

        this.minimumCores = convertToDoubleMetric(metricDTOS, METRIC_MINIMUM_CORES, MetricType.MINIMUM_CORES, 0.0);
    }

    /* for tests*/

    public UtilityFunctionEvaluatorCETraffic(Collection<ConfigurationElement> actConfiguration, boolean isReconfig,
            CostUtilityFunction costUtilityFunction, List<MetricDTO> metricDTOs) {
        super(actConfiguration, isReconfig);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = costUtilityFunction;
    }
}
