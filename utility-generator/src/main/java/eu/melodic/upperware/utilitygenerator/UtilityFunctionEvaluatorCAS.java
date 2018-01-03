/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;
import java.util.Map;

public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator{

  private double maxRamUsage;
  private Metric[] ramUsage;


  public UtilityFunctionEvaluatorCAS(Map<MetricType, Metric> metrics, Metric[] ramUsage,
      Collection<VirtualMachine> actConfiguration, boolean isReconfig,
      CostUtilityFunction costUtilityFunction) {

    super(actConfiguration, isReconfig, costUtilityFunction);
    getAndAssignMetrics(metrics, ramUsage);

  }

  public UtilityFunctionEvaluatorCAS(Map<MetricType, Metric> metrics, Metric[] ramUsage,
      Collection<VirtualMachine> actConfiguration, boolean isReconfig) {
    super(actConfiguration, isReconfig);
    getAndAssignMetrics(metrics, ramUsage);
  }

  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {

    double totalUseOfRam = countTotalRamUsage(ramUsage);
    int totalRamInNewConfiguration = 0;

    for (VirtualMachine vm : newConfiguration){
      totalRamInNewConfiguration += vm.getRam() * vm.getCount();
    }

    System.out.println("total Ram In New Configuration: " + totalRamInNewConfiguration);
    System.out.println("total use of Ram: " + totalUseOfRam);

    if ((totalRamInNewConfiguration > totalUseOfRam) && ((totalUseOfRam/totalRamInNewConfiguration) < maxRamUsage)){
      return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
    }
    return 0;
  }

  private void getAndAssignMetrics(Map<MetricType, Metric> metrics, Metric[] ramUsage){
    this.maxRamUsage = metrics.get(MetricType.MAX_RAM_USAGE).getValue();
    this.ramUsage = ramUsage;

  }

  private double countTotalRamUsage(Metric[] ramUsage){
    double totalRamUsage = 0.0;
    for (Metric metric: ramUsage){
      int ram = getRamForVm(metric.getVmId());
      totalRamUsage += metric.getValue() * ram;

      //System.out.println("count total ram usage, act is " + totalRamUsage);
    }
    return totalRamUsage;
  }

  private int getRamForVm(String vmName){
    return actConfiguration.stream().filter(vm -> vm.getId().equals(vmName)).findFirst().get().getRam();
  //fixme
  }
}
