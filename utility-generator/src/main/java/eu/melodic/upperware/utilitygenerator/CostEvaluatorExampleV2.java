package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public class CostEvaluatorExampleV2 extends CostEvaluator{


  @Override
  double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration, Collection<VirtualMachine> newConfiguration) {
    return 0;
  }
}
