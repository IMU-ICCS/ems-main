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

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertToIntMetric;

@Slf4j
public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator {

    private CostUtilityFunction costUtilityFunction;

    /* hardcoded for release 1.5 */
    private static final String METRIC_RAM_USAGE = "METRIC_RAMavg";
    private static final double MAX_RAM_USAGE = 0.7;
    private IntMetric ramUsage;



    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, UtilityGeneratorProperties properties, List<Var> deployedSolution,
            NodeCandidates nodeCandidates, List<MetricDTO> metricDTOs) {
        super(variables, properties, deployedSolution, nodeCandidates);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = new CostUtilityFunctionFraction();
    }

    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, UtilityGeneratorProperties properties, List<Var> deployedSolution, NodeCandidates nodeCandidates, CostUtilityFunction costUtilityFunction) {
        super(variables, properties, deployedSolution, nodeCandidates);
        this.costUtilityFunction = costUtilityFunction;
    }

    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {

        if (isReconfig){
            long totalRamAct = actConfiguration.stream().mapToLong(ConfigurationElement::getFullRam).sum();
            long totalRamNew = newConfiguration.stream().mapToLong(ConfigurationElement::getFullRam).sum();
            long totalRamUsage = totalRamAct * ramUsage.getValue()/100;

            if (totalRamUsage/(double)totalRamNew > MAX_RAM_USAGE){
                log.warn("Solution does not allow to keep required percent of ram usage");
                return 0.0;
            }
        }
        //if ((totalRamNew > ramUsage.getValue()) && (ramUsage.getValue()/(double)totalRamNew < MAX_RAM_USAGE)){
        return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);

    }

    private void getAndAssignMetrics(List<MetricDTO> metricDTOS) {

        this.ramUsage = convertToIntMetric(metricDTOS, METRIC_RAM_USAGE, MetricType.RAM_USAGE, 0);
    }

    /* for tests */

    public UtilityFunctionEvaluatorCAS(Collection<ConfigurationElement> actConfiguration, boolean isReconfig,
            CostUtilityFunction costUtilityFunction, List<MetricDTO> metricDTOs) {

        super(actConfiguration, isReconfig);
        getAndAssignMetrics(metricDTOs);
        this.costUtilityFunction = costUtilityFunction;
    }

}
