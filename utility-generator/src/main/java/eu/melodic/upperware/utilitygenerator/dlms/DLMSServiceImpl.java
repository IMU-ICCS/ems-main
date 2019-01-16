/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.upperware.utilitygenerator.dlms;

import eu.melodic.dlms.utility.DlmsConfigurationElement;
import eu.melodic.dlms.utility.DlmsControllerClient;
import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;

import java.util.Collection;
import java.util.stream.Collectors;

public class DLMSServiceImpl implements DLMSService {

    private DlmsControllerClient dlmsClient;

    public DLMSServiceImpl(String dlmsControllerUrl) {
        this.dlmsClient = new DlmsControllerClient(dlmsControllerUrl);
    }

    @Override
    public UtilityMetrics getDLMSUtility(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {
        return dlmsClient.getUtilityValues(convertToDlmsConfigurationElement(actConfiguration), convertToDlmsConfigurationElement(newConfiguration));
    }

    private Collection<DlmsConfigurationElement> convertToDlmsConfigurationElement(Collection<ConfigurationElement> configuration) {
        return configuration.stream()
                .map(element -> new DlmsConfigurationElement(element.getId(), element.getNodeCandidate(), element.getCardinality()))
                .collect(Collectors.toList());
    }
}
