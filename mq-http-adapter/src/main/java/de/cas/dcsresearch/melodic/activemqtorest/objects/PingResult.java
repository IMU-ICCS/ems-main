package de.cas.dcsresearch.melodic.activemqtorest.objects;

public class PingResult {

	private String status;

	public static PingResult create() {
		PingResult pingResult = new PingResult();
		pingResult.setStatus("PONG");
		return pingResult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PingResult{" +
				"status='" + status + '\'' +
				'}';
	}

}
