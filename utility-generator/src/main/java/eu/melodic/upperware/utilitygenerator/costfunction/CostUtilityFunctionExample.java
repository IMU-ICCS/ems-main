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
public class CostUtilityFunctionExample extends CostUtilityFunction { //todo: to rename


    private double actUtilityCost;
    private boolean isReconfig;


    public CostUtilityFunctionExample(boolean isReconfig) {
        this.isReconfig = isReconfig;
        this.actUtilityCost = 1;
    }

    @Override
    public double evaluateCostUtilityFunction(Collection<Component> actualConfiguration,
            Collection<Component> newConfiguration) {

        double oldCost = 1.0; //FIXME - how to set oldCost?
        if (isReconfig) {
            oldCost = calculateCost(actualConfiguration);
        }

        double newCost = calculateCost(newConfiguration);
        double result = Math.min(1, actUtilityCost * oldCost / newCost);

        log.info("evaluateCostUtilityFunction: result = {}", result);
        return result;
    }
}
