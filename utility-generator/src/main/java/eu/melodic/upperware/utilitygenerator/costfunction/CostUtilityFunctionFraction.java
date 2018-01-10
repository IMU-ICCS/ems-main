/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.Component;

import java.util.Collection;

public class CostUtilityFunctionFraction extends CostUtilityFunction {

    @Override
    public double evaluateCostUtilityFunction(Collection<Component> actualConfiguration,
            Collection<Component> newConfiguration) {

        double cost = calculateCost(newConfiguration);
        return 1 / cost; //todo: to change according to Geir's paper
    }
}
