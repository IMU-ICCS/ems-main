package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class CostEvaluatorWithAbsoluteCost extends CostEvaluator {

  private final int maxNumberOfVms;


  public CostEvaluatorWithAbsoluteCost(int maxVms){
    this.maxNumberOfVms = maxVms;
  }

  @Override
  double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration, Collection<VirtualMachine> newConfiguration) {
    double newCost = calculateCost(newConfiguration);
    //int numberOfVirtualMachines = countVirtualMachines(newConfiguration);
    //double avgCost = newCost/numberOfVirtualMachines;
    double normalized = normalize(getLowestCost(newConfiguration), getHighestCost(newConfiguration), newCost);
    log.info("normalized Cost = {}", normalized);
    double result = 1 - normalized;
    log.info("evaluateCostUtilityFunction: result = {}",result);
    return result;
  }


  private double getLowestCost(Collection<VirtualMachine> configuration){
    double min = getHighestCost(configuration);
    for (VirtualMachine vm: configuration){
      if (min> vm.getCost()){
        min = vm.getCost();
      }
    }
    return min;
  }

  private double getHighestCost(Collection<VirtualMachine> configuration){
    double max = 0.0;
    for (VirtualMachine vm: configuration){
      if (max< vm.getCost()){
        max = vm.getCost();
      }
    }
    return max * maxNumberOfVms;
  }
}
