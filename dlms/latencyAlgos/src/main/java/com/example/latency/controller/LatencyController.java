package com.example.latency.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Async;

import com.example.latency.extras.GetPropertyValues;
import com.example.latency.model.DCDistance;
import com.example.latency.model.DataCenter;
import com.example.latency.model.DataCenterLatencyBandwidth;
import com.example.latency.model.TwoDataCenterValues;
import com.example.latency.repository.CloudProviderRepository;
import com.example.latency.repository.DataCenterLatencyBandwidthRepository;
import com.example.latency.repository.DataCenterRepository;

//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class LatencyController {

	private GetPropertyValues propValues = new GetPropertyValues(); // store config
	private int max = 30; // number of datacenters for random initialization;
	private int numberOfDaysConsider = 30; // record from the history to consider

	@Autowired
	private CloudProviderRepository cloudProviderRepository;
	@Autowired
	private DataCenterRepository dataCenterRepository;
	@Autowired
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;
	@Autowired
	private LocationController locationController = new LocationController();

	List<String> dcPairList = new ArrayList<>();// list of paired dataset to get latency and ping for user defined ones
	List<String> dcPairListWithData = new ArrayList<>();// list of paired dataset with historical data to get latency
														// and ping for user defined ones
	List<List<DCDistance>> dcDistanceList = new ArrayList<List<DCDistance>>();
	Map<String, List<DCDistance>> dcDistanceMap = new HashMap<String, List<DCDistance>>();

	private static final Logger logger = LoggerFactory.getLogger(LatencyController.class);

	@Async
	public void run() {
		storeInternally(); // first time store internally so it can be called
		long currentTime = System.currentTimeMillis();
		// this to initialize data manually
//		int subtractNum = 180000;
//		for (int i = 1; i < 5000; i++)
//			insertData(subtractNum * i);
		storeInternally(); // use algorithm to find latency and bandwidth based on historical data or
							// location
		printDataStructure(); // write the stored data structure to a file
		while (true) {
			long newTime = System.currentTimeMillis();
			if ((newTime - currentTime) >= propValues.getTimeInterval() * 1000) {
				storeInternally();
				printDataStructure(); // write the stored data structure in the logger file
				currentTime = newTime;
			}

		}
//		if need to ask in the console
//		askUser(); // ask user the dataset they want to find the latency and bandwidth
	}

	public void insertData() {
		int subtractNum = 18000;
		for (int i = 1; i < 5000; i++)
			insertData(subtractNum * i);
	}

	public void storeInternally() {
		List<DataCenter> dataCenterList = dataCenterRepository.findAll();
		for (int i = 0; i < dataCenterList.size(); i++) {
			DataCenter dc1 = dataCenterList.get(i);
			for (int j = i + 1; j < dataCenterList.size(); j++) {
				DataCenter dc2 = dataCenterList.get(j);

				String[] nameList = { dc1.getName(), dc2.getName() };
				Arrays.sort(nameList, String.CASE_INSENSITIVE_ORDER);

				dcPairList.add(nameList[0] + "," + nameList[1]);
				int a;
				if (nameList[0].contains("South-Central"))
					a = 3;

				// for different functions
				switch (propValues.getFunction()) {
				case "average":
					computeAverage(nameList[0], nameList[1]);
					break;
				case "latest_higher":
					computeLatestHigher(nameList[0], nameList[1]);
					break;
				default:
					System.out.println("Sorry the function has not been implemented yet!");
				}

			}
		}

	}

	// algo : average
	public void computeAverage(String dc1, String dc2) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusSeconds(this.propValues.getTimeInterval());
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository.findByLatestRecords(dc1,
				dc2, date);

		if (dataCenterList.size() == 0) { // if no record exists
//			calculateUsingGPS(dc1, dc2);
		} else {
			algoEqualWeight(dataCenterList, dc1, dc2);
			dcPairListWithData.add(dc1 + "," + dc2);
		}

	}

	// algo : latest higher
	public void computeLatestHigher(String dc1, String dc2) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusSeconds(this.propValues.getTimeInterval());
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository
				.findByLatestRecordsOrdered(dc1, dc2, date);

		if (dataCenterList.size() == 0) { // if no record exists
//			calculateUsingGPS(dc1, dc2, latency, bandwidth);
		} else {
			algoLatestHigherWeight(dataCenterList, dc1, dc2);
			// now add to pairlist
			dcPairListWithData.add(dc1 + "," + dc2);
		}

	}

	public void algoEqualWeight(List<DataCenterLatencyBandwidth> dataCenterList, String dc1, String dc2) {
		double latency = 0, bandwidth = 0;
		for (DataCenterLatencyBandwidth dcLatencyBandwidthItem : dataCenterList) {
			latency += dcLatencyBandwidthItem.getLatency();
			bandwidth += dcLatencyBandwidthItem.getBandwidth();
		}
		latency = latency / dataCenterList.size();
		bandwidth = bandwidth / dataCenterList.size();

		DCDistance dcDistanceNew = new DCDistance(dc2, latency, bandwidth);
		List<DCDistance> dcDistanceList = new ArrayList<DCDistance>();

		if (dcDistanceMap.containsKey(dc1)) // if dc1 already exists
			dcDistanceList = dcDistanceMap.get(dc1);

		dcDistanceList.add(dcDistanceNew);
		dcDistanceMap.put(dc1, dcDistanceList);
	}

	public void algoLatestHigherWeight(List<DataCenterLatencyBandwidth> dataCenterList, String dc1, String dc2) {
		double latency = 0, bandwidth = 0;
		int numberRecords = dataCenterList.size();

		double multiply = 0;
		int numCounter = 1; // to multiply with
		double denom = 0;// to divide
		for (DataCenterLatencyBandwidth dcLatencyBandwidthItem : dataCenterList) {
			multiply = (numberRecords - numCounter + 1.0) / (double) numberRecords;
			latency += dcLatencyBandwidthItem.getLatency() * multiply;
			bandwidth += dcLatencyBandwidthItem.getBandwidth() * multiply;

			denom += multiply;
			numCounter++;
		}
		latency = latency / denom;
		bandwidth = bandwidth / denom;

		DCDistance dcDistanceNew = new DCDistance(dc2, latency, bandwidth);
		List<DCDistance> dcDistanceList = new ArrayList<DCDistance>();

		if (dcDistanceMap.containsKey(dc1)) // if dc1 already exists
			dcDistanceList = dcDistanceMap.get(dc1);

		dcDistanceList.add(dcDistanceNew);
		dcDistanceMap.put(dc1, dcDistanceList);
	}

	public void calculateUsingGPS(String dc1, String dc2) {
//		/*
		double distance = 0;
		try {
			distance = locationController.findLocation(dc1, dc2);
		} catch (Exception e) { // TODO Auto-generated
			e.printStackTrace();
		}

		Integer[] latArray = findlatlongWithDist(distance);
		double latency = latArray[0];
		double bandwidth = latArray[1];

		DCDistance dcDistanceNew = new DCDistance(dc2, latency, bandwidth);
		List<DCDistance> dcDistanceList = new ArrayList<DCDistance>();

		if (dcDistanceMap.containsKey(dc1)) // if dc1 already exists
			dcDistanceList = dcDistanceMap.get(dc1);

		dcDistanceList.add(dcDistanceNew);
		dcDistanceMap.put(dc1, dcDistanceList);
//		 * 
	}

	// initialize data
	public void insertData(int subtractNum) {
		DataCenter dataCenter1 = new DataCenter();
		DataCenter dataCenter2 = new DataCenter();
		List<Integer> randomIds = generateTwoRand(1, max);
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

		Date time = new Date(System.currentTimeMillis() - subtractNum);
//		System.out.println(time);

		DataCenterLatencyBandwidth data = new DataCenterLatencyBandwidth(nameList[0], nameList[1], time, latency,
				bandwidth);
		dataCenterLatencyBandwidthRepository.save(data);

	}

	// print the stored datastructure
	public void printDataStructure() {
//		System.out.println("datacenter1" + "\t" + "datacenter2" + "\t" + "latency" + "\t" + "bandwidth");
		logger.info("datacenter1" + "\t" + "datacenter2" + "\t" + "latency" + "\t" + "bandwidth");
		for (Entry<String, List<DCDistance>> entry : dcDistanceMap.entrySet()) {
			String dc1Name = entry.getKey();
			for (DCDistance dcDistance : entry.getValue()) {
//				System.out.println(dc1Name + "\t" + dcDistance.getName() + "\t" + dcDistance.getLatency() + "\t"
//						+ dcDistance.getBandwidth());
				logger.info(dc1Name + "\t" + dcDistance.getName() + "\t" + dcDistance.getLatency() + "\t"
						+ dcDistance.getBandwidth());
			}
		}

	}

	// ask user the name of datacenters to know latency and bandwidth between them
	public void askUser() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Press yes to ask ");
		String userChoice = sc.nextLine();
		while (userChoice.equalsIgnoreCase("yes")) {

			System.out.print("Enter name of the first datacenter ");

			String dc1 = sc.nextLine();
			System.out.print("Enter name of the second datacenter ");

			String dc2 = sc.nextLine();
			String[] dcGiven = { dc1, dc2 };
			Arrays.sort(dcGiven, String.CASE_INSENSITIVE_ORDER);
			if (dcPairList.contains(dcGiven[0] + "," + dcGiven[1])) {
				printForGiven(dcGiven);
			} else
				System.out.println("Unfortunately we do not have data for that");

			System.out.println("Press yes to ask again");
			userChoice = sc.nextLine();
		}
		System.out.println("Thanks for asking!");

	}

	public TwoDataCenterValues calculateTwoDataCenter(String dc1, String dc2) {
		TwoDataCenterValues twoDataCenterValues = null;
		String[] dcGiven = { dc1, dc2 };
		Arrays.sort(dcGiven, String.CASE_INSENSITIVE_ORDER);
		if (dcPairListWithData.contains(dcGiven[0] + "," + dcGiven[1])) {
			twoDataCenterValues = calculateLatencyBandwidth(dcGiven);
			printForGiven(dcGiven);
		} else
			System.out.println("Unfortunately we do not have data for that");
		return twoDataCenterValues;
	}

	public List<TwoDataCenterValues> calculateAllDataCenter() {
		List<TwoDataCenterValues> twoDataCenterValuesList = new ArrayList<TwoDataCenterValues>();
		for (String item : dcPairListWithData) {
			System.out.println(item);
			String[] dcArray = item.trim().split(",");
			TwoDataCenterValues twoDataCenterValues;
			twoDataCenterValues = calculateLatencyBandwidth(dcArray);
			twoDataCenterValuesList.add(twoDataCenterValues);
		}
		return twoDataCenterValuesList;
	}

	// return the latency, bandwidth, datacenter1, and datacenter2
	public TwoDataCenterValues calculateLatencyBandwidth(String[] dcGiven) {
		TwoDataCenterValues twoDataCenterValues = null;
		List<DCDistance> dcDistance = dcDistanceMap.get(dcGiven[0]);
		for (DCDistance item : dcDistance) {
			if (item.getName().equalsIgnoreCase(dcGiven[1])) {
				twoDataCenterValues = new TwoDataCenterValues(dcGiven[0], dcGiven[1], item.getLatency(),
						item.getBandwidth());
			}
		}
		return twoDataCenterValues;
	}

	// print the latency and bandwidth for user input
	public void printForGiven(String[] dcGiven) {

		List<DCDistance> dcDistance = dcDistanceMap.get(dcGiven[0]);
		for (DCDistance item : dcDistance) {
			if (item.getName().equalsIgnoreCase(dcGiven[1])) {
				System.out.println(
						dcGiven[0] + "\t" + dcGiven[1] + "\t" + item.getLatency() + "\t" + item.getBandwidth());
			}
		}
	}

	// return the latitude and longitude in an array
	public Integer[] findlatlongWithDist(double distance) {
		int latency = 0;
		int bandwidth = 0;
		if (distance < 10) {
			latency = propValues.getBestLatency();
			bandwidth = propValues.getBestBandwidth();
		} else if (distance < 100) {
			latency = generateRandomNum(propValues.getBestLatency(), propValues.getBestLatency() + (int) (distance));
			bandwidth = generateRandomNum(propValues.getBestBandwidth() - (int) (distance),
					propValues.getBestBandwidth());
		} else if (distance < 500) {
			latency = generateRandomNum(propValues.getBestLatency() + 100,
					propValues.getBestLatency() + (int) (distance));
			bandwidth = generateRandomNum(propValues.getBestBandwidth() - (int) (distance) - 100,
					propValues.getBestBandwidth());
		} else if (distance < 1000) {
			latency = generateRandomNum(propValues.getBestLatency() + 500,
					propValues.getBestLatency() + (int) (distance));
			bandwidth = generateRandomNum(propValues.getBestBandwidth() - (int) (distance) - 500,
					propValues.getBestBandwidth());
		} else if (distance < 2000) {
			latency = generateRandomNum(propValues.getBestLatency() + 1000,
					propValues.getBestLatency() + (int) (distance));
			bandwidth = generateRandomNum(propValues.getBestBandwidth() - (int) (distance) - 1000,
					propValues.getBestBandwidth());
		} else {
			latency = propValues.getWorstLatency();
			bandwidth = propValues.getWorstBandwidth();
		}
		if (latency > propValues.getWorstLatency())
			latency = propValues.getWorstLatency();
		if (bandwidth < propValues.getWorstBandwidth())
			bandwidth = propValues.getWorstBandwidth();

		return new Integer[] { latency, bandwidth };
	}

	// read the config properties
	public void simulateDataDatabase() {
		propValues.readValues();
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

	public GetPropertyValues getPropValues() {
		return propValues;
	}

	public void setPropValues(GetPropertyValues propValues) {
		this.propValues = propValues;
	}

}
