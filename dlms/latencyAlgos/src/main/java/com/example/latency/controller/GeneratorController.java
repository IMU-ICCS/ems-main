package com.example.latency.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Async;

import com.example.latency.Runner.Generator;
import com.example.latency.extras.GetPropertyValues;
import com.example.latency.repository.DataCenterLatencyBandwidthRepository;
import com.example.latency.repository.DataCenterRepository;

//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class GeneratorController {
	@Autowired
	Generator generator;
	@Autowired
	DataCenterRepository dataCenterRepository;
	@Autowired
	DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;

	private GetPropertyValues propValues = new GetPropertyValues(); // store config

	@Async
	public void run() throws InterruptedException {
		long currentTime = System.currentTimeMillis();

		// delete all records
		/*
		 * Date date2 = new Date(currentTime); System.out.println(date2);
		 * deleteData(date2);// delete the old data
		 */
		// generate data
		double numberOfDataGenerated = 
		
		while (true) {
			Thread.sleep(100);	// number of records generated per second
			generator.generateData(propValues);

			long newTime = System.currentTimeMillis();
			if ((newTime - currentTime) >= propValues.getTimePurge() * 1000) {

				Date date = new Date(currentTime);
				deleteData(date);// delete the old data
				currentTime = newTime;
			}
		}

	}

	public void deleteData(Date date) {
		dataCenterLatencyBandwidthRepository.deleteBytimestampBefore(date);
	}

	public GetPropertyValues getPropValues() {
		return propValues;
	}

	public void setPropValues(GetPropertyValues propValues) {
		this.propValues = propValues;
	}

}
