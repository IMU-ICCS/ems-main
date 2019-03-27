package eu.melodic.dlms.algorithms.latency_bandwidth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import eu.melodic.dlms.db.model.DataCenter;
import eu.melodic.dlms.db.model.Distance;
import eu.melodic.dlms.db.model.TwoDCKey;
import eu.melodic.dlms.db.model.TwoDataCenterCombination;
import eu.melodic.dlms.db.model.TwoDataCenters;
import eu.melodic.dlms.db.repository.DataCenterRepository;
import eu.melodic.dlms.db.repository.TwoDataCenterCombinationRepository;
import eu.melodic.dlms.db.repository.TwoDataCentersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Algo_DataCenterAwareness {
	// set default; maybe modify just for experiments
	private double WT_LATENCY = 0.5;

	private int minLatency = Integer.MAX_VALUE;
	private int maxLatency = 0;
	private int minBandWidth = Integer.MAX_VALUE;
	private int maxBandWidth = 0;

	/**
		configuration parameters
	 */
	@Getter	@Setter
	private int paraTimeInterval;
	@Getter	@Setter
	private int paraNumRecords;
	@Getter	@Setter
	private String paraUpdateWith;
	@Getter	@Setter
	private String weightData;
	
	// latency and bandwidth between two data centers. String in Map is in the form of {dc1},{dc2}
	private Map<TwoDataCenComb, Distance> dcDistanceMap = new HashMap<TwoDataCenComb, Distance>();

	private final DataCenterRepository dataCenterRepository;
	private final TwoDataCentersRepository twoDataCentersRepository;
	private final TwoDataCenterCombinationRepository twoDataCenterCombinationRepository;

	
	/**
	 * Get the combined latency and bandwidth value between the two components
	 */
	public double calculatePerformance(String from, String to) {
		double val = -1;
		// historical execution data exists between two components
		if (connectionExist(new TwoDCKey(from, to))) {
			val = getPerformance(new TwoDCKey(from, to));
		// if original connection does not exist
		}else if (connectionExist(new TwoDCKey(to, from))) {
			val = getPerformance(new TwoDCKey(to, from));
		}
		return val;
	}
	
	/**
	 * Check if there is historical execution data between the two components
	 */
	public boolean connectionExist(TwoDCKey twoDCKey) {
		return twoDataCenterCombinationRepository.existsByTwoDCKey(twoDCKey);
	}
	
	/**
	 * Get the network performance between the two components
	 */
	public double getPerformance(TwoDCKey twoDCKey) {
		return twoDataCenterCombinationRepository.findByTwoDCKey(twoDCKey).getCombValue();
	}
	
	
	/**
	 * Compute latency and bandwidth based on historical data
	 */
	public int computeAvgAndStore() {
		boolean hasFound = false;
		List<DataCenter> dataCenterList = dataCenterRepository.findAll();

		for (int i = 0; i < dataCenterList.size(); i++) {
			DataCenter dc1 = dataCenterList.get(i);
			for (int j = i + 1; j < dataCenterList.size(); j++) {
				DataCenter dc2 = dataCenterList.get(j);
				boolean found = false;
				// other functions can be added
				switch (weightData) {
				case "averageWeight":
					found = computeAverage(dc1.getName(), dc2.getName());
					break;
				case "latestHigher":
					found = computeLatestHigher(dc1.getName(), dc2.getName());
					break;
				default:
					log.error("Invalid function selected");
				}
				// atleast two data centers must exist
				if (!hasFound)
					hasFound = found;
			}
		}
		if (!hasFound)
			log.debug("No two data centers were found");
		// combine latency and bandwidth into one for each pair of data center
		saveinDatabase();
		log.debug("Algo_CombineValSelectedRecordsRunner has successfully executed");
		return 0;
	}

	/**
	 * Compute average for lat and bandwidth for dc1 and dc2 based on time interval
	 * it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1.
	 */
	private boolean computeAverage(String dc1Id, String dc2Id) {
		List<TwoDataCenters> dataCenterList = new ArrayList<>();
		switch (this.paraUpdateWith) {
		case "time":
			LocalDateTime localDateTime = LocalDateTime.now();
			localDateTime = localDateTime.minusSeconds(this.paraTimeInterval);
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			dataCenterList = twoDataCentersRepository.findByLatestRecords(dc1Id, dc2Id, date);
			break;
		case "numRecords":
			dataCenterList = twoDataCentersRepository.findByDc1IdAndDc2IdOrderByIdDesc(dc1Id, dc2Id,
					PageRequest.of(0, this.paraNumRecords));
			break;
		default:
			log.error("The function has not been implemented yet!");
		}
		if (dataCenterList.size() == 0) { // if no record exists
			return false;
		} else {
			computeEqualWeight(dataCenterList, dc1Id, dc2Id);
			return true;
		}
	}

	/**
	 * Compute latency and bandwidth for dc1 and dc2 based on time intervals: latest higher weight
	 * it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1.
	 */
	private boolean computeLatestHigher(String dc1Id, String dc2Id) {
		List<TwoDataCenters> dataCenterList = new ArrayList<>();
		switch (this.paraUpdateWith) {
		case "time":
			LocalDateTime localDateTime = LocalDateTime.now();
			localDateTime = localDateTime.minusSeconds(this.paraTimeInterval);
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			dataCenterList = twoDataCentersRepository.findByLatestRecords(dc1Id, dc2Id, date);
			break;
		case "numRecords":
			dataCenterList = twoDataCentersRepository.findByDc1IdAndDc2IdOrderByIdDesc(dc1Id, dc2Id,
					PageRequest.of(0, this.paraNumRecords));
			break;
		default:
			log.debug("The function has not been implemented yet!");
		}

		if (dataCenterList.size() == 0) { // if no record exists
			return false;
		} else {
			computeLatestHigherWeight(dataCenterList, dc1Id, dc2Id);
			return true;
		}
	}

	/**
	 * Latest records have higher weights
	 */
	private void computeLatestHigherWeight(List<TwoDataCenters> dataCenterList, String dc1Id, String dc2Id) {
		double latency = 0, bandwidth = 0;
		int numberRecords = dataCenterList.size();
		double multiply = 0;
		int numCounter = 1; // to multiply with
		double denom = 0;// to divide

		for (TwoDataCenters dcLatencyBandwidthItem : dataCenterList) {
			multiply = (numberRecords - numCounter + 1.0) / (double) numberRecords;
			latency += dcLatencyBandwidthItem.getLatency() * multiply;
			bandwidth += dcLatencyBandwidthItem.getBandwidth() * multiply;

			denom += multiply;
			numCounter++;

			setMinMaxLatency(dcLatencyBandwidthItem);
			setMinMaxBandwidth(dcLatencyBandwidthItem);
		}
		latency = latency / denom;
		bandwidth = bandwidth / denom;

		Distance distanceNew = new Distance(latency, bandwidth);
		TwoDataCenComb twoDataCenComb = new TwoDataCenComb(dc1Id, dc2Id);
		this.dcDistanceMap.put(twoDataCenComb, distanceNew);
	}
	
	/**
	 * Store min and max latency
	 */
	private void setMinMaxLatency(TwoDataCenters dcLatencyBandwidthItem) {
		this.minLatency = this.minLatency < dcLatencyBandwidthItem.getLatency() ? this.minLatency
				: dcLatencyBandwidthItem.getLatency();
		this.maxLatency = this.maxLatency > dcLatencyBandwidthItem.getLatency() ? this.maxLatency
				: dcLatencyBandwidthItem.getLatency();
	}
	
	/**
	 * Store min and max bandwidth
	 */
	private void setMinMaxBandwidth(TwoDataCenters dcLatencyBandwidthItem) {
		this.minBandWidth = this.minBandWidth < dcLatencyBandwidthItem.getLatency() ? this.minBandWidth
				: dcLatencyBandwidthItem.getLatency();
		this.maxBandWidth = this.maxBandWidth > dcLatencyBandwidthItem.getLatency() ? this.maxBandWidth
				: dcLatencyBandwidthItem.getLatency();
	}

	
	/**
	 * All the records have equal weight
	 */
	private void computeEqualWeight(List<TwoDataCenters> dataCenterList, String dc1Id, String dc2Id) {
		double latency = 0, bandwidth = 0;
		for (TwoDataCenters dcLatencyBandwidthItem : dataCenterList) {
			latency += dcLatencyBandwidthItem.getLatency();
			bandwidth += dcLatencyBandwidthItem.getBandwidth();

			setMinMaxLatency(dcLatencyBandwidthItem);
			setMinMaxBandwidth(dcLatencyBandwidthItem);
		}
		latency = latency / dataCenterList.size();
		bandwidth = bandwidth / dataCenterList.size();

		Distance distanceNew = new Distance(latency, bandwidth);
		TwoDataCenComb twoDataCenComb = new TwoDataCenComb(dc1Id, dc2Id);
		this.dcDistanceMap.put(twoDataCenComb, distanceNew);
	}

	/**
	 * combine latency and bandwidth into one value
	 */
	public void saveinDatabase() {
		List<TwoDataCenterCombination> dataList = new ArrayList<TwoDataCenterCombination>();
		for (Map.Entry<TwoDataCenComb, Distance> entry : this.dcDistanceMap.entrySet()) {
			double norLatency = norLatency(entry.getValue().getLatency());
			double norBW = norBandWidth(entry.getValue().getBandwidth());

			double comVal = (WT_LATENCY) * norLatency + (1 - WT_LATENCY) * norBW;
			Distance distance = entry.getValue();
			distance.setLatBWVal(comVal);

			TwoDataCenterCombination data = new TwoDataCenterCombination(
					new TwoDCKey(entry.getKey().getDc1Id(), entry.getKey().getDc2Id()), distance.getLatency(),
					distance.getBandwidth(), distance.getLatBWVal());
			dataList.add(data);
		}
		this.twoDataCenterCombinationRepository.saveAll(dataList);
	}

	/**
	 * Delete all the values between two datacenters
	 */
	public void deleteFirst() {
		this.twoDataCenterCombinationRepository.deleteAll();
	}

	/**
	 * Normalization function for latency
	 */
	public double norLatency(double latency) {
		double retVal = 0;
		retVal = (latency - this.minLatency) / (this.maxLatency - this.minLatency) * 1.;
		return retVal;
	}

	/**
	 * Normalization function for bandwidth
	 */
	public double norBandWidth(double bandwidth) {
		double retVal = 0;
		retVal = (bandwidth - this.minBandWidth) / (this.maxBandWidth - this.minBandWidth) * 1.;
		return retVal;
	}

}
