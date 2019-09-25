package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.metric_sender.Algo_DlmsMetricSender_DataCenter;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import lombok.extern.slf4j.Slf4j;

/**
 * Generate the metrics between two data centers No need of this class in
 * production. This is to generate data for testing.
 */
@Slf4j
public class Algo_DlmsMetricSender_DataCenterRunner extends AlgorithmRunner {

	private Algo_DlmsMetricSender_DataCenter algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_DlmsMetricSender_DataCenter();
	}

	@Override
	public int update(Object... parameters) {
		if (parameters.length < 6) {
			log.error("Number of parameters is not enough for Algo_DlmsMetricSender_DataCenter");
			return -1;
		}

		String jmsServerAddress = parameters[0].toString();
		String jmsServerPort = parameters[1].toString();
		int bestLatency = Integer.parseInt(parameters[2].toString());
		int worstLatency = Integer.parseInt(parameters[3].toString());
		int bestBandwidth = Integer.parseInt(parameters[4].toString());
		int worstBandwidth = Integer.parseInt(parameters[5].toString());
		// set configurations
		algo.setJmsServerAddress(jmsServerAddress);
		algo.setJmsServerPort(jmsServerPort);
		algo.setBestLatency(bestLatency);
		algo.setWorstLatency(worstLatency);
		algo.setBestBandwidth(bestBandwidth);
		algo.setWorstBandwidth(worstBandwidth);

		try {
			return algo.run();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return -1;
		}
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// TODO Auto-generated method stub
		return 0;
	}

}
