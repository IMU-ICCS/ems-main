package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public abstract class CostEvaluator {

  abstract double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration,
    Collection<VirtualMachine> newConfiguration);

  double calculateCost(Collection<VirtualMachine> vms){
    double cost = 0.0;
    for (VirtualMachine vm: vms){
      cost+= vm.getCount() * vm.getCost();
    }

    return cost;
  }

  double normalize(double min, double max, double x){
    return (x-min)/(max-min);
  }
}
