package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public class CostUtilityFunctionFraction extends CostUtilityFunction{
  @Override
  public double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration, Collection<VirtualMachine> newConfiguration) {
    double cost = calculateCost(newConfiguration);
    double result = 1/cost;
    return result;
  }
}
