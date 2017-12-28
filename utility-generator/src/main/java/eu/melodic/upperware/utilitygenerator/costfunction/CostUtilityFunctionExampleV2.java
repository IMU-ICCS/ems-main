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

@Slf4j
public class CostUtilityFunctionExampleV2 extends CostUtilityFunction {

  private boolean isReconfig;
  private double prevUtilityCost;

  public CostUtilityFunctionExampleV2(boolean isReconfig){
    this.isReconfig = isReconfig;
    this.prevUtilityCost = 1.0;
  }

  @Override
  public double evaluateCostUtilityFunction(Collection<Component> actualConfiguration,
      Collection<Component> newConfiguration) {

    double oldCost = 1.0; //FIXME - how to set oldCost?
    if (isReconfig){
      oldCost = calculateCost(actualConfiguration);
    }

    double newCost = calculateCost(newConfiguration);
    double result = Math.min(1, prevUtilityCost * oldCost / newCost);

    prevUtilityCost = result;
    log.info("evaluateCostUtilityFunction: result = {}",result);
    return result;
  }
}
