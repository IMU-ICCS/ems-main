/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;
import solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Collection;

public abstract class UtilityFunctionEvaluator {

  boolean isReconfig;
  Collection<VirtualMachine> actConfiguration;


  public abstract double evaluate(Collection<VirtualMachine> newConfiguration);
  public double evaluate(IntVar[] newConfiguration){
    Collection<VirtualMachine> newConfig = new ArrayList<>();

    for (IntVar var : newConfiguration) {
      //FIXME - parsing variables and get cost
      if (!(var.getName().startsWith("IntVar"))) {
        //log.info("Solution " + var);
        double cost = 10.0;
        if (var.getName().contains("xlarge")) {
          cost *= 4;
        } else if (var.getName().contains("large")) {
          cost *= 3;
        } else if (var.getName().contains("medium")) {
          cost *= 2;

        }
        newConfig.add(new VirtualMachine(var.getName(), cost, var.getValue()));
      }
    }
    return evaluate(newConfig);
  }

}
