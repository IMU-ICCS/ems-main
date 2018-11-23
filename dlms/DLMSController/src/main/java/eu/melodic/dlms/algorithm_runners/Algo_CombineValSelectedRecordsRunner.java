package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.latency_bandwidth.Algo_CombineValSelectedRecords;
import eu.melodic.dlms.utility.DlmsDiffBundle;
import lombok.extern.slf4j.Slf4j;

/**
 * Get a list of historical records.
 * Calculate latency and bandwidth between two datacenters for the records based on different weight assignment strategy
 * Combine and normal latency and bandwidth to a single value between two datacenters
 */
@Slf4j
public class Algo_CombineValSelectedRecordsRunner implements AlgorithmRunner {

	private Algo_CombineValSelectedRecords algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_CombineValSelectedRecords(application.getDataCenterRepository(),
				application.getTwoDataCentersRepository(),
				application.getTwoDataCenterCombinationRepository());
	}

	@Override
	public double queryResults(DlmsDiffBundle diffBundle) {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		if(parameters.length<4) {
			log.error("Number of parameters is not enough for Algo_CombineValSelectedRecordsRunner");
			return -1;
		}
		int paraTimeInterval = Integer.parseInt(parameters[0].toString());
		int paraNumRecords = Integer.parseInt(parameters[1].toString());
		String paraUpdateWith = parameters[2].toString();
		String weightData = parameters[3].toString();
		// set configuration
		algo.setParaTimeInterval(paraTimeInterval);
		algo.setParaNumRecords(paraNumRecords);
		algo.setParaUpdateWith(paraUpdateWith);
		algo.setWeightData(weightData);
		// run
		return algo.computeAvgAndStore();
	}

}
