/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;

import java.util.Collection;
import java.util.Map;

public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator{

  private double maxRamUsage;
  private Metric[] ramUsage;


  public UtilityFunctionEvaluatorCAS(Map<MetricType, Metric[]> metrics,
    Collection<Component> actConfiguration, boolean isReconfig,
    CostUtilityFunction costUtilityFunction) {

    super(actConfiguration, isReconfig, costUtilityFunction);
    getAndAssignMetrics(metrics);

  }

  public UtilityFunctionEvaluatorCAS(Map<MetricType, Metric[]> metrics,
    Collection<Component> actConfiguration, boolean isReconfig) {
    super(actConfiguration, isReconfig);
    getAndAssignMetrics(metrics);
  }

  @Override
  public double evaluate(Collection<Component> newConfiguration) {

    double totalUseOfRam = countTotalRamUsage(this.ramUsage);
    int totalRamInNewConfiguration = 0;

    for (Component component : newConfiguration){
      totalRamInNewConfiguration += component.getNodeCandidate().getHardware().getRam() * component.getCardinality();
    }

    System.out.println("total Ram In New Configuration: " + totalRamInNewConfiguration);
    System.out.println("total use of Ram: " + totalUseOfRam);

    if ((totalRamInNewConfiguration > totalUseOfRam) && ((totalUseOfRam/totalRamInNewConfiguration) < maxRamUsage)){
      return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
    }
    return 0;
  }

  private void getAndAssignMetrics(Map<MetricType, Metric[]> metrics){
    this.maxRamUsage = metrics.get(MetricType.MAX_RAM_USAGE)[0].getValue();
    this.ramUsage = metrics.get(MetricType.RAM_USAGE);

  }

  private double countTotalRamUsage(Metric[] ramUsage){
    double totalRamUsage = 0.0;
    for (Metric metric: ramUsage){
      long ram = getRamForVm(metric.getVmId());
      totalRamUsage += metric.getValue() * ram;

    }
    return totalRamUsage;
  }

  //fixme: to powinno być po czymś innym dopasowywane - metryki z maszynami
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

}
