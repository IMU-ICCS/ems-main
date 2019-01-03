package eu.melodic.dlms.utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;

public class DlmsControllerClientDiffTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(DlmsControllerClientDiffTest.class);
	}

	@Test
	public void hasSameId_Different() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(deployed.getId()).thenReturn("one");

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(proposed.getId()).thenReturn("another");

		Assert.assertFalse(client.hasSameId(deployed, proposed));
	}

	@Test
	public void hasSameId_Equal() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(deployed.getId()).thenReturn("the same");

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(proposed.getId()).thenReturn("the same");

		Assert.assertTrue(client.hasSameId(deployed, proposed));
	}

	@Test
	public void hasCardinalityDiff_Equal() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(deployed.getCardinality()).thenReturn(1);

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(proposed.getCardinality()).thenReturn(1);

		Assert.assertFalse(client.hasCardinalityDiff(deployed, proposed));
	}

	@Test
	public void hasCardinalityDiff_Different() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(deployed.getCardinality()).thenReturn(1);

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(proposed.getCardinality()).thenReturn(2);

		Assert.assertTrue(client.hasCardinalityDiff(deployed, proposed));
	}

	@Test
	public void hasValidNodeCandidates_BothValid() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		NodeCandidate deployedNodeCandidate = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getNodeCandidate()).thenReturn(deployedNodeCandidate);

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		NodeCandidate proposedNodeCandidate = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getNodeCandidate()).thenReturn(proposedNodeCandidate);

		Assert.assertTrue(client.hasValidNodeCandidates(deployed, proposed));
	}

	@Test
	public void hasValidNodeCandidates_DeployedInvalid() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(deployed.getNodeCandidate()).thenReturn(null);

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		NodeCandidate proposedNodeCandidate = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getNodeCandidate()).thenReturn(proposedNodeCandidate);

		Assert.assertFalse(client.hasValidNodeCandidates(deployed, proposed));
	}

	@Test
	public void hasValidNodeCandidates_ProposedInvalid() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		NodeCandidate deployedNodeCandidate = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getNodeCandidate()).thenReturn(deployedNodeCandidate);

		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);
		Mockito.when(proposed.getNodeCandidate()).thenReturn(null);

		Assert.assertFalse(client.hasValidNodeCandidates(deployed, proposed));
	}

	@Test
	public void hasLocationDiff_DeployedLocationNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(null);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);

		Assert.assertFalse(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void hasLocationDiff_ProposedLocationNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("one");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(null);

		Assert.assertTrue(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void hasLocationDiff_LocationDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("one");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		Location locationProposed = new Location();
		locationProposed.setId("another");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(locationProposed);

		Assert.assertTrue(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void hasLocationDiff_NoLocationDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("the same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		Location locationProposed = new Location();
		locationProposed.setId("the same");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(locationProposed);

		Assert.assertFalse(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void hasHardwareDiff_DeployedHardwareNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(null);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);

		Assert.assertFalse(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void hasHardwareDiff_ProposedHardwareNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("one");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(null);

		Assert.assertTrue(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void hasHardwareDiff_HardwareDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("one");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		Hardware hardwareProposed = new Hardware();
		hardwareProposed.setId("another");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(hardwareProposed);

		Assert.assertTrue(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void hasHardwareDiff_NoHardwareDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("the same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		Hardware hardwareProposed = new Hardware();
		hardwareProposed.setId("the same");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(hardwareProposed);

		Assert.assertFalse(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void testRegisterDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		DlmsDiffBundle diffBundle = new DlmsDiffBundle();
		Assert.assertTrue(diffBundle.isEmpty());

		DlmsConfigurationElement deployed = Mockito.mock(DlmsConfigurationElement.class);
		DlmsConfigurationElement proposed = Mockito.mock(DlmsConfigurationElement.class);

		client.registerDiff(diffBundle, deployed, proposed);

		Assert.assertFalse(diffBundle.isEmpty());
	}

}
