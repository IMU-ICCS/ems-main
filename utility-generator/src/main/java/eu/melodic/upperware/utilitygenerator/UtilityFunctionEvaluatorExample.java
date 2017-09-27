package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import org.apache.commons.math3.distribution.*;

import java.util.Collection;

public class UtilityFunctionEvaluatorExample implements UtilityFunctionEvaluator{

  private final double alpha = 7;
  private final double beta;
  private final double maxResponseTime;
  private final double nomResponseTime;

  private final double costWeight;

  private BetaDistribution responseUtilityFunction;

  private double avgResponseTime;

  private Collection<VirtualMachine> actualConfiguration;
  private double actUtilityCost;


  public UtilityFunctionEvaluatorExample(Collection<VirtualMachine> actualConfiguration, Metric responseTime, double
    maxResponseTime,
    double nomResponseTime, double costWeight){

    this.maxResponseTime = maxResponseTime;
    this.nomResponseTime = nomResponseTime;
    this.costWeight = costWeight;
    this.actualConfiguration = actualConfiguration;
    actUtilityCost = 1;


    beta = alpha * (this.maxResponseTime / this.nomResponseTime -1);

    responseUtilityFunction = new BetaDistribution(alpha, beta);

    avgResponseTime = responseTime.getValue();
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
    System.out.println("evaluateResponseUtilityFunction: x = " + x);
    double result = responseUtilityFunction.density(x);
    System.out.println("evaluateResponseUtilityFunction: result = " + result);

    double min = responseUtilityFunction.getSupportLowerBound();
    double max = responseUtilityFunction.getSupportUpperBound();
    return normalize(min,max,x);
  }

  private double evaluateCostUtilityFunction(Collection<VirtualMachine> newConfiguration){

    double oldCost = calculateCost(actualConfiguration);
    double newCost = calculateCost(newConfiguration);
    double result = actUtilityCost * oldCost / newCost;

    System.out.println("evaluateCostUtilityFunction: oldCost = " + oldCost);
    System.out.println("evaluateCostUtilityFunction: newCost = " + newCost);
    System.out.println("evaluateCostUtilityFunction: result = " + result);
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

    double nextAvgResponseTime = (
      (countVirtualMachines(actualConfiguration)) * avgResponseTime)
      / countVirtualMachines(newConfig);
    System.out.println("estimate Time: " + nextAvgResponseTime);
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
    double result = (x-min)/(max-min);
    System.out.println("after normalization: " + result);
    return result;
  }




}
