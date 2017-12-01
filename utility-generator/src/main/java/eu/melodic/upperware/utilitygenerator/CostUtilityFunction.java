/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public abstract class CostUtilityFunction {


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
