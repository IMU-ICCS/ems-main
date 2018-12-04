package eu.melodic.dlms.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Distance {

	private double latency;
	private double bandwidth;
	private double latBWVal; // combination of latency and bandwidth

	public Distance(double latency, double bandwidth) {
		this.latency = latency;
		this.bandwidth = bandwidth;
	}

}
