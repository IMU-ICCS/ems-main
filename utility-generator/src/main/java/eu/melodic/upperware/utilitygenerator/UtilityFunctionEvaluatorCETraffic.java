package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public class UtilityFunctionEvaluatorCETraffic extends UtilityFunctionEvaluator{

  private CostUtilityFunction costUtilityFunction;


  public UtilityFunctionEvaluatorCETraffic(Collection<VirtualMachine> actConfiguration, boolean isReconfig,
    CostUtilityFunction costUtilityFunction){
    this.actConfiguration = actConfiguration;
    this.costUtilityFunction = costUtilityFunction;
    this.isReconfig = isReconfig;
  }

  public UtilityFunctionEvaluatorCETraffic(Collection<VirtualMachine> actConfiguration, boolean isReconfig){
    this.actConfiguration = actConfiguration;
    this.costUtilityFunction = new CostUtilityFunctionExample(isReconfig);
    this.isReconfig = isReconfig;
  }


  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {
    return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
  }

}
