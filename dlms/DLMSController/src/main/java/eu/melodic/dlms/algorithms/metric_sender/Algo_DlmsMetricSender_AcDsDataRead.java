package eu.melodic.dlms.algorithms.metric_sender;

import java.util.Date;

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
 * Class to randomly generate data read between application component and data source 
 * Send to metric generator
 * This class is only for TEST and should be commented in production
 */
@Getter
@Setter
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataRead {
	// configuration parameters
	private String jmsServerAddress;
	private String jmsServerPort;
	private int numAC;
	private int numDS;
	private long bestDataRead;
	private long worstDataRead;

	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	private ActiveMQConnectionFactory activeMQConnectionFactory;

	public int run() throws Exception {
		long ac = RandomGenerator.generateNum(1, numAC);
		long ds = RandomGenerator.generateNum(1, numDS);

		long amountRead = RandomGenerator.generateNum(worstDataRead, bestDataRead);

		this.activeMQConnectionFactory = new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
		sendOneMessage(ac, ds, amountRead);
		log.info("Algo_DlmsMetricSender_AcDsDataRead has successfully executed");
		return 0;
	}

	private void sendOneMessage(long ac, long ds, long amountRead) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		long timestamp = new Date().getTime();
		String topicStr = "dataRead";
		String message = "{\"ac\":\"" + ac + "\" ,  \"ds\": \"" + ds + "\", \"amountRead\":\"" + amountRead
				+ "\",  \"timeStamp\":\"" + timestamp + "\" }";
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
