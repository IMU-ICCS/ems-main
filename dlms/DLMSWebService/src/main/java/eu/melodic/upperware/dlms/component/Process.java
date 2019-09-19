package eu.melodic.upperware.dlms.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process {

	private String id;
	private String originId;
	private String processType;
	private State state;
	private Type type;
	private String schedule;
	private String task;
	private String taskInterface;
	private String diagnostic;
	private String reason;
	private String owner;
	private IpAddress ipAddresses;
	private String endpoint;

}
