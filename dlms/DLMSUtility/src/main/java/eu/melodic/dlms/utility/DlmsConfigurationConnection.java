package eu.melodic.dlms.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import camel.deployment.SoftwareComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DlmsConfigurationConnection {

	private Collection<DlmsConfigurationElement> proposedConfiguration;
	private Map<SoftwareComponent, List<SoftwareComponent>> compConMap;
	
}
