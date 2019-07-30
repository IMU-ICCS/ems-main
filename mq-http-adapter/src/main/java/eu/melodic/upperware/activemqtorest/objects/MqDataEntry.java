package eu.melodic.upperware.activemqtorest.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MqDataEntry {

	private long id = 42l;
	private String topic;
	private String level;
	private String value;
	private String timestamp;
	private String producer;

}
