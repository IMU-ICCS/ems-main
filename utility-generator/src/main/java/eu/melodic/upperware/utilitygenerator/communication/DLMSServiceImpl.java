package eu.melodic.upperware.utilitygenerator.communication;

import eu.melodic.dlms.utility.DlmsControllerClient;
import eu.melodic.dlms.utility.UtilityMetrics;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;

import java.util.Collection;

public class DLMSServiceImpl extends DLMSService{

    private DlmsControllerClient dlmsClient;

    public DLMSServiceImpl(String dlmsControllerUrl){
        this.dlmsClient = new DlmsControllerClient(dlmsControllerUrl);
    }

    @Override
    public UtilityMetrics getDLMSUtility(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {

        return dlmsClient.getUtilityValues(convertToDlmsConfigurationElement(actConfiguration), convertToDlmsConfigurationElement(newConfiguration));
    }
}
