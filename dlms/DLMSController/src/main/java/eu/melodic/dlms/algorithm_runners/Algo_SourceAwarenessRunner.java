package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.source.Algo_SourceAwareness;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;

/**
 * Calculate the cost of solutions
 */
public class Algo_SourceAwarenessRunner implements AlgorithmRunner {

	private Algo_SourceAwareness algo;

	@Override
	public void initialize(DlmsControllerApplication application) {

	}

	@Override
	public int update(Object... parameters) {
		return 0;
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// TODO Auto-generated method stub
		return 0;
	}

}
