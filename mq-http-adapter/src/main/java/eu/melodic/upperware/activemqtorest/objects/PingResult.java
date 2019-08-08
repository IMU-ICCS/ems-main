package eu.melodic.upperware.activemqtorest.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PingResult {

	private String status;

	public static PingResult create() {
		PingResult pingResult = new PingResult();
		pingResult.setStatus("PONG");
		return pingResult;
	}

}
