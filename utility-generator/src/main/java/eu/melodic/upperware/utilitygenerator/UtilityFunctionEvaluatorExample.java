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

@Slf4j
public class UtilityFunctionEvaluatorExample implements UtilityFunctionEvaluator{

  private static final double ALPHA = 7;

  private double beta;
  private boolean isReconfig;

  private double maxResponseTime;
  private double nomResponseTime;
  private double costWeight;
  private double avgResponseTime;

  private Collection<VirtualMachine> actConfiguration;

  private CostUtilityFunction costUtilityFunction;
  private BetaDistribution responseUtilityFunction;



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

  @Override
  public double evaluate(IntVar[] newConfiguration) {

    Collection<VirtualMachine> newConfig = new ArrayList<>();

    for (IntVar var : newConfiguration) {
      //FIXME - parsing variables and get cost
      if (!(var.getName().startsWith("IntVar"))) {
        //log.info("Solution " + var);
        double cost = 10.0;
        if (var.getName().contains("xlarge")) {
          cost *= 4;
        } else if (var.getName().contains("large")) {
          cost *= 3;
        } else if (var.getName().contains("medium")) {
          cost *= 2;

        }
        newConfig.add(new VirtualMachine(var.getName(), cost, var.getValue()));
      }
    }
    return evaluate(newConfig);

  }

/*
  @Override
  public void setActualConfiguration(Collection<VirtualMachine> configuration) {
    actUtilityCost = evaluateCostUtilityFunction(configuration);
    actualConfiguration = configuration;
  }*/

  private double evaluateResponseUtilityFunction(double x){
    //log.info("evaluateResponseUtilityFunction: x = " + x);
    double result = responseUtilityFunction.density(x);
    //log.info("evaluateResponseUtilityFunction: result = " + result);

    double max = responseUtilityFunction.density((ALPHA-1)/(ALPHA+beta-2));
    double normalizedResult = normalize(0,max,result);


    log.info("evaluateResponseUtilityFunction: normalized result = {}", normalizedResult);
    return normalizedResult;
  }

  private double estimateNextAvgResponseTime(double avgResponseTime, Collection<VirtualMachine> newConfig){

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

  private int countVirtualMachines(Collection<VirtualMachine> configuration){
    int counter = 0;
    for (VirtualMachine vm: configuration){
      counter += vm.getCount();
    }
    return counter;
  }

  private double normalize(double min, double max, double x){
    return (x-min)/(max-min);
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
