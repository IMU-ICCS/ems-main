package eu.melodic.dlms.utility;

/**
 * Class representing two DlmsConfigurationElements that are different from another.
 */
public class DlmsConfigurationDiff {

	private DlmsConfigurationElement deployedConfiguration;
	private DlmsConfigurationElement proposedConfiguration;

	/**
	 * Constructor needed for (de)serialization.
	 */
	private DlmsConfigurationDiff() {
	}

	/**
	 * Convenience constructor taking the deployed and proposed DlmsConfigurationElements.
	 */
	public DlmsConfigurationDiff(DlmsConfigurationElement deployedConfiguration, DlmsConfigurationElement proposedConfiguration) {
		this.deployedConfiguration = deployedConfiguration;
		this.proposedConfiguration = proposedConfiguration;
	}

	public DlmsConfigurationElement getDeployedConfiguration() {
		return deployedConfiguration;
	}

	public void setDeployedConfiguration(DlmsConfigurationElement deployedConfiguration) {
		this.deployedConfiguration = deployedConfiguration;
	}

	public DlmsConfigurationElement getProposedConfiguration() {
		return proposedConfiguration;
	}

	public void setProposedConfiguration(DlmsConfigurationElement proposedConfiguration) {
		this.proposedConfiguration = proposedConfiguration;
	}
}
