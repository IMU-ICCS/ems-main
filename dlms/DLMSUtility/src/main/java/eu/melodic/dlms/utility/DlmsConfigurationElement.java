package eu.melodic.dlms.utility;

import io.github.cloudiator.rest.model.NodeCandidate;

/**
 * Just a placeholder class as the original from upperware is not accessible from the DlmsController project.
 */
public class DlmsConfigurationElement {

	private String id;
	private NodeCandidate nodeCandidate;
	private int cardinality;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NodeCandidate getNodeCandidate() {
		return nodeCandidate;
	}

	public void setNodeCandidate(NodeCandidate nodeCandidate) {
		this.nodeCandidate = nodeCandidate;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}
}
