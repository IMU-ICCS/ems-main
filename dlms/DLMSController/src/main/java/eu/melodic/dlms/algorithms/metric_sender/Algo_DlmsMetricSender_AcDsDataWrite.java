package eu.melodic.dlms.algorithms.metric_sender;

import java.util.Date;

import eu.melodic.dlms.algorithms.utility.RandomGenerator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to randomly generate data write between application component and data
 * source Send to metric generator This class is only for TEST and should be
 * commented in production
 */

@Setter
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataWrite extends Algo_DlmsMetricSender<AcDsDataWritePojo> {
	// Configuration parameters
	private int numAC;
	private int numDS;
	private long bestDataWrite;
	private long worstDataWrite;

	// Pattern how the message should be sent
	private final String PATTERN = "{\"ac\":\"%d\" , \"ds\":\"%d\" , \"amountWrite\":\"%d\" , \"timeStamp\":\"%d\"}";

	@Override
	public int run() throws Exception {
		long ac = RandomGenerator.generateNum(1, numAC);
		long ds = RandomGenerator.generateNum(1, numDS);

		long amountWrite = RandomGenerator.generateNum(worstDataWrite, bestDataWrite);

		AcDsDataWritePojo parameters = AcDsDataWritePojo
				.builder()
				.ac(ac)
				.ds(ds)
				.amountWrite(amountWrite)
				.build();

		sendOneMessage(parameters);
		log.info("Algo_DlmsMetricSender_AcDsDataWrite has successfully executed");
		return 0;
	}

	@Override
	protected String getMessage(AcDsDataWritePojo parameters) {
		long timestamp = new Date().getTime();
		return String.format(PATTERN, parameters.getAc(), parameters.getDs(), parameters.getAmountWrite(), timestamp);
	}

	@Override
	protected String getTopicName() {
		return "dataWrite";
	}

}