package eu.melodic.dlms.algorithms.metric_sender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import eu.melodic.dlms.algorithms.utility.RandomGenerator;
import lombok.AccessLevel;
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
public class Algo_DlmsMetricSender_DataCenter {
	// configuration parameter
	private String jmsServerAddress;
	private String jmsServerPort;
	private int bestLatency;
	private int worstLatency;
	private int bestBandwidth;
	private int worstBandwidth;
//	private int numRecordGenerate = 0;

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private ActiveMQConnectionFactory activeMQConnectionFactory;

	public int run() throws Exception {
		int latencyVal = RandomGenerator.generateNum(worstLatency, bestLatency);
		int bandwidthVal = RandomGenerator.generateNum(worstBandwidth, bestBandwidth);
		List<String> dcKeysList = new ArrayList<>(DC_Region.MYHASH.keySet());
		String dataCenter1 = dcKeysList.get(RandomGenerator.randNumFromSize(dcKeysList.size()));
		String region1 = DC_Region.MYHASH.get(dataCenter1);

		String dataCenter2;
		do {
			// choose datacenter different than datacenter1
			dataCenter2 = dcKeysList.get(RandomGenerator.randNumFromSize(dcKeysList.size()));
		} while (dataCenter1.equals(dataCenter2));
		String region2 = DC_Region.MYHASH.get(dataCenter2);

		String cloudProvider1 = "AWS"; // use default value currently
		String cloudProvider2 = "AWS"; // use default value currently

		this.activeMQConnectionFactory = new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
		sendOneMessage(cloudProvider1, dataCenter1, region1, cloudProvider2, dataCenter2, region2, latencyVal,
				bandwidthVal);
		log.info("Algo_DlmsMetricSender has successfully executed");
		return 0;
	}

	private void sendOneMessage(String cloudProvider1, String dataCenter1, String region1, String cloudProvider2,
			String dataCenter2, String region2, int latencyVal, int bandwidthVal) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		long timestamp = new Date().getTime();
		String topicStr = "dataCenterConnection";
		String message = "{\"cloudProvider1\":\"" + cloudProvider1 + "\" ,  \"dataCenter1\": \"" + dataCenter1
				+ "\", \"region1\":\"" + region1 + "\",\"cloudProvider2\":\"" + cloudProvider2
				+ "\" , \"dataCenter2\": \"" + dataCenter2 + "\", \"region2\":\"" + region2 + "\", \"latencyVal\": \""
				+ latencyVal + "\", \"bandwidthVal\": \"" + bandwidthVal + "\",  \"timeStamp\": \"" + timestamp
				+ "\" }";
		Topic topic = session.createTopic(topicStr);
		log.debug("Message: {}", message);
		Message msg = session.createTextMessage(message);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);

		stopConnection(connection);
	}

	private Connection startConnection() throws JMSException {
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		log.debug("Connection started");
		return connection;
	}

	private void stopConnection(Connection connection) throws Exception {
		if (connection != null) {
			connection.close();
		}
		log.debug("Connection closed");
	}

}
