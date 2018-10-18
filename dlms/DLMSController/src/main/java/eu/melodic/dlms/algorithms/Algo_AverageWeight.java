package eu.melodic.dlms.algorithms;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.algorithms.dbModel.DataCenter;
import eu.melodic.dlms.algorithms.dbModel.DataCenterLatencyBandwidth;
import eu.melodic.dlms.algorithms.model.Distance;
import eu.melodic.dlms.algorithms.repository.DataCenterLatencyBandwidthRepository;
import eu.melodic.dlms.algorithms.repository.DataCenterRepository;

public class Algo_AverageWeight {

	private DataCenterRepository dataCenterRepository;
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;
	// list of paired dataset with historical data to get latency and bandwidth
	private List<String> dcPairListWithData = new ArrayList<>();
	// latency and bandwidth between two data centers. String in Map is in the form
	// of {dc1},{dc2}
	private Map<String, Distance> dcDistanceMap = new HashMap<String, Distance>();

	public Algo_AverageWeight(DataCenterRepository dataCenterRepository, DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository) {
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

				boolean found = computeAverage(dc1.getName(), dc2.getName());
				// atleast two data centers must exist
				if (!hasFound)
					hasFound = found;
			}
		}
		if (hasFound)
			return 0;
		else
			return 1;
	}

	// compute the average for latency and bandwidth for dc1 and dc2
	// it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1
	private boolean computeAverage(String dc1, String dc2) {
		LocalDateTime localDateTime = LocalDateTime.now();
		// read from the configuration file
		// testing with manual value currently
		localDateTime = localDateTime.minusSeconds(3000);
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository.findByLatestRecords(dc1,
				dc2, date);

		if (dataCenterList.size() == 0) { // if no record exists
			return false;
		} else {
			computeEqualWeight(dataCenterList, dc1, dc2);
			dcPairListWithData.add(dc1 + "," + dc2);
			return true;
		}
	}

	private void computeEqualWeight(List<DataCenterLatencyBandwidth> dataCenterList, String dc1, String dc2) {
		double latency = 0, bandwidth = 0;
		for (DataCenterLatencyBandwidth dcLatencyBandwidthItem : dataCenterList) {
			latency += dcLatencyBandwidthItem.getLatency();
			bandwidth += dcLatencyBandwidthItem.getBandwidth();
		}
		latency = latency / dataCenterList.size();
		bandwidth = bandwidth / dataCenterList.size();

		Distance distanceNew = new Distance(latency, bandwidth);
		dcDistanceMap.put(dc1 + "," + dc2, distanceNew);
	}

}
