package eu.melodic.dlms.algorithm_runners;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.latency_bandwidth.Algo_DataCenterAwareness;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.common.DlmsConfigurationElement;
import lombok.extern.slf4j.Slf4j;

/**
 * Get a list of historical records. Calculate latency and bandwidth between two
 * datacenters for the records based on different weight assignment strategy.
 * Combine and normal latency and bandwidth to a single value between two datacenters.
 */
@Slf4j
public class Algo_DataCenterAwarenessRunner extends AlgorithmRunner {

	private Algo_DataCenterAwareness algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_DataCenterAwareness(application.getDataCenterRepository(),
				application.getTwoDataCentersRepository(), application.getTwoDataCenterCombinationRepository());
	}

	@Override
	public int update(Object... parameters) {
		if (parameters.length < 4) {
			log.error("Number of parameters is not enough for Algo_CombineValSelectedRecordsRunner");
			return -1;
		}
		int paraTimeInterval = Integer.parseInt(parameters[0].toString());
		int paraNumRecords = Integer.parseInt(parameters[1].toString());
		String paraUpdateWith = parameters[2].toString();
		String weightData = parameters[3].toString();
		// set configuration
		algo.setParaTimeInterval(paraTimeInterval);
		algo.setParaNumRecords(paraNumRecords);
		algo.setParaUpdateWith(paraUpdateWith);
		algo.setWeightData(weightData);
		// run
		return algo.computeAvgAndStore();
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		log.debug("Calculating utility from Algo_DataCenterAwarenessRunner");
		Collection<DlmsConfigurationElement> proposed = diff.getProposedConfiguration();
		Map<String, List<String>> compConMap = diff.getCompConMap();

		double utility = 0;
		int numberConnection = 0;
		for (Map.Entry<String, List<String>> comp : compConMap.entrySet()) {
			String fromComp = comp.getKey();
			List<String> toCompList = comp.getValue();
			log.debug("Calculating utility");
			DlmsConfigurationElement fromElement = getComp(proposed, fromComp);

			if (!isEmpty(fromElement)) {
				// connection exists
				for (String toComp : toCompList) {
					DlmsConfigurationElement toElement = getComp(proposed, toComp);
					if (!isEmpty(toElement)) {
						// calculate the utility between application component and data center
						double currentUtility = algo.calculatePerformance(fromElement.getId(), toElement.getId());

						// there is no historical data between the two connections
						if (currentUtility == -1) {
							log.debug("No historical data exists between: {} and {}", fromElement.getId(),
									toElement.getId());
						} else {
							// increase iteration and utility
							numberConnection++;
							utility += currentUtility;
						}
					}
				}
			}
		}
		log.debug("Utility for DATA_CENTER_AWARENESS was calculated successfully");
		return (numberConnection > 0 ? utility / numberConnection : 0);
	}

}
