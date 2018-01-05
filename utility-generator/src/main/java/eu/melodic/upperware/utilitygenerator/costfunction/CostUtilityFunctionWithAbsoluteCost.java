/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.Component;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static eu.melodic.upperware.utilitygenerator.UtilityFunctionUtils.normalize;


@AllArgsConstructor
@Slf4j
public class CostUtilityFunctionWithAbsoluteCost extends CostUtilityFunction {

  private final int maxNumberOfVms;

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
    return configuration
      .stream()
      .mapToDouble(c -> c.getNodeCandidate().getPrice())
      .min()
      .orElse(0.0);
  }

  private double getHighestCost(Collection<Component> configuration){
    double max = configuration
      .stream()
      .mapToDouble(c -> c.getNodeCandidate().getPrice())
      .max()
      .orElse(0.0);

    return max * maxNumberOfVms;
  }


}
