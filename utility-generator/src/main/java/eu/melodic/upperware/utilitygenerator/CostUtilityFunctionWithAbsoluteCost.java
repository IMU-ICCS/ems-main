/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class CostUtilityFunctionWithAbsoluteCost extends CostUtilityFunction {

  private final int maxNumberOfVms;


  public CostUtilityFunctionWithAbsoluteCost(int maxVms){
    this.maxNumberOfVms = maxVms;
  }

  @Override
  double evaluateCostUtilityFunction(Collection<VirtualMachine> act, Collection<VirtualMachine> newConfiguration) {

    double newCost = calculateCost(newConfiguration);

    //int numberOfVirtualMachines = countVirtualMachines(newConfiguration);
    //double avgCost = newCost/numberOfVirtualMachines;

    double normalized = normalize(getLowestCost(newConfiguration), getHighestCost(newConfiguration), newCost);
    double result = 1 - normalized;
    log.info("evaluateResponseUtilityFunction: normalized result = {}", result);
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
