package com.example.latency.model;

public class DCDistance {

	private String name;
	private double latency;
	private double bandwidth;

	public DCDistance(String name, double latency, double bandwidth) {
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
