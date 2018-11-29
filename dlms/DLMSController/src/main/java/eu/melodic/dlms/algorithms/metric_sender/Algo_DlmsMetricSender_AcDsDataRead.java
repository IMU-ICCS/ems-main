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
 * Class to randomly generate data read between application component and data source
 * Send to metric generator 
 * This class is only for TEST and should be commented in production
 */
@Getter
@Setter
@Slf4j
public class Algo_DlmsMetricSender_AcDsDataRead extends Algo_DlmsMetricSender  {
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
		this.activeMQConnectionFactory = new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
		sendOneMessage(ac, ds, amountRead);
		log.info("Algo_DlmsMetricSender_AcDsDataRead has successfully executed");
		return 0;
	}

	@Override
	protected void sendOneMessage(Object... parameters) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		long timestamp = new Date().getTime();
		String topicStr = "dataRead";
		String message = String.format(PATTERN, parameters[0], parameters[1], parameters[2], timestamp);
//		String message = "{\"ac\":\"" + ac + "\" ,  \"ds\": \"" + ds + "\", \"amountRead\":\"" + amountRead
//				+ "\",  \"timeStamp\":\"" + timestamp + "\" }";
		Topic topic = session.createTopic(topicStr);
		log.debug("Message: {}", message);
		Message msg = session.createTextMessage(message);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);

		stopConnection(connection);		
	}

}
