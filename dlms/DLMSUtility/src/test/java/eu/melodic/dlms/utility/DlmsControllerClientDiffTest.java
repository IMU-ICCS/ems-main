package eu.melodic.dlms.utility;

import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DlmsControllerClientDiffTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(DlmsControllerClientDiffTest.class);
	}

	@Test
	public void testHasHardwareDiff_DeployedHardwareNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(null);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);

		Assert.assertFalse(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void testHasHardwareDiff_ProposedHardwareNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(null);

		Assert.assertTrue(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void testHasHardwareDiff_HardwareDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		Hardware hardwareProposed = new Hardware();
		hardwareProposed.setId("another");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(hardwareProposed);

		Assert.assertTrue(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void testHasHardwareDiff_NoHardwareDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Hardware hardwareDeployed = new Hardware();
		hardwareDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getHardware()).thenReturn(hardwareDeployed);

		Hardware hardwareProposed = new Hardware();
		hardwareProposed.setId("same");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getHardware()).thenReturn(hardwareProposed);

		Assert.assertFalse(client.hasHardwareDiff(deployed, proposed));
	}

	@Test
	public void testHasLocationDiff_DeployedLocationNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(null);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);

		Assert.assertFalse(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void testHasHardwareDiff_ProposedLocationNull() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(null);

		Assert.assertTrue(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void testHasLocationDiff_LocationDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		Location locationProposed = new Location();
		locationProposed.setId("another");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(locationProposed);

		Assert.assertTrue(client.hasLocationDiff(deployed, proposed));
	}

	@Test
	public void testHasLocationDiff_NoLocationDiff() {
		DlmsControllerClient client = new DlmsControllerClient();

		Location locationDeployed = new Location();
		locationDeployed.setId("same");

		NodeCandidate deployed = Mockito.mock(NodeCandidate.class);
		Mockito.when(deployed.getLocation()).thenReturn(locationDeployed);

		Location locationProposed = new Location();
		locationProposed.setId("same");

		NodeCandidate proposed = Mockito.mock(NodeCandidate.class);
		Mockito.when(proposed.getLocation()).thenReturn(locationProposed);

		Assert.assertFalse(client.hasLocationDiff(deployed, proposed));
	}

}
