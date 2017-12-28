/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;


@Slf4j
public class CostUtilityFunctionWithAbsoluteCost extends CostUtilityFunction {

  private final int maxNumberOfVms;


  public CostUtilityFunctionWithAbsoluteCost(int maxVms){
    this.maxNumberOfVms = maxVms;
  }


  @Override
  public double evaluateCostUtilityFunction(Collection<Component> actualConfiguration,
      Collection<Component> newConfiguration) {

    double newCost = calculateCost(newConfiguration);
    //int numberOfVirtualMachines = countVirtualMachines(newConfiguration);
    //double avgCost = newCost/numberOfVirtualMachines;

    double normalized = normalize(getLowestCost(newConfiguration), getHighestCost(newConfiguration), newCost);
    double result = 1 - normalized;
    log.info("evaluateResponseUtilityFunction: normalized result = {}", result);
    return result;
  }


  private double getLowestCost(Collection<Component> configuration){
    double min = getHighestCost(configuration);
    for (Component p: configuration){
      if (min> p.getNodeCandidate().getPrice()){
        min = p.getNodeCandidate().getPrice();
      }
    }
    return min;
  }

  private double getHighestCost(Collection<Component> configuration){
    double max = 0.0;
    for (Component p: configuration){
      if (max< p.getNodeCandidate().getPrice()){
        max = p.getNodeCandidate().getPrice();
      }
    }
    return max * maxNumberOfVms;
  }


}
