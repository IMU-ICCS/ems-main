package eu.melodic.dlms.algorithms.metric_sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.melodic.dlms.algorithms.utility.RandomGenerator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * Class to randomly generate latency, bandwidth between two data center metric generator 
 * This class is only for TEST and should be commented in production
 */

@Setter
@Slf4j
public class Algo_DlmsMetricSender_DataCenter extends Algo_DlmsMetricSender<DataCenterPojo> {
	// Configuration parameter
	private int bestLatency;
	private int worstLatency;
	private int bestBandwidth;
	private int worstBandwidth;

	// Pattern how the message should be sent
	private final String PATTERN = "{\"cloudProvider1\":\"%s\" , \"isCp1Public\":\"%b\" , \"dataCenter1\":\"%s\" , \"region1\":\"%s\", \"cloudProvider2\":\"%s\", \"isCp2Public\":\"%b\" , \"dataCenter2\":\"%s\" , \"region2\":\"%s\" , \"latencyVal\":\"%d\" , \"bandwidthVal\":\"%d\" , \"timeStamp\":\"%d\"}";

	@Override
	public int run() throws Exception {
		int latencyVal = RandomGenerator.generateNum(worstLatency, bestLatency);
		int bandwidthVal = RandomGenerator.generateNum(worstBandwidth, bestBandwidth);
		List<String> dcKeysList = new ArrayList<>(DC_Region.MYHASH.keySet());
		String dataCenter1 = dcKeysList.get(RandomGenerator.randNumFromSize(dcKeysList.size()));
		String region1 = DC_Region.MYHASH.get(dataCenter1);

		String dataCenter2;
		do {
			// Choose datacenter different than datacenter1
			dataCenter2 = dcKeysList.get(RandomGenerator.randNumFromSize(dcKeysList.size()));
		} while (dataCenter1.equals(dataCenter2));
		String region2 = DC_Region.MYHASH.get(dataCenter2);

		// use default value currently
		String cloudProvider1 = "AWS";
		String cloudProvider2 = "AWS";
		boolean cp1Public = true;
		boolean cp2Public = true;

		DataCenterPojo dataCenterPojo = DataCenterPojo
				.builder()
				.dataCenter1(DataCenterPojo.DataCenter.builder().cloudProvider(cloudProvider1).cpPublic(cp1Public).dataCenter(dataCenter1).region(region1).build())
				.dataCenter2(DataCenterPojo.DataCenter.builder().cloudProvider(cloudProvider2).cpPublic(cp2Public).dataCenter(dataCenter2).region(region2).build())
				.bandwidthVal(bandwidthVal)
				.latencyVal(latencyVal)
				.build();

		sendOneMessage(dataCenterPojo);
		log.info("Algo_DlmsMetricSender has successfully executed");
		return 0;
	}

	@Override
	protected String getMessage(DataCenterPojo dataCenterPojo) {
		long timestamp = new Date().getTime();
		DataCenterPojo.DataCenter dc1 = dataCenterPojo.getDataCenter1();
		DataCenterPojo.DataCenter dc2 = dataCenterPojo.getDataCenter2();

		return String.format(PATTERN, dc1.getCloudProvider(), dc1.isCpPublic(), dc1.getDataCenter(), dc1.getRegion(),
				dc2.getCloudProvider(), dc2.isCpPublic(), dc2.getDataCenter(), dc2.getRegion(), dataCenterPojo.getLatencyVal(), dataCenterPojo.getBandwidthVal(), timestamp);
	}

	@Override
	protected String getTopicName() {
		return "dataCenterConnection";
	}

}