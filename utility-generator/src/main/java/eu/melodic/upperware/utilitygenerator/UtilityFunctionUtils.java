/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;

import java.util.Collection;

public class UtilityFunctionUtils { //todo: to move elsewhere

    public static double normalize(double min, double max, double x) {
        return (x - min) / (max - min);
    }

    public static int countVirtualMachines(Collection<ConfigurationElement> configuration) {

        return configuration.stream().mapToInt(ConfigurationElement::getCardinality).sum();

    }

    public static int countNumberOfCores(Collection<ConfigurationElement> configuration) {
        return configuration.stream().mapToInt(ConfigurationElement::getTotalNumberOfCores).sum();
    }

}
