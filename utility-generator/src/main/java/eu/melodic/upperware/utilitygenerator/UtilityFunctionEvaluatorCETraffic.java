package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public class UtilityFunctionEvaluatorCETraffic extends UtilityFunctionEvaluator{


  public UtilityFunctionEvaluatorCETraffic(Collection<VirtualMachine> actConfiguration, boolean isReconfig, CostUtilityFunction costUtilityFunction) {
    super(actConfiguration, isReconfig, costUtilityFunction);
  }

  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {
    return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
  }

}
