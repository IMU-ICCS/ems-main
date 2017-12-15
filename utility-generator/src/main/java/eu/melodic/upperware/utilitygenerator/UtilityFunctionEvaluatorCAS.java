package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator{


  public UtilityFunctionEvaluatorCAS(Collection<VirtualMachine> actConfiguration, boolean isReconfig,
    CostUtilityFunction costUtilityFunction) {
    super(actConfiguration, isReconfig, costUtilityFunction);

  }

  public UtilityFunctionEvaluatorCAS(Collection<VirtualMachine> actConfiguration, boolean isReconfig) {
    super(actConfiguration, isReconfig);
  }

  @Override
  public double evaluate(Collection<VirtualMachine> newConfiguration) {
    return 0;
  }
}
