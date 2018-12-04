package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.cost.Algo_ComputeCost;
import eu.melodic.dlms.utility.DlmsDiffBundle;

/**
 * Calculate the cost of solutions
 */
public class Algo_ComputeCostRunner implements AlgorithmRunner {

	private Algo_ComputeCost algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_ComputeCost(application.getCpRepository(), application.getRegionRepository(), application.getDataCenterRepository(), application.getDataCenterZoneRepository());
	}

	@Override
	public double queryResults(DlmsDiffBundle diffBundle) {
		// TO DO: Get src, dst, size, max, and min from other components	
		return algo.totalCost();
	}

	@Override
	public int update(Object... parameters) {	
		// Is update needed?
		return 0;
	}

}
