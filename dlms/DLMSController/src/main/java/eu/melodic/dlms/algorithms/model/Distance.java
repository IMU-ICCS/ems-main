package eu.melodic.dlms.algorithms.model;

public class Distance {

	private double latency;
	private double bandwidth;

	public Distance(double latency, double bandwidth) {
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

	public double getLatency() {
		return latency;
	}

	public void setLatency(double latency) {
		this.latency = latency;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

}
