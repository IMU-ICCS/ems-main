package eu.morphemic.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.codehaus.plexus.util.StringUtils;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public abstract class AbstractFacade<Q extends GeneratedMessageV3, Y extends GeneratedMessageV3> {

	private static final String BROKER_PROPERTIES_CONFIGURATION_FILE_LOCATION__SYS_PROPERTY = "broker_properties_configuration_file_location";
	private static final String JMS_URL__SYS_PROPERTY = "jmsUrl";
	private static final String JMS_USER__SYS_PROPERTY = "jmsUser";
	private static final String JMS_PW__SYS_PROPERTY = "jmsPw";

	private final String requestTopic;
	private final String replyTopic;

	private final String jmsUrl;
	private final InetAddress localhost;

	private final Properties brokerClientProperties;

	private int idCounter = 0;

	protected AbstractFacade(String requestTopic, String replyTopic) {
		this.requestTopic = requestTopic;
		this.replyTopic = replyTopic;
		brokerClientProperties = initializeBrokerClientProperties();
		jmsUrl = initializeJmsServerUrl();
		localhost = initializeLocalhost();
	}

	private InetAddress initializeLocalhost() {
		try {
			return InetAddress.getLocalHost();
		}
		catch(UnknownHostException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	protected String generateNextId() {
		return localhost.getHostAddress() + "-" + new Date().getTime() + "-" + ++idCounter; // TODO how save is this? For port we need Spring...
	}

	protected void sendMessage(Message message, Destination destination, Session session) throws JMSException {
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.send(message);
		producer.close();
	}

	protected byte[] getBytesFromMessage(BytesMessage message) throws JMSException {
		long length = message.getBodyLength();
		byte[] bytes = new byte[(int) length];
		message.readBytes(bytes);
		return bytes;
	}

	protected BytesMessage createRequestBytesMessage(Q bean, Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bean.toByteArray());
		return message;
	}

	protected BytesMessage createResponseBytesMessage(Y bean, Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bean.toByteArray());
		return message;
	}

	protected TextMessage createJsonTextMessage(Map<String, Object> data, Session session) throws JMSException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(data);
			log.info("Created JSON string: {}", json);
			return session.createTextMessage(json);
		} catch (JsonProcessingException e) {
			log.error("JSON encoding failed: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> readJsonTextMessage(TextMessage message) throws JMSException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = message.getText();
			log.info("Received JSON string: {}", json);
			return	objectMapper.readValue(json, HashMap.class);
		} catch (JsonProcessingException e) {
			log.error("JSON decoding failed: {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	protected Topic createTopic(String name, Session session) throws JMSException {
		return session.createTopic(name);
	}

	protected Queue createQueue(String name, Session session) throws JMSException {
		return session.createQueue(name);
	}

	protected MessageConsumer createConsumer(Destination destination, Session session) throws JMSException {
		return session.createConsumer(destination);
	}

	protected Connection initializeConnection() throws JMSException {
		String user = initializeJmsUser();
		String pw = initializeJmsPw();

		ConnectionFactory activeMQConnectionFactory;
		if(!user.isEmpty() && !pw.isEmpty()) {
			activeMQConnectionFactory = new ActiveMQConnectionFactory(user, pw, jmsUrl);
		}
		else {
			activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsUrl);
		}
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		return connection;
	}

	protected Session initializeSession(Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	protected void closeSession(Session session) {
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				log.error("Closing JMS session failed: {}", e.getMessage(), e);
			}
		}
	}

	protected void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				log.error("Closing JMS connection failed: {}", e.getMessage(), e);
			}
		}
	}

	public String getRequestTopic() {
		return requestTopic;
	}

	public String getReplyTopic() {
		return replyTopic;
	}

	private Properties initializeBrokerClientProperties() {
		String brokerPropertiesPath = System.getProperties().getProperty(BROKER_PROPERTIES_CONFIGURATION_FILE_LOCATION__SYS_PROPERTY);
		if(brokerPropertiesPath == null || brokerPropertiesPath.trim().isEmpty()) {
			log.warn("System property with path for BrokerClient properties file is not set or empty");
			return null;
		}

		try (InputStream in = new FileInputStream(brokerPropertiesPath)) {
			final Properties p = new Properties();
			p.load(in);
			log.info("Successfully loaded BrokerClient properties file from {}", brokerPropertiesPath);
			return p;
		}
		catch(IOException e) {
			log.warn("No BrokerClient properties file found at the expected location: {}", brokerPropertiesPath);
			return null;
		}
	}

	private String initializeJmsServerUrl() {
		if(brokerClientProperties == null) {
			String jmsUrl = System.getProperties().getProperty(JMS_URL__SYS_PROPERTY);

			if (StringUtils.isBlank(jmsUrl)) {
				throw new IllegalArgumentException("No URL for JMS server set.");
			}
			return jmsUrl;
		}
		return brokerClientProperties.getProperty("brokerclient.broker-url");
	}

	private String initializeJmsUser() {
		if(brokerClientProperties == null) {
			String user = System.getProperties().getProperty(JMS_USER__SYS_PROPERTY);
			return user == null ? "" : user;
		}
		return brokerClientProperties.getProperty("brokerclient.broker-username");
	}

	private String initializeJmsPw() {
		if(brokerClientProperties == null) {
			String pw = System.getProperties().getProperty(JMS_PW__SYS_PROPERTY);
			return pw == null ? "" : pw;
		}
		return brokerClientProperties.getProperty("brokerclient.broker-password");
	}
}
