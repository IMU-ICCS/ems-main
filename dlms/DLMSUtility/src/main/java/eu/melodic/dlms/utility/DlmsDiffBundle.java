package eu.melodic.dlms.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Bundles a list of DlmsConfigurationDiff objects.
 */
public class DlmsDiffBundle {

	private List<DlmsConfigurationDiff> configurationDiffs;

	/**
	 * Default constructor.
	 */
	public DlmsDiffBundle() {
		configurationDiffs  = new ArrayList<>();
	}

	public List<DlmsConfigurationDiff> getConfigurationDiffs() {
		return configurationDiffs;
	}

	/**
	 * Add the given DlmsConfigurationDiff to the bundle.
	 */
	public void addConfigurationDiff(DlmsConfigurationDiff configurationDiff) {
		configurationDiffs.add(configurationDiff);
	}

	public void setConfigurationDiffs(List<DlmsConfigurationDiff> configurationDiffs) {
		this.configurationDiffs = configurationDiffs;
	}

	/**
	 * Returns true if the bundle is empty.
	 */
	public boolean isEmpty() {
		return configurationDiffs.isEmpty();
	}
}
