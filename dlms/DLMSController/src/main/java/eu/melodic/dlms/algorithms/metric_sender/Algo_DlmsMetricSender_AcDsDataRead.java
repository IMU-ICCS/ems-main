package eu.melodic.dlms.algorithms.metric_sender;

import java.util.Date;

import eu.melodic.dlms.algorithms.extra.RandomGenerator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to randomly generate data read between application component and data source
 * Send to metric generator 
 * This class is only for TEST and should be commented in production
 */

@Setter
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataRead extends Algo_DlmsMetricSender<AcDsDataReadPojo>  {
	// Configuration parameters
	private int numAC;
	private int numDS;
	private long bestDataRead;
	private long worstDataRead;

	// Pattern how the message should be sent as
	private final String PATTERN = "{\"ac\":\"%d\" , \"ds\":\"%d\" , \"amountRead\":\"%d\" , \"timeStamp\":\"%d\"}";

	@Override
	public int run() throws Exception {
		long ac = RandomGenerator.generateNum(1, numAC);
		long ds = RandomGenerator.generateNum(1, numDS);
		long amountRead = RandomGenerator.generateNum(worstDataRead, bestDataRead);

		AcDsDataReadPojo parameters = AcDsDataReadPojo
				.builder()
				.ac(ac)
				.ds(ds)
				.amountRead(amountRead)
				.build();

		sendOneMessage(parameters);
		log.info("Algo_DlmsMetricSender_AcDsDataRead has successfully executed");
		return 0;
	}

	@Override
	protected String getMessage(AcDsDataReadPojo parameters) {
		long timestamp = new Date().getTime();
		return String.format(PATTERN, parameters.getAc(), parameters.getDs(), parameters.getAmountRead(), timestamp);
	}

	@Override
	protected String getTopicName() {
		return "dataRead";
	}

}