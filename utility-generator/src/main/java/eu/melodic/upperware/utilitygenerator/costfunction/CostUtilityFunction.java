/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

public abstract class CostUtilityFunction {


  public abstract double evaluateCostUtilityFunction(Collection<VirtualMachine> actualConfiguration,
    Collection<VirtualMachine> newConfiguration);

  double calculateCost(Collection<VirtualMachine> vms){

    return vms.stream().mapToDouble(vm -> vm.getCount() * vm.getCost()).sum();

  }
}
