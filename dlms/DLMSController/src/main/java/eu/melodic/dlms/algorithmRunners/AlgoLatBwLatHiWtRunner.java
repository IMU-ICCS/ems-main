package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.algoLatencyBandwidth.AlgoLatBwAvgWt;
import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;

/**
 * Demo implementation of a simple algorithm.
 */
public class AlgoLatBwLatHiWtRunner implements AlgorithmRunner {

	private AlgoLatBwAvgWt algoLatBwAvgWt;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algoLatBwAvgWt = new AlgoLatBwAvgWt(application.getDataCenterRepository(),
				application.getDataCenterLatencyBandwidthRepository(),
				application.getTwoDataCenterCombinationRepository());
	}

	@Override
	public double queryResults() {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		int paraTimeInterval = Integer.parseInt(parameters[0].toString());
		int paraNumRecords = Integer.parseInt(parameters[1].toString());
		String paraUpdateWith = parameters[2].toString();
		// set configuration
		algoLatBwAvgWt.setParaTimeInterval(paraTimeInterval);
		algoLatBwAvgWt.setParaNumRecords(paraNumRecords);
		algoLatBwAvgWt.setParaUpdateWith(paraUpdateWith);
		// run
		return (algoLatBwAvgWt.computeAvgAndStore());
	}

}
