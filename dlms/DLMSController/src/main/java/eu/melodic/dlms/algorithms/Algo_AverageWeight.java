package eu.melodic.dlms.algorithms;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.melodic.dlms.DlmsRestController;
import eu.melodic.dlms.algorithms.dbModel.DataCenter;
import eu.melodic.dlms.algorithms.dbModel.DataCenterLatencyBandwidth;
import eu.melodic.dlms.algorithms.model.Distance;
import eu.melodic.dlms.algorithms.model.TwoDataCenComb;
import eu.melodic.dlms.algorithms.repository.DataCenterLatencyBandwidthRepository;
import eu.melodic.dlms.algorithms.repository.DataCenterRepository;

public class Algo_AverageWeight {
	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsRestController.class);
	private DataCenterRepository dataCenterRepository;
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;

	// latency and bandwidth between two data centers. String in Map is in the form
	// of {dc1},{dc2}
	private Map<TwoDataCenComb, Distance> dcDistanceMap = new HashMap<TwoDataCenComb, Distance>();
	private int paraTimeInterval;

	public Algo_AverageWeight(DataCenterRepository dataCenterRepository,
			DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository) {
		this.dataCenterRepository = dataCenterRepository;
		this.dataCenterLatencyBandwidthRepository = dataCenterLatencyBandwidthRepository;
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
		return 0;
	}

	// compute the average for latency and bandwidth for dc1 and dc2
	// it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1
	private boolean computeAverage(Long dc1Id, Long dc2Id) {
		LocalDateTime localDateTime = LocalDateTime.now();
		// read from the configuration file
		// testing with manual value currently
		localDateTime = localDateTime.minusSeconds(this.paraTimeInterval);
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository
				.findByLatestRecords(dc1Id, dc2Id, date);

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
		}
		latency = latency / dataCenterList.size();
		bandwidth = bandwidth / dataCenterList.size();

		Distance distanceNew = new Distance(latency, bandwidth);
		TwoDataCenComb twoDataCenComb = new TwoDataCenComb(dc1Id, dc2Id);
		dcDistanceMap.put(twoDataCenComb, distanceNew);
	}

	public int getParaTimeInterval() {
		return paraTimeInterval;
	}

	public void setParaTimeInterval(int paraTimeInterval) {
		this.paraTimeInterval = paraTimeInterval;
	}

}
