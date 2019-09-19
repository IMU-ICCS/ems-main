package eu.melodic.upperware.dlms.component;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {

	private String name;
	private List<IpAddress> ipAddresses;
	private String reason;
	private String diagnostic;
	private String nodeCandidate;
	private String id;
	private String originId;
	private String userId;
	private NodeType nodeType;

}