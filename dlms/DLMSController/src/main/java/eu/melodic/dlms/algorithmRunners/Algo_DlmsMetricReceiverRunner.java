package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.latencyBandwidth.AlgoAvgWtLatBand;
import eu.melodic.dlms.utility.DlmsDiffBundle;

/**
 * Demo implementation of a simple algorithm.
 */
public class Algo_DlmsMetricReceiverRunner implements AlgorithmRunner {

	private AlgoAvgWtLatBand algo1;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo1 = new AlgoAvgWtLatBand(application.getDataCenterRepository(),
				application.getDataCenterLatencyBandwidthRepository(),
				application.getTwoDataCenterCombinationRepository());
	}

	@Override
	public double queryResults(DlmsDiffBundle diffBundle) {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		int paraTimeInterval = Integer.parseInt(parameters[0].toString());
		int paraNumRecords = Integer.parseInt(parameters[1].toString());
		String paraUpdateWith = parameters[2].toString();
		// set configuration
		algo1.setParaTimeInterval(paraTimeInterval);
		algo1.setParaNumRecords(paraNumRecords);
		algo1.setParaUpdateWith(paraUpdateWith);
		// run
		return (algo1.computeAvgAndStore());
	}

}
