package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import org.apache.commons.math3.distribution.*;

import lombok.extern.slf4j.Slf4j;


import java.util.Collection;
import java.util.Map;

@Slf4j
public class UtilityFunctionEvaluatorExample implements UtilityFunctionEvaluator{

  private static final double ALPHA = 7;
  private final double beta;
  private final double maxResponseTime;
  private final double nomResponseTime;
  private final double costWeight;

  private BetaDistribution responseUtilityFunction;
  private double avgResponseTime;

  private Collection<VirtualMachine> actualConfiguration;
  private double actUtilityCost;


  public UtilityFunctionEvaluatorExample(Collection<VirtualMachine> actualConfiguration,
    Map<MetricType, Metric> metrics){

    this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME).getValue();
    this.nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME).getValue();
    this.costWeight = metrics.get(MetricType.COST_WEIGHT).getValue();
    this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME).getValue();


    this.actualConfiguration = actualConfiguration;
    actUtilityCost = 1;


    this.beta = ALPHA * (this.maxResponseTime / this.nomResponseTime -1);

    responseUtilityFunction = new BetaDistribution(ALPHA, beta);

  }

  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {

    double nextAvgResponseTime = estimateNextAvgResponseTime(avgResponseTime, newConfiguration);
    return (1-costWeight) * evaluateResponseUtilityFunction(nextAvgResponseTime/maxResponseTime)
      + costWeight * evaluateCostUtilityFunction(newConfiguration);
  }

  @Override
  public void setActualConfiguration(Collection<VirtualMachine> configuration) {
    actUtilityCost = evaluateCostUtilityFunction(configuration);
    actualConfiguration = configuration;
  }

  private double evaluateResponseUtilityFunction(double x){
    //log.info("evaluateResponseUtilityFunction: x = " + x);
    double result = responseUtilityFunction.density(x);
    //log.info("evaluateResponseUtilityFunction: result = " + result);

    double max = responseUtilityFunction.density((ALPHA-1)/(ALPHA+beta-2));
    double normalizedResult = normalize(0,max,result);


    log.info("evaluateResponseUtilityFunction: normalized result = {}", normalizedResult);
    return normalizedResult;
  }

  private double evaluateCostUtilityFunction(Collection<VirtualMachine> newConfiguration){

    double oldCost = calculateCost(actualConfiguration);
    double newCost = calculateCost(newConfiguration);
    double result = Math.min(1, actUtilityCost * oldCost / newCost);

    log.info("evaluateCostUtilityFunction: result = {}",result);
    return result;

  }

  private double calculateCost(Collection<VirtualMachine> vms){
    double cost = 0.0;
    for (VirtualMachine vm: vms){
      cost+= vm.getCount() * vm.getCost();
    }

    return cost;
  }


  private double estimateNextAvgResponseTime(double avgResponseTime, Collection<VirtualMachine> newConfig){

    double nextAvgResponseTime = (countVirtualMachines(actualConfiguration) * avgResponseTime)
      / countVirtualMachines(newConfig);
    log.info("estimate Time: {}", nextAvgResponseTime);
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




}
