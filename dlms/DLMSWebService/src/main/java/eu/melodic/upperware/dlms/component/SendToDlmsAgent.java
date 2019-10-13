package eu.melodic.upperware.dlms.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendToDlmsAgent {

	private String componentId;
	private String metricName;
	private AlluxioParam alluxio;
	private MysqlParam mysql;

	public SendToDlmsAgent(String componentId, String metricName, AlluxioParam alluxio) {
		this.componentId = componentId;
		this.metricName = metricName;
		this.alluxio = alluxio;

	}

	public SendToDlmsAgent(String componentId, String metricName, MysqlParam mysql) {
		this.componentId = componentId;
		this.metricName = metricName;
		this.mysql = mysql;

	}


}
