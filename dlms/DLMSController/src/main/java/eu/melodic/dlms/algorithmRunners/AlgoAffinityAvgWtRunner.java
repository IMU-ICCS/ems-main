package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.algoAffinity.AlgoAffinityAvgWt;
import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;

/**
 * Demo implementation of a simple algorithm.
 */
public class AlgoAffinityAvgWtRunner implements AlgorithmRunner {

	private AlgoAffinityAvgWt algoAffinityAvgWt;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algoAffinityAvgWt = new AlgoAffinityAvgWt(application.getAcRepository(), application.getDsRepsitory(),
				application.getAcDSDataRepository(), application.getAcDSRelationRepository());
	}

	@Override
	public double queryResults() {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		int paraNumRecords = Integer.parseInt(parameters[0].toString());
		// set configuration
		algoAffinityAvgWt.setParaNumRecords(paraNumRecords);
		// run
		return (algoAffinityAvgWt.computeAffinityAvgWt());
	}

}
