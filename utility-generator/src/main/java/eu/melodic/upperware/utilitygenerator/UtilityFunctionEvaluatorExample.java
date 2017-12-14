/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.BetaDistribution;
import solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.countVirtualMachines;
import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;

@Slf4j
public class UtilityFunctionEvaluatorExample extends UtilityFunctionEvaluator{

  private static final double ALPHA = 7;

  private double beta;

  private double maxResponseTime;
  private double nomResponseTime;
  private double costWeight;
  private double avgResponseTime;

  private CostUtilityFunction costUtilityFunction;
  private BetaDistribution responseUtilityFunction;


  /* constructors */

  public UtilityFunctionEvaluatorExample(Map<MetricType, Metric> metrics, boolean isReconfig,
    Collection<VirtualMachine> actConfiguration){

    getAndAssignMetrics(metrics);
    setFields(isReconfig, actConfiguration);

    this.costUtilityFunction = new CostUtilityFunctionExample(isReconfig);

  }

  public UtilityFunctionEvaluatorExample(Map<MetricType, Metric> metrics, boolean isReconfig,
    Collection<VirtualMachine> actConfiguration, CostUtilityFunction costUtilityFunction){

    getAndAssignMetrics(metrics);
    setFields(isReconfig, actConfiguration);

    this.costUtilityFunction = costUtilityFunction;
  }


  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {

    double nextAvgResponseTime = estimateNextAvgResponseTime(avgResponseTime, newConfiguration);
    return (1-costWeight) * evaluateResponseUtilityFunction(nextAvgResponseTime/maxResponseTime)
      + costWeight * costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
  }


  /* response time utility function */
  private double evaluateResponseUtilityFunction(double x){
    //log.info("evaluateResponseUtilityFunction: x = " + x);
    double result = responseUtilityFunction.density(x);
    //log.info("evaluateResponseUtilityFunction: result = " + result);

    double max = responseUtilityFunction.density((ALPHA-1)/(ALPHA+beta-2));
    double normalizedResult = normalize(0,max,result);


    log.info("evaluateResponseUtilityFunction: normalized result = {}", normalizedResult);
    return normalizedResult;
  }

  private double estimateNextAvgResponseTime(double avgResponseTime,
    Collection<VirtualMachine> newConfig){

    double nextAvgResponseTime;

    if (isReconfig){
      nextAvgResponseTime = (countVirtualMachines(actConfiguration) * avgResponseTime)
        / countVirtualMachines(newConfig);
    }
    else { //FIXME - for configuration - how to estimate?
      nextAvgResponseTime = avgResponseTime/countVirtualMachines(newConfig);
    }

    //log.info("estimate Time: {}", nextAvgResponseTime);
    return nextAvgResponseTime;

  }


  /* utils for constructors */
  private void getAndAssignMetrics(Map<MetricType, Metric> metrics){
    this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME).getValue();
    this.nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME).getValue();
    this.costWeight = metrics.get(MetricType.COST_WEIGHT).getValue();
    this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME).getValue();
  }

  private void setFields(boolean isReconfig, Collection<VirtualMachine> actualConfiguration){

    this.isReconfig = isReconfig;

    if (isReconfig){
      this.actConfiguration = actualConfiguration;
    }
    this.beta = ALPHA * (this.maxResponseTime / this.nomResponseTime -1);
    this.responseUtilityFunction = new BetaDistribution(ALPHA, beta);

  }


}
