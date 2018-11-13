package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.latencyBandwidth.AlgoAvgWtLatBand;
import eu.melodic.dlms.utility.DlmsDiffBundle;

/**
 * Demo implementation of a simple algorithm.
 */
public class Algo_LatestHigherWeightRunner implements AlgorithmRunner {

	private AlgoAvgWtLatBand algo1;

	@Override
	public void initialize(DlmsControllerApplication application) {
//		algo1 = new Algo_AverageWeight(application.getDataCenterRepository(),
//				application.getDataCenterLatencyBandwidthRepository());
	}

	@Override
	public double queryResults(DlmsDiffBundle diffBundle) {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		int paraTimeInterval = Integer.parseInt(parameters[0].toString());
		algo1.setParaTimeInterval(paraTimeInterval);
		return (algo1.computeAvgAndStore());
	}

}
