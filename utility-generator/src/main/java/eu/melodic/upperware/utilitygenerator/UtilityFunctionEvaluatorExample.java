package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import org.apache.commons.math3.distribution.*;

import lombok.extern.slf4j.Slf4j;
import solver.variables.IntVar;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class UtilityFunctionEvaluatorExample implements UtilityFunctionEvaluator{

  private static final double ALPHA = 7;
  private final double beta;
  private final double maxResponseTime;
  private final double nomResponseTime;
  private final double costWeight;
  private final boolean isReconfig;

  private BetaDistribution responseUtilityFunction;

  private double avgResponseTime;

  private Collection<VirtualMachine> actualConfiguration;

  private CostEvaluator costEvaluator;



  public UtilityFunctionEvaluatorExample(Map<MetricType, Metric> metrics, boolean isReconfig,
    Collection<VirtualMachine> actConfiguration){

    this.isReconfig = isReconfig;
    this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME).getValue();
    this.nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME).getValue();
    this.costWeight = metrics.get(MetricType.COST_WEIGHT).getValue();
    this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME).getValue();

    if (isReconfig){
      this.actualConfiguration = actConfiguration;
    }

    this.costEvaluator = new CostEvaluatorExample(isReconfig);

    this.beta = ALPHA * (this.maxResponseTime / this.nomResponseTime -1);

    responseUtilityFunction = new BetaDistribution(ALPHA, beta);
  }

  public UtilityFunctionEvaluatorExample(Map<MetricType, Metric> metrics, boolean isReconfig){
    this.isReconfig = isReconfig;
    this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME).getValue();
    this.nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME).getValue();
    this.costWeight = metrics.get(MetricType.COST_WEIGHT).getValue();
    this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME).getValue();
    this.costEvaluator = new CostEvaluatorExample(isReconfig);
    this.beta = ALPHA * (this.maxResponseTime / this.nomResponseTime -1);
    responseUtilityFunction = new BetaDistribution(ALPHA, beta);
  }

  public UtilityFunctionEvaluatorExample(Map<MetricType, Metric> metrics, boolean isReconfig,
    Collection<VirtualMachine> actConfiguration, CostEvaluator costEvaluator){
    this.isReconfig = isReconfig;
    this.maxResponseTime = metrics.get(MetricType.MAX_RESPONSE_TIME).getValue();
    this.nomResponseTime = metrics.get(MetricType.NOM_RESPONSE_TIME).getValue();
    this.costWeight = metrics.get(MetricType.COST_WEIGHT).getValue();
    this.avgResponseTime = metrics.get(MetricType.AVG_RESPONSE_TIME).getValue();

    if (isReconfig){
      this.actualConfiguration = actConfiguration;
    }

    this.costEvaluator = costEvaluator;

    this.beta = ALPHA * (this.maxResponseTime / this.nomResponseTime -1);

    responseUtilityFunction = new BetaDistribution(ALPHA, beta);
  }


  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {

    double nextAvgResponseTime = estimateNextAvgResponseTime(avgResponseTime, newConfiguration);
    double result =  (1-costWeight) * evaluateResponseUtilityFunction(nextAvgResponseTime/maxResponseTime)
      + costWeight * costEvaluator.evaluateCostUtilityFunction(actualConfiguration, newConfiguration);

    log.info("result = {}", result);
    return result;
  }

  @Override
  public double evaluate(IntVar[] newConfiguration) {

    Collection<VirtualMachine> newConfig = new ArrayList<>();

    for (int j=0; j<newConfiguration.length; j++){
      IntVar var = newConfiguration[j];

      //FIXME - parsing variables and get costs
      if (!(var.getName().startsWith("IntVar"))){
        log.info("Solution " + var);
        double cost = 10.0;
        if (var.getName().contains("xlarge")) {
          cost *= 4;
        } else if (var.getName().contains("large")) {
          cost *= 3;

        }
        else if (var.getName().contains("medium")){
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


    //log.info("evaluateResponseUtilityFunction: normalized result = {}", normalizedResult);
    return normalizedResult;
  }

  private double estimateNextAvgResponseTime(double avgResponseTime, Collection<VirtualMachine> newConfig){

    double nextAvgResponseTime;
    //FIXME - for configuration? how to estimate?
    if (isReconfig){
      nextAvgResponseTime = (countVirtualMachines(actualConfiguration) * avgResponseTime)
        / countVirtualMachines(newConfig);
    }
    else {
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




}
