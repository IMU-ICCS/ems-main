package eu.melodic.dlms.algorithm_runners;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.cost.Algo_DlmsTotalCosts;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.common.DlmsConfigurationElement;
import lombok.extern.slf4j.Slf4j;

/**
 * Calculate the total cost of solutions
 */
@Slf4j
public class Algo_DlmsTotalCostsRunner extends AlgorithmRunner {

	private Algo_DlmsTotalCosts algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_DlmsTotalCosts(application.getCpRepository(), application.getRegionRepository(),
				application.getDataCenterRepository(), application.getDataCenterZoneRepository());
	}

	@Override
	public int update(Object... parameters) {
		if (parameters.length < 2) {
			log.error("Number of parameters is not enough for Algo_DlmsTotalCostsRunner to caculate the utlities");
			return -1;
		}
		// parameters for normalization to calculate the utilities
		this.algo.setMax(Long.parseLong(parameters[0].toString()));
		this.algo.setMin(Long.parseLong(parameters[1].toString()));

		return 0;
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// TODO Auto-generated method stub
		log.debug("Calculating utility from Algo_DlmsTotalCostsRunner");
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
						// calculate the total cost utility between components
						double currentUtility = algo.totalCost(fromElement.getId(), toElement.getId());

						// there is no historical data between the two connections
						if (currentUtility == -1) {
							log.debug("No historical data exists between: {} and {}", fromElement.getId(),
									toElement.getId());
						} else{
							// increase iteration and utility
							numberConnection++;
							utility += currentUtility;
						}
					}
				}
			}
		}
		log.debug("Utility for DLMS_TOTAL_COSTS was calculated successfully");
		return (numberConnection > 0 ? utility / numberConnection : 0);
	}

}
