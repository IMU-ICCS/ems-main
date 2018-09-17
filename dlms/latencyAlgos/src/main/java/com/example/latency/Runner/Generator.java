package com.example.latency.Runner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.latency.extras.GetPropertyValues;
import com.example.latency.model.DataCenter;
import com.example.latency.model.DataCenterLatencyBandwidth;
import com.example.latency.repository.DataCenterLatencyBandwidthRepository;
import com.example.latency.repository.DataCenterRepository;

// class to generate data in the database
//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class Generator {

	@Autowired
	DataCenterRepository dataCenterRepository;
	@Autowired
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;

	private int numDataCenter = 20;

	public void generateData(GetPropertyValues propValues) {
		DataCenter dataCenter1 = new DataCenter();
		DataCenter dataCenter2 = new DataCenter();
		List<Integer> randomIds = generateTwoRand(1, numDataCenter);
		// first data center
		Optional<DataCenter> dc1 = dataCenterRepository.findById((long) randomIds.get(0));
		if (dc1.isPresent())
			dataCenter1 = dc1.get();

		// second data center
		Optional<DataCenter> dc2 = dataCenterRepository.findById((long) randomIds.get(1));
		if (dc2.isPresent())
			dataCenter2 = dc2.get();

		// to sort the dataCenter names
		String[] nameList = { dataCenter1.getName(), dataCenter2.getName() };
		Arrays.sort(nameList, String.CASE_INSENSITIVE_ORDER);

		int latency = generateRandomNum(propValues.getBestLatency(), propValues.getWorstLatency());
		int bandwidth = generateRandomNum(propValues.getBestLatency(), propValues.getWorstLatency());

		Date time = new Date(System.currentTimeMillis());

		DataCenterLatencyBandwidth data = new DataCenterLatencyBandwidth(nameList[0], nameList[1], time, latency,
				bandwidth);
		dataCenterLatencyBandwidthRepository.save(data);
	}

	// return two random numbers
	public List<Integer> generateTwoRand(int min, int max) {
		List<Integer> numList = new ArrayList<Integer>();

		while (numList.size() < 2) {
			int randomNum = generateRandomNum(min, max);
			if (!numList.contains(randomNum))
				numList.add(randomNum);
		}

		return numList;
	}

	// generate random num
	public int generateRandomNum(int min, int max) {
		int retNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return retNum;
	}

}
