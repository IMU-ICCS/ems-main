package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.clusteringDataCenter.Algo_ClusterDataCenters;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import lombok.extern.slf4j.Slf4j;

/**
 * Cluster data centers to different zones 
 * Two clustering algorithms are available with different working mechanisms
 */
@Slf4j
public class Algo_ClusterDataCentersRunner extends AlgorithmRunner {

	private Algo_ClusterDataCenters algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_ClusterDataCenters(application.getTwoDataCenterCombinationRepository(),
				application.getDataCenterZoneRepository());
	}

	@Override
	public int update(Object... parameters) {
		if (parameters.length < 2) {
			log.error("Number of parameters is not enough for Algo_ClusterDataCentersRunner");
			return -1;
		}
		String clusteringMethod = parameters[0].toString();
		int numCluster = Integer.parseInt(parameters[1].toString());

		algo.setClusteringMethod(clusteringMethod);
		algo.setNumCluster(numCluster);
		return algo.cluster();
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// currently eu.melodic.upperware.genetic_solver.utility is not necessary to generate eu.melodic.upperware.genetic_solver.utility by this algorithm
		return 0;
	}

}
