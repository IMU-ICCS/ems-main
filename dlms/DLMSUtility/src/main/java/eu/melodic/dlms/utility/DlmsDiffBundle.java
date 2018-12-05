package eu.melodic.dlms.utility;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bundles a list of DlmsConfigurationDiff objects.
 */
@NoArgsConstructor
@Getter
@Setter
public class DlmsDiffBundle {

	private List<DlmsConfigurationDiff> configurationDiffs = new ArrayList<>();


	/**
	 * Add the given DlmsConfigurationDiff to the bundle.
	 */
	public void addConfigurationDiff(DlmsConfigurationDiff configurationDiff) {
		configurationDiffs.add(configurationDiff);
	}

	/**
	 * Returns true if the bundle is empty.
	 */
	public boolean isEmpty() {
		return configurationDiffs.isEmpty();
	}
}
