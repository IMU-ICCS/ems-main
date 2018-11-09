package eu.melodic.dlms.algorithms.latencyBandwidth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import eu.melodic.dlms.DlmsRestController;
import eu.melodic.dlms.algorithms.latencyBandwidth.dbModel.DataCenter;
import eu.melodic.dlms.algorithms.latencyBandwidth.dbModel.DataCenterLatencyBandwidth;
import eu.melodic.dlms.algorithms.latencyBandwidth.dbModel.TwoDCKey;
import eu.melodic.dlms.algorithms.latencyBandwidth.dbModel.TwoDataCenterCombination;
import eu.melodic.dlms.algorithms.latencyBandwidth.model.Distance;
import eu.melodic.dlms.algorithms.latencyBandwidth.model.TwoDataCenComb;
import eu.melodic.dlms.algorithms.latencyBandwidth.repository.DataCenterLatencyBandwidthRepository;
import eu.melodic.dlms.algorithms.latencyBandwidth.repository.DataCenterRepository;
import eu.melodic.dlms.algorithms.latencyBandwidth.repository.TwoDataCenterCombinationRepository;

public class AlgoAvgWtLatBand {
	// set default; maybe modify just for experiments
	private final double WT_LATENCY = 0.5;

	private int minLatency = Integer.MAX_VALUE;
	private int maxLatency = 0;
	private int minBandWidth = Integer.MAX_VALUE;
	private int maxBandWidth = 0;

	// configuration parameter
	private int paraTimeInterval;
	private int paraNumRecords;
	private String paraUpdateWith;

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsRestController.class);
	private DataCenterRepository dataCenterRepository;
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;
	private TwoDataCenterCombinationRepository twoDataCenterCombinationRepository;

	// latency and bandwidth between two data centers. String in Map is in the form
	// of {dc1},{dc2}
	private Map<TwoDataCenComb, Distance> dcDistanceMap = new HashMap<TwoDataCenComb, Distance>();

	public AlgoAvgWtLatBand(DataCenterRepository dataCenterRepository,
			DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository,
			TwoDataCenterCombinationRepository twoDataCenterCombinationRepository) {
		this.dataCenterRepository = dataCenterRepository;
		this.dataCenterLatencyBandwidthRepository = dataCenterLatencyBandwidthRepository;
		this.twoDataCenterCombinationRepository = twoDataCenterCombinationRepository;
	}

	// use algorithm to find latency and bandwidth based on historical data
	public int computeAvgAndStore() {
		boolean hasFound = false;
		List<DataCenter> dataCenterList = new ArrayList<DataCenter>();
		dataCenterList = dataCenterRepository.findAll();
		for (int i = 0; i < dataCenterList.size(); i++) {
			DataCenter dc1 = dataCenterList.get(i);
			for (int j = i + 1; j < dataCenterList.size(); j++) {
				DataCenter dc2 = dataCenterList.get(j);

				boolean found = computeAverage(dc1.getId(), dc2.getId());
				// atleast two data centers must exist
				if (!hasFound)
					hasFound = found;
			}
		}
		if (!hasFound)
			LOGGER.info("No two data centers were found");
		// combine latency and bandwidth into one for each pair of data center
		saveinDatabase();
		LOGGER.info("Algorithm AlgoAvgWtLatBand has successfully executed");
		return 0;
	}

	// compute average for lat and bandwidth for dc1 and dc2 based on time intervals
	// it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1
	private boolean computeAverage(Long dc1Id, Long dc2Id) {
		List<DataCenterLatencyBandwidth> dataCenterList = new ArrayList<DataCenterLatencyBandwidth>();
		switch (this.paraUpdateWith) {
		case "time":
			LocalDateTime localDateTime = LocalDateTime.now();
			localDateTime = localDateTime.minusSeconds(this.paraTimeInterval);
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			dataCenterList = dataCenterLatencyBandwidthRepository.findByLatestRecords(dc1Id, dc2Id, date);
			break;
		case "numRecords":
			dataCenterList = dataCenterLatencyBandwidthRepository.findByDc1AndDc2OrderByIdDesc(dc1Id, dc2Id,
					PageRequest.of(0, this.paraNumRecords));
			break;
		default:
			System.out.println("Sorry the function has not been implemented yet!");
		}

		if (dataCenterList.size() == 0) { // if no record exists
			return false;
		} else {
			computeEqualWeight(dataCenterList, dc1Id, dc2Id);
			return true;
		}
	}

	private void computeEqualWeight(List<DataCenterLatencyBandwidth> dataCenterList, Long dc1Id, Long dc2Id) {
		double latency = 0, bandwidth = 0;
		for (DataCenterLatencyBandwidth dcLatencyBandwidthItem : dataCenterList) {
			latency += dcLatencyBandwidthItem.getLatency();
			bandwidth += dcLatencyBandwidthItem.getBandwidth();

			this.minLatency = this.minLatency < dcLatencyBandwidthItem.getLatency() ? this.minLatency
					: dcLatencyBandwidthItem.getLatency();
			this.maxLatency = this.maxLatency > dcLatencyBandwidthItem.getLatency() ? this.maxLatency
					: dcLatencyBandwidthItem.getLatency();

			this.minBandWidth = this.minBandWidth < dcLatencyBandwidthItem.getLatency() ? this.minBandWidth
					: dcLatencyBandwidthItem.getLatency();
			this.maxBandWidth = this.maxBandWidth > dcLatencyBandwidthItem.getLatency() ? this.maxBandWidth
					: dcLatencyBandwidthItem.getLatency();
		}
		latency = latency / dataCenterList.size();
		bandwidth = bandwidth / dataCenterList.size();

		Distance distanceNew = new Distance(latency, bandwidth);
		TwoDataCenComb twoDataCenComb = new TwoDataCenComb(dc1Id, dc2Id);
		this.dcDistanceMap.put(twoDataCenComb, distanceNew);
	}

	// combine latency and bandwidth into one value
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

	public double norLatency(double latency) {
		double retVal = 0;
		retVal = (latency - this.minLatency) / (this.maxLatency - this.minLatency) * 1.;
		return retVal;
	}

	public double norBandWidth(double bandwidth) {
		double retVal = 0;
		retVal = (bandwidth - this.minBandWidth) / (this.maxBandWidth - this.minBandWidth) * 1.;
		return retVal;
	}

	public int getParaTimeInterval() {
		return paraTimeInterval;
	}

	public void setParaTimeInterval(int paraTimeInterval) {
		this.paraTimeInterval = paraTimeInterval;
	}

	public int getParaNumRecords() {
		return paraNumRecords;
	}

	public void setParaNumRecords(int paraNumRecords) {
		this.paraNumRecords = paraNumRecords;
	}

	public String getParaUpdateWith() {
		return paraUpdateWith;
	}

	public void setParaUpdateWith(String paraUpdateWith) {
		this.paraUpdateWith = paraUpdateWith;
	}

}
