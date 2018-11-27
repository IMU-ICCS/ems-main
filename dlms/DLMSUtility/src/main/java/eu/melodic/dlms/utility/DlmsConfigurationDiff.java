package eu.melodic.dlms.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing two DlmsConfigurationElements that are different from another.
 */

@NoArgsConstructor //Constructor needed for (de)serialization.
@AllArgsConstructor //Convenience constructor taking the deployed and proposed DlmsConfigurationElements.
@Getter
@Setter
public class DlmsConfigurationDiff {

	private DlmsConfigurationElement deployedConfiguration;
	private DlmsConfigurationElement proposedConfiguration;

}
