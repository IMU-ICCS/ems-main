package eu.morphemic.facade;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Abstract base class for facade requesting side. This provides convenience methods and dictates the workflow in sendRequest().
 */
@Slf4j
public abstract class AbstractTextRequesterFacade extends AbstractFacade {

	public static final String SENDER_ID_PROPERTY_NAME = "sender_id";

	protected AbstractTextRequesterFacade(String requestTopic, String replyTopic) {
		super(requestTopic, replyTopic);
	}

	/**
	 * Sends a request on the set request topic and awaits an answer on the reply topic.
	 * @param data Map with parameters to be included in the request. Field names in the message will equal the map keys, all map entries will be sent.
	 * @param timeoutSeconds - timeout given in seconds to wait for the reply message. If the call takes longer the method will return without result. Default is 60 secs.
	 * @return Map containing the received fields from the message. All fields from the reply message will be in the map using the same field names.
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public Map<String, Object> sendRequestAndAwaitReply(Map<String, Object> data, int timeoutSeconds) {
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("Setting up requesting side...");

			String id = generateNextId();
			data.put(SENDER_ID_PROPERTY_NAME, id);

			createAndSendRequestMessage(data, session);

			Destination replyTopic = createTopic(getReplyTopic(), session);
			consumer = createConsumer(replyTopic,session);

			Map<String, Object> result = listenForReplyMessage(consumer, id, timeoutSeconds);
			consumer.close();
			return result;
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
	 * Sends a request on the set request topic without waiting for an answer.
	 * @param data Map with parameters to be included in the request. Field names in the message will equal the map keys, all map entries will be sent.
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public void sendRequest(Map<String, Object> data) {
		Connection connection = null;
		Session session = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("Setting up requesting side...");

			String id = generateNextId();
			data.put(SENDER_ID_PROPERTY_NAME, id);

			createAndSendRequestMessage(data, session);
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
	 * Listens on the reply topic (blocking) for a message and returns the data received.
	 * @param id sender-ID needed to filter for the correct message inside the topic.
	 * @param timeoutSeconds - timeout given in seconds to wait for the reply message. If the call takes longer the method will return without result. Default is 60 secs.
	 * @return Map containing the received fields from the message. All fields from the reply message will be in the map using the same field names.
	 * @throws RuntimeException In case of errors during JMS communication.
	 */
	public Map<String, Object> listenForReplyMessage(String id, int timeoutSeconds) throws JMSException {
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);

			Destination replyTopic = createTopic(getReplyTopic(), session);
			consumer = createConsumer(replyTopic,session);

			Map<String, Object> result = listenForReplyMessage(consumer, id, timeoutSeconds);
			consumer.close();
			return result;
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
	 * Creates and sends a JSON-text message on the request topic.
	 * @param data Map with parameters to be included in the request. Field names in the message will equal the map keys, all map entries will be sent.
	 * @param session Session to be used.
	 * @throws JMSException In case of errors during JMS communication.
	 */
	protected void createAndSendRequestMessage(Map<String, Object> data, Session session) throws JMSException {
		TextMessage requestMessage = createJsonTextMessage(data, session);
		log.info("Sending to topic: {}", getRequestTopic());
		log.info("Message: \n{}", requestMessage.getText());
		Destination requestTopic = createTopic(getRequestTopic(), session);
		sendMessage(requestMessage, requestTopic, session);
		log.info("Message sent");
	}

	/**
	 * Listens for JSON-text messages on the reply topic (blocking). <br>
	 * If an id is used, it is check vs. the message and if it does not match, the message is ignored and listening continues.
	 * @param consumer MessageConsumer to be used (created outside to be reusable and closable from there).
	 * @param id Sender-ID - provide ID if not every reply might be for this client. Set null or empty if not needed.
	 * @param timeoutSeconds - timeout given in seconds to wait for the reply message. If the call takes longer the method will return without result. Default is 60 secs.
	 * @return Map containing the received fields from the message. All fields from the reply message will be in the map using the same field names.
	 * @throws JMSException In case of errors during JMS communication.
	 */
	protected Map<String, Object> listenForReplyMessage(MessageConsumer consumer, String id, int timeoutSeconds) throws JMSException {
		int timeoutSec = (timeoutSeconds <= 0) ? 60 : timeoutSeconds;

		log.info("Listening to topic: {}", getReplyTopic());
		TextMessage replyMessage = null;

		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<TextMessage> task = () -> (TextMessage) consumer.receive();

		Future<TextMessage> future = executor.submit(task);
		try {
			replyMessage = future.get(timeoutSec, TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			log.warn("Timeout hit after {} seconds - no result received, continuing", timeoutSec);
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage(), e);
		} finally {
			future.cancel(true);
		}

		if(replyMessage == null) {
			return Collections.emptyMap();
		}

		Map<String,Object> result = readJsonTextMessage(replyMessage);
		log.info("Message received:\n{}", result.toString());

		String replyId = (String) result.get(SENDER_ID_PROPERTY_NAME);
		if(isIdPresent(id) && id.equals(replyId)) {
			return result;
		}
		else {
			log.info("ID '{}' from reply does not match '{}' --> continuing to wait", replyId, id);
			return listenForReplyMessage(consumer, id, timeoutSeconds);
		}
	}

	private boolean isIdPresent(String id) {
		return id != null && !id.trim().isEmpty();
	}
}
