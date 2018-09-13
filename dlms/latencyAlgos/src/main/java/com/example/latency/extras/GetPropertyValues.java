package com.example.latency.extras;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

// Class to read the properties from the properties file

public class GetPropertyValues {
	private int recordsConsider = 0;
	private int bestLatency = 0;
	private int worstLatency = 0;
	private int bestBandwidth = 0;
	private int worstBandwidth = 0;
	private int timeInterval = 0; // time interval in seconds to update
	private String function = ""; // algorithm to calculate for the latest
	private int timePurge = 0; //time to purge old records in seconds
	private int numRecord = 0; //number of records to generate each second
	

//	read the config files and populate the elements
	public void readValues() {
		try {
			Properties properties = new Properties();
			String propertiesFile = "config.properties";

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertiesFile + "' not found");
			}

			// get the property value
			recordsConsider = Integer.parseInt(properties.getProperty("recordsConsider"));
			bestLatency = Integer.parseInt(properties.getProperty("bestLatency"));
			worstLatency = Integer.parseInt(properties.getProperty("worstLatency"));
			bestBandwidth = Integer.parseInt(properties.getProperty("bestBandwidth"));
			worstBandwidth = Integer.parseInt(properties.getProperty("worstBandwidth"));
			timeInterval = Integer.parseInt(properties.getProperty("timeInterval"));
			function = properties.getProperty("function");
			timePurge = Integer.parseInt(properties.getProperty("timePurge"));

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public int getRecordsConsider() {
		return recordsConsider;
	}

	public void setRecordsConsider(int recordsConsider) {
		this.recordsConsider = recordsConsider;
	}

	public int getBestLatency() {
		return bestLatency;
	}

	public void setBestLatency(int bestLatency) {
		this.bestLatency = bestLatency;
	}

	public int getWorstLatency() {
		return worstLatency;
	}

	public void setWorstLatency(int worstLatency) {
		this.worstLatency = worstLatency;
	}

	public int getBestBandwidth() {
		return bestBandwidth;
	}

	public void setBestBandwidth(int bestBandwidth) {
		this.bestBandwidth = bestBandwidth;
	}

	public int getWorstBandwidth() {
		return worstBandwidth;
	}

	public void setWorstBandwidth(int worstBandwidth) {
		this.worstBandwidth = worstBandwidth;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getTimePurge() {
		return timePurge;
	}

	public void setTimePurge(int timePurge) {
		this.timePurge = timePurge;
	}

}
