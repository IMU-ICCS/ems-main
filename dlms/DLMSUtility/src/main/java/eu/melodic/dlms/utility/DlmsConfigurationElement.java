package eu.melodic.dlms.utility;

import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Just a placeholder class as the original from upperware is not accessible
 * from the DlmsController project.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DlmsConfigurationElement {
	// software component name from camel
	private String id; 
	private NodeCandidate nodeCandidate;
	private int cardinality;

}
