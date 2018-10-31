package eu.melodic.upperware.utilitygenerator.communication;

import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class DLMSServiceMock extends DLMSService{


    @Override
    public UtilityMetrics getDLMSUtility(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {

        Map<String, Double> mockResult = new HashMap<>();

        mockResult.put(CamelMetadata.AFFINITY_AWARENESS.camelName, 2.0);
        mockResult.put(CamelMetadata.DATA_CENTRE_AWARENESS.camelName, 2.1);
        mockResult.put(CamelMetadata.DLMS_TOTAL_UTILITY.camelName, 4.0);
        mockResult.put(CamelMetadata.SOURCE_AWARENESS.camelName, 0.9);

        return new UtilityMetrics(mockResult);
    }
}
