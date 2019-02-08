package eu.melodic.dlms.algorithm_runners;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.affinity.Algo_AffinityAwareness;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.DlmsConfigurationElement;
import lombok.extern.slf4j.Slf4j;

/**
 * Calculate affinity between application component and data source
 */
@Slf4j
public class Algo_AffinityAwarenessRunner extends AlgorithmRunner {

	private Algo_AffinityAwareness algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_AffinityAwareness(application.getAcRepository(), application.getDsRepository(),
				application.getAcDsDataRepository(), application.getAcDsAffinityRepository());
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

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		log.debug("Calculating utility from Algo_AffinityAwarenessRunner");
		Collection<DlmsConfigurationElement> proposed = diff.getProposedConfiguration();
		Map<String, List<String>> compConMap = diff.getCompConMap();

		double utility = 0;
		int numberConnection = 0;
		for (Map.Entry<String, List<String>> comp : compConMap.entrySet()) {
			String fromComp = comp.getKey();
			List<String> toCompList = comp.getValue();

			DlmsConfigurationElement fromElement = getComp(proposed, fromComp);
			if (!isEmpty(fromElement)) {
				// connected to data source
				for (String toComp : toCompList) {
					DlmsConfigurationElement toElement = getComp(proposed, toComp);
					if (!isEmpty(toElement)) {
						// calculate the utility between application component and datasource
						double currentUtility = algo.calculateAffinity(fromElement.getId(), toElement.getId());

						// there is no historical data between the two connections
						if (currentUtility == -1) {
							log.debug("No historical data exists between: {} and {}", fromElement.getId(),
									toElement.getId());
						} else
							// increase iteration
							numberConnection++;
						utility += currentUtility;
					}
				}
			}
		}
		log.debug("Utility for AFFINITY_AWARENESS was calculated successfully");
		return (numberConnection > 0 ? utility / numberConnection : 0);
	}

}
