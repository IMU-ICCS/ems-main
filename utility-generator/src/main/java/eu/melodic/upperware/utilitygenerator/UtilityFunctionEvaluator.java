package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public interface UtilityFunctionEvaluator {

  double evaluate(Collection<VirtualMachine> newConfiguration);

  void setActualConfiguration(Collection<VirtualMachine> configuration);
}
