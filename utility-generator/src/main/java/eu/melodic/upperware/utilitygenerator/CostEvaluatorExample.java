package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class CostEvaluatorExample extends CostEvaluator{

  private double actUtilityCost;
  private boolean isReconfig;


  public CostEvaluatorExample(boolean isReconfig){

    this.isReconfig = isReconfig;
    actUtilityCost = 1;
  }


  @Override
  double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration, Collection<VirtualMachine>
    newConfiguration) {
    double oldCost = 1.0; //FIXME - how to set oldCost?
    if (isReconfig){
      oldCost = calculateCost(actualConfiguration);
    }

    double newCost = calculateCost(newConfiguration);
    double result = Math.min(1, actUtilityCost * oldCost / newCost);

    log.info("evaluateCostUtilityFunction: result = {}",result);
    return result;
  }
}
