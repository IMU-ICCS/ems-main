/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.costfunction;

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class CostUtilityFunctionFraction extends CostUtilityFunction {

    @Override
    public double evaluateCostUtilityFunction(Collection<ConfigurationElement> actualConfiguration,
            Collection<ConfigurationElement> newConfiguration) {

        double cost = calculateCost(newConfiguration);
        double result = 1/cost;  //todo: to change according to Geir's paper

        log.debug("evaluateCostUtilityFunction: result = {}", result);
        return result;
    }
}
