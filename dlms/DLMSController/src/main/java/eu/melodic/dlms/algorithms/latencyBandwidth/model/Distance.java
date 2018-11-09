package eu.melodic.dlms.algorithms.latencyBandwidth.model;

public class Distance {

	private double latency;
	private double bandwidth;
	private double latBWVal; // combination of latency and bandwidth

	public Distance(double latency, double bandwidth) {
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

	public Distance(double latency, double bandwidth, double latBWVal) {
		this.latency = latency;
		this.bandwidth = bandwidth;
		this.latBWVal = latBWVal;
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

	public double getLatBWVal() {
		return latBWVal;
	}

	public void setLatBWVal(double latBndVal) {
		this.latBWVal = latBndVal;
	}

}
