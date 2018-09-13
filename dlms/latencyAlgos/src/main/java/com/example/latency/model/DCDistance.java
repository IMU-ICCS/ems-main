package com.example.latency.model;

public class DCDistance {

	private String name;
	private int latency;
	private int bandwidth;

	public DCDistance(String name, int latency, int bandwidth) {
		this.name = name;
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

	public DCDistance() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

}
