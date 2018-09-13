package com.example.latency.model;

public class TwoDataCenterValues {

	private final String dc1;
	private final String dc2;
	private final int latency;
	private final int bandwidth;

	public TwoDataCenterValues(String dc1, String dc2, int latency, int bandwidth) {
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

	public int getLatency() {
		return latency;
	}

	public int getBandwidth() {
		return bandwidth;
	}

}
