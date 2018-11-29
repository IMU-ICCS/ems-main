package eu.melodic.dlms.algorithms.metric_sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import eu.melodic.dlms.algorithms.utility.RandomGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * Class to randomly generate latency, bandwidth between two data center metric generator 
 * This class is only for TEST and should be commented in production
 */
@Getter
@Setter
@Slf4j
public class Algo_DlmsMetricSender_DataCenter extends Algo_DlmsMetricSender {
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

		this.activeMQConnectionFactory = new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
		sendOneMessage(cloudProvider1, cp1Public, dataCenter1, region1, cloudProvider2, cp2Public, dataCenter2, region2,
				latencyVal, bandwidthVal);
		log.info("Algo_DlmsMetricSender has successfully executed");
		return 0;
	}

	@Override
	protected void sendOneMessage(Object... parameters) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		long timestamp = new Date().getTime();
		String topicStr = "dataCenterConnection";
		String message = String.format(PATTERN, parameters[0], parameters[1], parameters[2], parameters[3], parameters[4], parameters[5], parameters[6], parameters[7], parameters[8], parameters[9], timestamp);
		Topic topic = session.createTopic(topicStr);
		log.debug("Message: {}", message);
		Message msg = session.createTextMessage(message);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);

		stopConnection(connection);
	}

}
