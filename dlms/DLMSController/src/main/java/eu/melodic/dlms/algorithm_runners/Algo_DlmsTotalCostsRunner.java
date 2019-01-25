package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.cost.Algo_DlmsTotalCosts;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;

/**
 * Calculate the cost of solutions
 */
public class Algo_DlmsTotalCostsRunner implements AlgorithmRunner {

	private Algo_DlmsTotalCosts algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_DlmsTotalCosts(application.getCpRepository(), application.getRegionRepository(), application.getDataCenterRepository(), application.getDataCenterZoneRepository());
	}

	@Override
	public int update(Object... parameters) {	
		// Is update needed?
		return 0;
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// TODO Auto-generated method stub
		return 0;
	}



}
