package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.source.Algo_SourceAwareness;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;

/**
 * Calculate the source awareness
 * Need to experiment with Camel model!!!
 */

// Not yet implemented
public class Algo_SourceAwarenessRunner extends AlgorithmRunner {

	private Algo_SourceAwareness algo;

	@Override
	public void initialize(DlmsControllerApplication application) {

	}

	/**
	 * Not essential to update this
	 */
	public int update(Object... parameters) {
		return 0;
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// TODO Auto-generated method stub
		return 0;
	}

}
