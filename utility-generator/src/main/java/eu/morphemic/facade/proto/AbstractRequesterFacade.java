package eu.morphemic.facade.proto;

import com.google.protobuf.GeneratedMessageV3;
import eu.morphemic.facade.AbstractFacade;
import lombok.extern.slf4j.Slf4j;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.Map;

@Slf4j
public abstract class AbstractRequesterFacade <Q extends GeneratedMessageV3, Y extends GeneratedMessageV3> extends AbstractFacade<Q, Y> {

	private final Class<Q> requestClass;
	private final Class<Y> replyClass;

	protected AbstractRequesterFacade(String requestTopic, String replyTopic, Class<Q> requestClass, Class<Y> replyClass) {
		super(requestTopic, replyTopic);
		this.requestClass = requestClass;
		this.replyClass = replyClass;
	}

	public Y sendRequest(Map<String, Object> data) {
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			connection = initializeConnection();
			session = initializeSession(connection);
			log.info("!!!!!!!!  Setting up requesting side...");

			String id = generateNextId();
			data.put("senderID", id);

			createAndSendRequestMessage(data, session);

			Destination replyTopic = createTopic(getReplyTopic(), session);
			consumer = createConsumer(replyTopic,session);

			Y result = listenForReplyMessage(consumer, id);
			consumer.close();
			return result;
		}
		catch (JMSException e) {
			log.error("JMS communication failed: {}", e.getMessage(), e);
		}
		finally {
			closeSession(session);
			closeConnection(connection);
		}
		// TODO what to return in this case?
		return null;
	}

	protected void createAndSendRequestMessage(Map<String, Object> data, Session session) throws JMSException {
		ReflectionHelper<Q, Q.Builder> requestHelper = new ReflectionHelper<>(requestClass);
		Q.Builder builder = requestHelper.createBuilderInstance();
		requestHelper.prepareBuilder(builder, data);
		Q request = (Q) builder.build();

		Message requestMessage = createRequestBytesMessage(request, session);
		log.info("!!!!!!!!  Sending to topic: {}", getRequestTopic());
		log.info("!!!!!!!!  Message: \n{}", request);
		Destination requestTopic = createTopic(getRequestTopic(), session);
		sendMessage(requestMessage, requestTopic, session);
		log.info("!!!!!!!!  Message sent");
	}

	protected Y listenForReplyMessage(MessageConsumer consumer, String id) throws JMSException {
		log.info("!!!!!!!!  Listening to topic: {}", getReplyTopic());
		BytesMessage replyByteMessage = (BytesMessage) consumer.receive();
		byte[] bytes = getBytesFromMessage(replyByteMessage);
		ReflectionHelper<Y, GeneratedMessageV3.Builder> responseHelper = new ReflectionHelper<>(replyClass);
		Y reply = responseHelper.parseFromBytes(bytes);
		log.info("!!!!!!!!  Message received:\n" + reply);

		String replyId = getIdFromReply(reply);
		if(id.equals(replyId)) {
			return reply;
		}
		else {
			log.info("ID '" + replyId + "' from reply does not match '" + id + "' --> continuing to wait");
			return listenForReplyMessage(consumer, id);
		}
	}

	public abstract String getIdFromReply(Y reply);
}
