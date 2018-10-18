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

public class Algo_LatestHigherWeight {
	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsRestController.class);
	private DataCenterRepository dataCenterRepository;
	private DataCenterLatencyBandwidthRepository dataCenterLatencyBandwidthRepository;

	// latency and bandwidth between two data centers. String in Map is in the form
	// of {dc1},{dc2}
	// probably save this to the database or access it using rest
	private Map<TwoDataCenComb, Distance> dcDistanceMap = new HashMap<TwoDataCenComb, Distance>();
	private int paraTimeInterval;

	public Algo_LatestHigherWeight(DataCenterRepository dataCenterRepository,
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

				boolean found = computeLatestHigher(dc1.getName(), dc2.getName());
				// atleast two data centers must exist
				if (!hasFound)
					hasFound = found;
			}
		}
		if (!hasFound)
			LOGGER.info("No two data centers were found");
		return 0;
	}

	// compute lat and bandwid for dc1 and dc2, latest data are give higher wts
	// it is not commutative, i.e., dc1 and dc2 is not equal to dc2 and dc1
	public boolean computeLatestHigher(String dc1Name, String dc2Name) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusSeconds(this.paraTimeInterval);
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<DataCenterLatencyBandwidth> dataCenterList = dataCenterLatencyBandwidthRepository
				.findByLatestRecordsOrdered(dc1Name, dc2Name, date);

		if (dataCenterList.size() == 0) { // if no record exists
			return false;
		} else {
			algoLatestHigherWeight(dataCenterList, dc1Name, dc2Name);
			return true;
		}

	}

	public void algoLatestHigherWeight(List<DataCenterLatencyBandwidth> dataCenterList, String dc1Name,
			String dc2Name) {
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

		Distance distanceNew = new Distance(latency, bandwidth);
		TwoDataCenComb twoDataCenComb = new TwoDataCenComb(dc1Name, dc2Name);
		dcDistanceMap.put(twoDataCenComb, distanceNew);
	}

	public int getParaTimeInterval() {
		return paraTimeInterval;
	}

	public void setParaTimeInterval(int paraTimeInterval) {
		this.paraTimeInterval = paraTimeInterval;
	}

}
