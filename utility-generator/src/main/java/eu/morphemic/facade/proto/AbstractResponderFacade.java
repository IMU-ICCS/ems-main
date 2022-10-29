package eu.morphemic.facade.proto;

import com.google.protobuf.GeneratedMessageV3;
import eu.morphemic.facade.AbstractFacade;
import lombok.extern.slf4j.Slf4j;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.Map;

@Slf4j
public abstract class AbstractResponderFacade<Q extends GeneratedMessageV3, Y extends GeneratedMessageV3> extends AbstractFacade<Q, Y> {

	private final Class<Q> requestClass;
	private final Class<Y> replyClass;

	protected AbstractResponderFacade(String requestTopic, String replyTopic, Class<Q> requestClass, Class<Y> replyClass) {
		super(requestTopic, replyTopic);
		this.requestClass = requestClass;
		this.replyClass = replyClass;
	}

	public void listenForRequests() {
		Connection connection = null;
		Session session = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("!!!!!!!!  Setting up responding side...");

			Topic requestTopic = createTopic(getRequestTopic(), session);
			MessageConsumer consumer = createConsumer(requestTopic, session);

			runMessageCycle(session, consumer);
		}
		catch (JMSException e) {
			log.error("JMS communication failed: {}", e.getMessage(), e);
		}
		finally {
			closeSession(session);
			closeConnection(connection);
		}
	}

	private void runMessageCycle(Session session, MessageConsumer consumer) throws JMSException {
		Q request = listenForRequestMessage(consumer);

		Map<String, Object> result = callExternal(request);

		createAndSendReplyMessage(session, result);
		runMessageCycle(session, consumer);
	}

	protected Q listenForRequestMessage(MessageConsumer consumer) throws JMSException {
		log.info("!!!!!!!!  Listening to topic: {}", getRequestTopic());
		BytesMessage requestMessage = (BytesMessage) consumer.receive();
		byte[] bytes = getBytesFromMessage(requestMessage);
		ReflectionHelper<Q, GeneratedMessageV3.Builder> requestHelper = new ReflectionHelper<>(requestClass);
		Q request = requestHelper.parseFromBytes(bytes);
		log.info("!!!!!!!!  Message received:\n{}", request);
		return request;
	}

	protected void createAndSendReplyMessage(Session session, Map<String, Object> result) throws JMSException {
		ReflectionHelper<Y, GeneratedMessageV3.Builder> responseHelper = new ReflectionHelper<>(replyClass);
		Y.Builder responseBuilder = responseHelper.createBuilderInstance();
		responseHelper.prepareBuilder(responseBuilder, result);
		Y response = (Y) responseBuilder.build();

		BytesMessage replyMessage = createResponseBytesMessage(response, session);
		log.info("!!!!!!!!  Sending to topic: {}", getReplyTopic());
		log.info("!!!!!!!!  Message: \n{}", response);
		Topic replyTopic = createTopic(getReplyTopic(), session);
		sendMessage(replyMessage, replyTopic, session);
		log.info("!!!!!!!!  Message sent");
	}

	public abstract Map<String, Object> callExternal(Q data);

}
