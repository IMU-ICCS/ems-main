package eu.melodic.dlms.algorithms.metric_sender;

import java.util.Date;

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
 * Class to randomly generate data write between application component and data
 * source Send to metric generator This class is only for TEST and should be
 * commented in production
 */
@Getter
@Setter
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataWrite extends Algo_DlmsMetricSender {
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
		this.activeMQConnectionFactory = new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
		sendOneMessage(ac, ds, amountWrite);
		log.info("Algo_DlmsMetricSender_AcDsDataWrite has successfully executed");
		return 0;
	}

	@Override
	protected void sendOneMessage(Object... parameters) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		long timestamp = new Date().getTime();
		String topicStr = "dataWrite";
		String message = String.format(PATTERN, parameters[0], parameters[1], parameters[2], timestamp);
		Topic topic = session.createTopic(topicStr);
		log.debug("Message: {}", message);
		Message msg = session.createTextMessage(message);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);

		stopConnection(connection);
	}

}
