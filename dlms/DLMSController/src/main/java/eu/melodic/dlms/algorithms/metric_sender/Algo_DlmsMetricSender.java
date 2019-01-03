package eu.melodic.dlms.algorithms.metric_sender;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class Algo_DlmsMetricSender<T> {
	protected String jmsServerAddress;
	protected String jmsServerPort;

	/**
	 * Starting class
	 */
	public abstract int run() throws Exception;

	protected void sendOneMessage(T parameters) throws Exception {
		Connection connection = startConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topic = session.createTopic(getTopicName());
		String message = getMessage(parameters);
		log.debug("Message: {}", message);
		Message msg = session.createTextMessage(message);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);

		stopConnection(connection);
	}

	protected abstract String getMessage(T parameters);

	protected abstract String getTopicName();

	/**
	 * Start the connection to server
	 */
	private Connection startConnection() throws JMSException {
		Connection connection = createActiveMQConnectionFactory().createConnection();
		connection.start();
		log.debug("Connection started");
		return connection;
	}
	
	/**
	 * Stop the connection to server
	 */
	private void stopConnection(Connection connection) throws Exception {
		if (connection != null) {
			connection.close();
		}
		log.debug("Connection closed");
	}

	private ActiveMQConnectionFactory createActiveMQConnectionFactory() {
		return new ActiveMQConnectionFactory(
				this.jmsServerAddress + ":" + this.jmsServerPort);
	}

}