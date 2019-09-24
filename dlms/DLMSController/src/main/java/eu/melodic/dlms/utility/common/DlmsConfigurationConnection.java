package eu.melodic.dlms.utility.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DlmsConfigurationConnection {

	private Collection<DlmsConfigurationElement> proposedConfiguration;
	private Map<String, List<String>> compConMap;

}
