package eu.melodic.dlms.algorithm_runners;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import camel.deployment.SoftwareComponent;
import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.latency_bandwidth.Algo_DataCenterAwareness;
import eu.melodic.dlms.utility.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.DlmsConfigurationElement;
import lombok.extern.slf4j.Slf4j;

/**
 * Get a list of historical records. Calculate latency and bandwidth between two
 * datacenters for the records based on different weight assignment strategy
 * Combine and normal latency and bandwidth to a single value between two
 * datacenters
 */
@Slf4j
public class Algo_DataCenterAwarenessRunner implements AlgorithmRunner {

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
		System.out.println(parameters[0].toString());
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
		Collection<DlmsConfigurationElement> proposed = diff.getProposedConfiguration();
		Map<SoftwareComponent, List<SoftwareComponent>> compConMap = diff.getCompConMap();

		double utility = 0;
		int numberConnection = 0;
		for (Map.Entry<SoftwareComponent, List<SoftwareComponent>> comp : compConMap.entrySet()) {
			SoftwareComponent fromComp = comp.getKey();
			List<SoftwareComponent> toCompList = comp.getValue();

			DlmsConfigurationElement fromElement = getComp(proposed, fromComp);
			// connected to data source
			for (SoftwareComponent toComp : toCompList) {
				DlmsConfigurationElement toElement = getComp(proposed, toComp);
				// calculate the utility between application component and datasource
				double currentUtility = algo.calculatePerformance(fromElement.getId(), toElement.getId());

				// there is no historical data between the two connections
				if (currentUtility == -1) {
					log.debug("No historical data exists between: {} and {}", fromElement.getId(), toElement.getId());

				} else
					// increase iteration
					numberConnection++;
				utility += currentUtility;
			}
		}
		log.info("Utility for DATA_CENTER_AWARENESS was calculated successfully");
		return utility / numberConnection;
	}

	/**
	 * Get DlmsConfigurationElement matching the connection component name
	 */
	private DlmsConfigurationElement getComp(Collection<DlmsConfigurationElement> deployed, SoftwareComponent toComp) {
		DlmsConfigurationElement element = new DlmsConfigurationElement();
		for (DlmsConfigurationElement deployedElement : deployed) {
			if (deployedElement.getId().equals(toComp.getName())) {
				element = deployedElement;
				break;
			}
		}
		return element;
	}

}
