package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.metric_sender.Algo_DlmsMetricSender_AcDsDataWrite;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import lombok.extern.slf4j.Slf4j;

/**
 * Generate the metrics for data write between application component and data
 * source. This is to generate data for testing.
 */
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataWriteRunner extends AlgorithmRunner {

	private Algo_DlmsMetricSender_AcDsDataWrite algo;

	@Override
	public void initialize(DlmsControllerApplication application) {
		algo = new Algo_DlmsMetricSender_AcDsDataWrite();
	}


	@Override
	public int update(Object... parameters) {
		if (parameters.length < 6) {
			log.error("Number of parameters is not enough for Algo_DlmsMetricSender_AcDsDataWriteRunner");
			return -1;
		}
		String jmsServerAddress = parameters[0].toString();
		String jmsServerPort = parameters[1].toString();
		int numAC = Integer.parseInt(parameters[2].toString());
		int numDS = Integer.parseInt(parameters[3].toString());
		long bestDataWrite = Long.parseLong(parameters[4].toString());
		long worstDataWrite = Long.parseLong(parameters[5].toString());

		// set configurations
		algo.setJmsServerAddress(jmsServerAddress);
		algo.setJmsServerPort(jmsServerPort);
		algo.setNumAC(numAC);
		algo.setNumDS(numDS);
		algo.setBestDataWrite(bestDataWrite);
		algo.setWorstDataWrite(worstDataWrite);

		try {
			return algo.run();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return -1;
		}
	}

	@Override
	public double queryResults(DlmsConfigurationConnection diff) {
		// currently utility is not necessary to generate utility by this algorithm
		return 0;
	}

}
