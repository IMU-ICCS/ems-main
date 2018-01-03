/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

import java.util.Collection;

public class UtilityFunctionEvaluatorCETraffic extends UtilityFunctionEvaluator{




  public UtilityFunctionEvaluatorCETraffic(ConstraintProblem cp){
    super(cp);
  }


  @Override
  public double evaluate(Collection<Component> newConfiguration) {
    return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
  }

  /* for tests*/

  public UtilityFunctionEvaluatorCETraffic(Collection<Component> actConfiguration, boolean isReconfig, CostUtilityFunction costUtilityFunction) {
    super(actConfiguration, isReconfig, costUtilityFunction);
  }
}
