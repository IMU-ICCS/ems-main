package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.utility.Algo_DlmsTotalUtility;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;

/**
 * Calculate the total utility of solutions
 */

// Not yet implemented
public class Algo_DlmsTotalUtilityRunner extends AlgorithmRunner {

	private Algo_DlmsTotalUtility algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		
	}

	/**
	 * Not essential to update this
	 */
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
