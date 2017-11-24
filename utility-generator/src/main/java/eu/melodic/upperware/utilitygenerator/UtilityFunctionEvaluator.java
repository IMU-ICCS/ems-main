package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import solver.variables.IntVar;

import java.util.Collection;

public interface UtilityFunctionEvaluator {

  double evaluate(Collection<VirtualMachine> newConfiguration);
  double evaluate(IntVar[] newConfiguration);

  void setActualConfiguration(Collection<VirtualMachine> configuration);
}
