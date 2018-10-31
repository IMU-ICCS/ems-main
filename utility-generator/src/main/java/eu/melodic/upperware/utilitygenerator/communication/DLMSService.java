package eu.melodic.upperware.utilitygenerator.communication;

import eu.melodic.dlms.utility.DlmsConfigurationElement;
import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class DLMSService {


    abstract public UtilityMetrics getDLMSUtility(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration);

    static Collection<DlmsConfigurationElement> convertToDlmsConfigurationElement(Collection<ConfigurationElement> configuration){
        return configuration.stream()
                .map(element -> new DlmsConfigurationElement(element.getId(), element.getNodeCandidate(), element.getCardinality()))
                .collect(Collectors.toList());
    }

}
