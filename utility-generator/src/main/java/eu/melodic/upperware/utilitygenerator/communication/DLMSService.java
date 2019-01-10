/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.upperware.utilitygenerator.communication;

import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;

import java.util.Collection;

public interface DLMSService {

    UtilityMetrics getDLMSUtility(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration);

}
