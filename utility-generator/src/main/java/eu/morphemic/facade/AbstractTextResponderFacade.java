package eu.morphemic.facade;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.Map;

/**
 * Abstract base class for facade responding side. This provides convenience methods and dictates the workflow in listenForRequests().
 */
@Slf4j
public abstract class AbstractTextResponderFacade extends AbstractFacade {

	protected AbstractTextResponderFacade(String requestTopic, String replyTopic) {
		super(requestTopic, replyTopic);
	}

	/**
	 * Listens for messages on the request topic (blocking), runs callExternalWithResult() on received data and returns the result on the reply topic.
	 * This will run recursively!
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public void listenForRequestsAndDoReply() {
		Connection connection = null;
		Session session = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("Setting up responding side...");

			Destination requestTopic = createTopic(getRequestTopic(), session);
			MessageConsumer consumer = createConsumer(requestTopic, session);

			runMessageCycleWithReply(consumer, session);
		}
		catch (JMSException e) {
			log.error("JMS communication failed: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally {
			closeSession(session);
			closeConnection(connection);
		}
	}

	/**
	 * Listens for messages on the request topic (blocking), runs callExternal() on received data and returns the result on the reply topic.
	 * This will run recursively!
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public void listenForRequests() {
		Connection connection = null;
		Session session = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("Setting up responding side...");

			Destination requestTopic = createTopic(getRequestTopic(), session);
			MessageConsumer consumer = createConsumer(requestTopic, session);

			runMessageCycle(consumer);
		}
		catch (JMSException e) {
			log.error("JMS communication failed: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally {
			closeSession(session);
			closeConnection(connection);
		}
	}

	/**
	 * Sends a message on the set reply topic.
	 * @param data Map with parameters to be included in the request. Field names in the message will equal the map keys, all map entries will be sent.
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public void sendReply(Map<String, Object> data) {
		Connection connection = null;
		Session session = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("Setting up reply...");

			createAndSendReplyMessage(data, session);
		}
		catch (JMSException e) {
			log.error("JMS communication failed: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally {
			closeSession(session);
			closeConnection(connection);
		}
	}

	private void runMessageCycleWithReply(MessageConsumer consumer, Session session) throws JMSException {
		Map<String,Object> request = listenForRequestMessage(consumer);

		Map<String, Object> result = callExternalWithResult(request);

		createAndSendReplyMessage(result, session);
		runMessageCycleWithReply(consumer, session); // recursive call
	}

	private void runMessageCycle(MessageConsumer consumer) throws JMSException {
		Map<String,Object> request = listenForRequestMessage(consumer);

		callExternal(request);

		runMessageCycle(consumer); // recursive call
	}

	/**
	 * Listens for JSON-text messages on the request topic (blocking).
	 * @param consumer MessageConsumer to be used (created outside to be reusable and closable from there).
	 * @return Map containing the received fields from the message. All fields from the reply message will be in the map using the same field names.
	 * @throws JMSException In case of errors during JMS communication.
	 */
	protected Map<String,Object> listenForRequestMessage(MessageConsumer consumer) throws JMSException {
		log.info("Listening to topic: {}", getRequestTopic());
		TextMessage replyMessage = (TextMessage) consumer.receive();
		Map<String,Object> request =	readJsonTextMessage(replyMessage);
		log.info("Message received:\n{}", request.toString());
		return request;
	}

	/**
	 * Creates and send a JSON-text message on the reply topic.
	 * @param data Map with parameters to be included in the request. Field names in the message will equal the map keys, all map entries will be sent.
	 * @param session Session to be used.
	 * @throws JMSException In case of errors during JMS communication.
	 */
	protected void createAndSendReplyMessage(Map<String, Object> data, Session session) throws JMSException {
		TextMessage replyMessage = createJsonTextMessage(data, session);
		log.info("Sending to topic: {}", getReplyTopic());
		log.info("Message: \n{}", replyMessage.getText());
		Topic replyTopic = createTopic(getReplyTopic(), session);
		sendMessage(replyMessage, replyTopic, session);
		log.info("Message sent");
	}

	// TODO make double-abstract somehow nicer
	/**
	 * Method to call external code outside of the facade returning the result of the external call.
	 * @param data Map with parameters coming from message received by the facade.
	 * @return Map with parameters returned to the facade to be sent via message.
	 */
	public abstract Map<String, Object> callExternalWithResult(Map<String, Object> data);

	/**
	 * Method to call external code outside of the facade without retuning the result.
	 * @param data Map with parameters coming from message received by the facade.
	 */
	public abstract void callExternal(Map<String, Object> data);
}
