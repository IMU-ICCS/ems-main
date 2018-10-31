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
import eu.melodic.upperware.utilitygenerator.communication.DLMSServiceMock;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.function.DLMSUtilityAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.function.ElementFactory.createElement;

public class DLMSConverter {


    private DLMSService dlmsUtilityService;
    private Collection<DLMSUtilityAttribute> dlmsUtilityAttributes;


    public DLMSConverter(String dlmsControllerUrl, Collection<DLMSUtilityAttribute> dlmsUtilityAttributes){
        if (dlmsControllerUrl.isEmpty()){
            this.dlmsUtilityService = new DLMSServiceMock();
        }
        else {
            this.dlmsUtilityService = new DLMSServiceImpl(dlmsControllerUrl);
        }
        this.dlmsUtilityAttributes = dlmsUtilityAttributes;
    }

    public Collection<Element> convertDLMSUtilityAttributes(Collection<ConfigurationElement> actConfiguration,
            Collection<ConfigurationElement> newConfiguration) {

        if (dlmsUtilityAttributes.isEmpty()){ //way to not call dlms library if not used
            return Collections.emptyList();
        }
        UtilityMetrics dlmsUtility = dlmsUtilityService.getDLMSUtility(actConfiguration, newConfiguration);

        return dlmsUtilityAttributes.stream()
                .map(attribute -> createElement(attribute.getName(),getDLMSUtilityAttributeValue(dlmsUtility, attribute.getType())))
                .collect(Collectors.toList());

    }

    private static Number getDLMSUtilityAttributeValue(UtilityMetrics dlmsUtility, CamelMetadata type) {
        switch (type) {
            case AFFINITY_AWARENESS:
                return dlmsUtility.getResult(CamelMetadata.AFFINITY_AWARENESS.camelName);
            case DATA_CENTRE_AWARENESS:
                return dlmsUtility.getResult(CamelMetadata.DATA_CENTRE_AWARENESS.camelName);
            case SOURCE_AWARENESS:
                return dlmsUtility.getResult(CamelMetadata.SOURCE_AWARENESS.camelName);
            case DLMS_TOTAL_UTILITY:
                return dlmsUtility.getResult(CamelMetadata.DLMS_TOTAL_UTILITY.camelName);
            default:
                throw new IllegalArgumentException("Illegal type of DLMS utility attribute: " + type);
        }
    }


}
