/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.communication.DLMSService;
import eu.melodic.upperware.utilitygenerator.communication.DLMSServiceImpl;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.function.DLMSUtilityAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.function.ElementFactory.createElement;

@AllArgsConstructor
@Slf4j
public class DLMSConverter extends ArgumentConverter {

    private DLMSService dlmsUtilityService;
    private Collection<DLMSUtilityAttribute> dlmsUtilityAttributes;
    private Collection<ConfigurationElement> actConfiguration;


    public DLMSConverter(String dlmsControllerUrl, Collection<DLMSUtilityAttribute> dlmsUtilityAttributes, Collection<ConfigurationElement> actConfiguration) {
        this.dlmsUtilityService = new DLMSServiceImpl(dlmsControllerUrl);
        this.dlmsUtilityAttributes = dlmsUtilityAttributes;
        this.actConfiguration = actConfiguration;
    }


    @Override
    public Collection<Element> convertToElements(Collection<Element> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertDLMSUtilityAttributes(newConfiguration);
    }

    private Collection<Element> convertDLMSUtilityAttributes(Collection<ConfigurationElement> newConfiguration) {
        if (dlmsUtilityAttributes.isEmpty()) { //way to not call dlms library if not needed
            return Collections.emptyList();
        }
        UtilityMetrics dlmsUtility;
        try {
            dlmsUtility = dlmsUtilityService.getDLMSUtility(actConfiguration, newConfiguration);
        } catch (Exception e) {
            log.warn("There was an error during invoking the DLMS Utility library, returning 0 as DLMS utility value");
            return createDefaultValuesOfDLMSUtilityAttributes();
        }

        return dlmsUtilityAttributes.stream()
                .map(attribute -> createElement(attribute.getName(), getDLMSUtilityAttributeValue(dlmsUtility, attribute.getType())))
                .collect(Collectors.toList());


    }

    private Collection<Element> createDefaultValuesOfDLMSUtilityAttributes() {
        return dlmsUtilityAttributes.stream().map(attribute -> createElement(attribute.getName(), 0)).collect(Collectors.toList());
    }

    private static Number getDLMSUtilityAttributeValue(UtilityMetrics dlmsUtility, CamelMetadata type) {
        if (!CamelMetadata.DLMS_LIST.contains(type)) {
            throw new IllegalArgumentException("Illegal type of DLMS utility attribute: " + type);
        }

        if (dlmsUtility == null || dlmsUtility.getResults() == null) {
            log.warn("DLMSUtility is null, returning 0 as DLMS utility value");
            return 0;
        }

        Double dlmsUtilityResult = dlmsUtility.getResults().get(type.camelName);
        if (dlmsUtilityResult == null) {
            log.warn("DLMS utility result for type: "+ type.camelName + " is null, returning 0 as a DLMS utility value");
            return 0;
        }

        return dlmsUtilityResult;
    }
}
