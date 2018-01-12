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
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.melodic.upperware.utilitygenerator.model.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator {

    private double maxRamUsage;
    private MetricDTO[] ramUsage;
    private CostUtilityFunction costUtilityFunction;


    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, NodeCandidates nodeCandidates) {
        this(variables, nodeCandidates, new CostUtilityFunctionFraction());
    }

    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, NodeCandidates nodeCandidates, CostUtilityFunction costUtilityFunction) {
        super(variables, nodeCandidates);
        this.costUtilityFunction = costUtilityFunction;
        //getAndAssignMetrics(metrics);
    }

    @Override
    public double evaluate(Collection<Component> newConfiguration) {

        double totalUseOfRam = countTotalRamUsage(this.ramUsage);
        long totalRamInNewConfiguration = newConfiguration.stream()
                .mapToLong(Component::getFullRam)
                .sum();

        System.out.println("total Ram In New Configuration: " + totalRamInNewConfiguration);
        System.out.println("total use of Ram: " + totalUseOfRam);

        if ((totalRamInNewConfiguration > totalUseOfRam) && ((totalUseOfRam / totalRamInNewConfiguration) < maxRamUsage)) {
            return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
        }
        return 0;
    }

    private void getAndAssignMetrics(Map<MetricType, MetricDTO[]> metrics) {
        this.maxRamUsage = metrics.get(MetricType.MAX_RAM_USAGE)[0].getValue();
        this.ramUsage = metrics.get(MetricType.RAM_USAGE);

    }

    private double countTotalRamUsage(MetricDTO[] ramUsage) {
        double totalRamUsage = 0.0;
        for (MetricDTO metric : ramUsage) {
            long ram = getRamForVm(metric.getVmId());
            totalRamUsage += metric.getValue() * ram;
        }
        return totalRamUsage;
    }

    //fixme: how to match metric with node candidates?
    private long getRamForVm(String vmId) {
        return actConfiguration
                .stream()
                .filter(c -> vmId.equals(c.getNodeCandidate().getHardware().getName()))
                .findFirst()
                .get()
                .getNodeCandidate()
                .getHardware()
                .getRam();
    }

  /* for tests */

    public UtilityFunctionEvaluatorCAS(Map<MetricType, MetricDTO[]> metrics,
            Collection<Component> actConfiguration, boolean isReconfig,
            CostUtilityFunction costUtilityFunction) {

        super(actConfiguration, isReconfig);
        this.costUtilityFunction = costUtilityFunction;
        getAndAssignMetrics(metrics);

    }

}
