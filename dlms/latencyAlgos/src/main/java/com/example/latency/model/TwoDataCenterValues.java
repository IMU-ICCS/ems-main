package com.example.latency.model;

public class TwoDataCenterValues {

	private final String dc1;
	private final String dc2;
	private final double latency;
	private final double bandwidth;

	public TwoDataCenterValues(String dc1, String dc2, double latency, double bandwidth) {
		this.dc1 = dc1;
		this.dc2 = dc2;
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

	public String getDc1() {
		return dc1;
	}

	public String getDc2() {
		return dc2;
	}

	public double getLatency() {
		return latency;
	}

	public double getBandwidth() {
		return bandwidth;
	}

}
