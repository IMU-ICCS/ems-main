package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.affinity.Algo_CalculateCouplet;
import eu.melodic.dlms.utility.DlmsDiffBundle;
import lombok.extern.slf4j.Slf4j;

/**
 * Calculate affinity between application component and data source
 */
@Slf4j
public class Algo_CalculateCoupletRunner implements AlgorithmRunner {

	private Algo_CalculateCouplet algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_CalculateCouplet(application.getAcRepository(), application.getDsRepository(),
				application.getAcDsDataRepository(), application.getAcDsAffinityRepository());
	}

	@Override
	public double queryResults(DlmsDiffBundle diffBundle) {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		if (parameters.length < 2) {
			log.error("Number of parameters is not enough for Algo_CalculateCoupletRunner");
			return -1;
		}
		int paraNumRecords = Integer.parseInt(parameters[0].toString());
		String functionName = parameters[1].toString();

		algo.setParaNumRecords(paraNumRecords);
		algo.setFunctionName(functionName);
		return algo.computeAffinity();
	}

}
