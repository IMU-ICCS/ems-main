package com.example.latency.controller;

import java.time.LocalDate;
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
	List<List<DCDistance>> dcDistanceList = new ArrayList<List<DCDistance>>();
	Map<String, List<DCDistance>> dcDistanceMap = new HashMap<String, List<DCDistance>>();

	private static final Logger logger = LoggerFactory.getLogger(LatencyController.class);

	@Async
	public void run() {
		long currentTime = System.currentTimeMillis();
		// this to initialize data

//		int subtractNum = 180000;
//		for (int i = 1; i < 5000; i++)
//			insertData(subtractNum * i);
		storeInternally();
		printDataStructure(); // print the stored data structure
		while (true) {
			long newTime = System.currentTimeMillis();
			if ((newTime - currentTime) >= propValues.getTimeInterval() * 1000) {
				storeInternally();
				printDataStructure(); // print the stored data structure
				currentTime = newTime;
			}

		}
//		
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
				searchDatabase(nameList[0], nameList[1]);
			}
		}

	}

	// Search and get data from the database
	public void searchDatabase(String dc1, String dc2) {
		LocalDate localDate = LocalDate.now().minusDays(numberOfDaysConsider);
		Date date = java.sql.Date.valueOf(localDate);
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository.findByLatestRecords(dc1,
				dc2, date);

		// to store in the array
		int latency = 0;
		int bandWidth = 0;

		if (dataCenterList.size() == 0) { // if no record exists

			// this computes distance and calculates latency and bandwidth
			/*
			 * double distance = 0; try { distance = locationController.findLocation(dc1,
			 * dc2); Thread.sleep(1000); } catch (Exception e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 * 
			 * Integer[] latArray = findlatlongWithDist(distance); latency = latArray[0];
			 * bandWidth = latArray[1];
			 * 
			 */
		} else {

			for (DataCenterLatencyBandwidth dcLatencyBandwidthItem : dataCenterList) {
				latency += dcLatencyBandwidthItem.getLatency();
				bandWidth += dcLatencyBandwidthItem.getBandwidth();
			}
			// use average
			latency = latency / dataCenterList.size();
			bandWidth = bandWidth / dataCenterList.size();

//		}
			DCDistance dcDistanceNew = new DCDistance(dc2, latency, bandWidth);
			List<DCDistance> dcDistanceList = new ArrayList<DCDistance>();

			if (dcDistanceMap.containsKey(dc1)) // if dc1 already exists
				dcDistanceList = dcDistanceMap.get(dc1);

			dcDistanceList.add(dcDistanceNew);
			dcDistanceMap.put(dc1, dcDistanceList);
		}
	}

	// use gps coordinate to calculate the latency and bandwidth
	public void useGPS(String location1, String location2, int latency, int bandwidth) {

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
		if (dcPairList.contains(dcGiven[0] + "," + dcGiven[1])) {
			twoDataCenterValues = calculateLatencyBandwidth(dcGiven);
			printForGiven(dcGiven);
		} else
			System.out.println("Unfortunately we do not have data for that");
		return twoDataCenterValues;
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
