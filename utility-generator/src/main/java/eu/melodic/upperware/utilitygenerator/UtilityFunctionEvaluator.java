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

import java.util.Collection;

public interface UtilityFunctionEvaluator {

  double evaluate(Collection<VirtualMachine> newConfiguration);
  double evaluate(IntVar[] newConfiguration);

  //void setActualConfiguration(Collection<VirtualMachine> configuration);
}
