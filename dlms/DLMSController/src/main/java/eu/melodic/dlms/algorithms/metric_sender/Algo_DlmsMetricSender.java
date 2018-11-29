package eu.melodic.dlms.algorithms.metric_sender;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class Algo_DlmsMetricSender {
	protected String jmsServerAddress;
	protected String jmsServerPort;
	
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	protected ActiveMQConnectionFactory activeMQConnectionFactory;

	/**
	 * Starting class
	 */
	public abstract int run() throws Exception;
	
	/**
	 * Send one message to metric generator
	 */
	protected abstract void sendOneMessage(Object... parameters) throws Exception;
	
	/**
	 * Start the connection to server
	 */
	protected Connection startConnection() throws JMSException {
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		log.debug("Connection started");
		return connection;
	}
	
	/**
	 * Stop the connection to server
	 */
	protected void stopConnection(Connection connection) throws Exception {
		if (connection != null) {
			connection.close();
		}
		log.debug("Connection closed");
	}
}
