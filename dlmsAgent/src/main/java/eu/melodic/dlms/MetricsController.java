package eu.melodic.dlms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import eu.melodic.dlms.data.Metrics;
import eu.melodic.dlms.exception.NoMetricsException;
import lombok.extern.slf4j.Slf4j;

/**
 * Combined collector and store of metrics, controller for the webservice to
 * query metrics and sender to JMS.
 */
@RestController
@Slf4j
public class MetricsController {

	private final JdbcTemplate jdbcTemplate;

	private final MetricsProperties metricsProperties;

	private Metrics metrics = null;

	private final Map<String, Object> mySqlMetrics = new ConcurrentHashMap<>();

	private final String jmsUrl;

	// Store the values to avoid getting cumulative values
	private Map<String, Long> acDsValueMap = new HashMap<>();
	// Pattern how the message should be sent as
	private final String PATTERN_READ = "{\"ac\":\"%s\" , \"ds\":\"%s\" , \"amountRead\":\"%d\" , \"timeStamp\":\"%d\"}";
	private final String PATTERN_WRITE = "{\"ac\":\"%s\" , \"ds\":\"%s\" , \"amountWrite\":\"%d\" , \"timeStamp\":\"%d\"}";

	@Autowired
	public MetricsController(JdbcTemplate jdbcTemplate, MetricsProperties metricsProperties) {
		this.jdbcTemplate = jdbcTemplate;
		this.metricsProperties = metricsProperties;

		jmsUrl = initializeJmsServerUrl();
	}

	/**
	 * Collects Alluxio metrics for the given url and stores them.
	 */
	public void collectAlluxioMetrics(String url) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			ClientHttpResponse response = execution.execute(request, body);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return response;
		});
		metrics = restTemplate.getForObject(url, Metrics.class);
	}

	/**
	 * Webservice method to query for the value of the metric with the given name.
	 * Uses MetricsFinder.findMetric() to get the value and will pass the result of
	 * that search (see there for information on how non-existing metrics are
	 * handled). Will return an error if no metrics have yet been collected or if
	 * the parameter was not set.
	 * 
	 * @see MetricsFinder#findMetric(Metrics, String)
	 */
	@GetMapping("/metric")
	public Object findMetric(String metricName) {
		if (metrics == null) {
			throw new NoMetricsException();
		}
		if (StringUtils.isBlank(metricName)) {
			throw new IllegalArgumentException("metricName must not be empty");
		}

		return MetricsFinder.findMetric(metrics, metricName);
	}

	/**
	 * Connects to the JMS/ActiveMQ-URL declared as system property 'jmsUrl' and
	 * sends every collected metric found as a message to that server (with the
	 * metric's name as topic).
	 */
	public void sendMetrics(String appComp) {
		Connection connection = null;
		try {
			connection = initializeConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			if (hasAlluxioMetrics()) {
				log.debug("Alluxio metrics found");
//				Map<String, Object> gaugesMap = metrics.getGauges().getProperties();
				Map<String, Object> countersMap = metrics.getCounters().getProperties();

				extractSendAlluxioMetrics(countersMap, session, appComp);
			}

			if (!mySqlMetrics.isEmpty()) {
				log.info("MySQL metrics found");
				extractAndSendMetrics(mySqlMetrics, null, session, appComp);
			}
		} catch (JMSException e) {
			log.error("JMS sending failed: {}", e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Extract relevant read and write metrics
	 */
	public void extractSendAlluxioMetrics(Map<String, Object> map, Session session, String appComp) throws JMSException {
		Date now = new Date();
		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			if (isRelevant(entry.getKey())) {
				String ds = getApplicationId(entry.getKey());

				String msgType = getMessageType(entry.getKey());
				String topic = getTopic(msgType);
				long totalVal = getTotalValue(entry.getValue());

//				String acDsKey = "ac:" + applicationId + " ds:" + ds + " " + msgType;
				String acDsKey = "ac:" + appComp + " ds:" + ds + " " + msgType;
				long effectVal = getCurrentValue(acDsKey, totalVal);

				String msg = getMessage(topic, appComp, ds, effectVal, now.getTime());
				log.info(msg);

				sendMessage(topic, msg, session);
				log.debug("Message sent");
			}

		}
	}

	/**
	 * Get the message
	 */
	private String getMessage(String topic, String ac, String ds, long val, long time) {
		if ("dataRead".equalsIgnoreCase(topic))
			return convertMessage(PATTERN_READ, ac, ds, val, time);
		else if ("dataWrite".equalsIgnoreCase(topic))
			return convertMessage(PATTERN_WRITE, ac, ds, val, time);
		return "";
	}

	/**
	 * Convert the message in a particular format
	 */
	private String convertMessage(String pattern, String ac, String ds, long val, long time) {
		return String.format(pattern, ac, ds, val, time);
	}

	/**
	 * Get the value for the metric
	 */
	private long getTotalValue(Object item) {
		String text = String.valueOf(item);
		log.debug("Value follows a particular pattern {}", text);
		// text contains a particular pattern
		String val = text.substring(text.indexOf("{count=") + 7, text.indexOf("}"));

		return Long.parseLong(val);
	}

	/**
	 * Get value for the metrics
	 */
	private long getCurrentValue(String acDsKey, long totalVal) {
		long newVal = 0;
		if (acDsValueMap.containsKey(acDsKey)) {
			long oldVal = acDsValueMap.get(acDsKey);
			newVal = totalVal - oldVal;
		} else {
			newVal = totalVal;
		}
		acDsValueMap.put(acDsKey, totalVal);
		return newVal;
	}

	/**
	 * Get the application id from the message
	 */
	private String getApplicationId(String message) {
		return message.substring(message.lastIndexOf("__") + 2);
	}

	/**
	 * Check if the message needs to be passed to jms queue
	 */
	private boolean isRelevant(String message) {
		return (message.contains("BytesReadPerUfs") || message.contains("BytesWrittenPerUfs"));
	}

	/**
	 * Type of message
	 */
	private String getMessageType(String message) {
		if (message.contains("BytesReadPerUfs"))
			return "amountRead";
		else if (message.contains("BytesWrittenPerUfs"))
			return "amountWrite";
		log.debug("No read or write available");
		return "";
	}

	/**
	 * Topic for the message
	 */
	private String getTopic(String msgType) {
		if ("amountRead".equalsIgnoreCase(msgType))
			return "dataRead";
		else if ("amountWrite".equalsIgnoreCase(msgType))
			return "dataWrite";
		return "";
	}

	private boolean hasAlluxioMetrics() {
		return metrics != null
				&& (!metrics.getGauges().getProperties().isEmpty() || !metrics.getCounters().getProperties().isEmpty());
	}

	private void extractAndSendMetrics(Map<String, Object> map, String propertyName, Session session, String appComp)
			throws JMSException {
		Date now = new Date();

		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			String message = createMessage(entry, propertyName, now);
			log.info(message);

			sendMessage(entry.getKey(), message, session);
			log.info("Message sent");
		}
	}

	private String createMessage(Map.Entry<String, Object> entry, String propertyName, Date now) {
		String metric = entry.getKey();
		Object value = extractValueFromMapEntry(entry, propertyName);
		return metric + ' ' + value + ' ' + now.getTime();
	}

	private Object extractValueFromMapEntry(Map.Entry<String, Object> entry, String propertyName) {
		if (entry.getValue() == null) {
			return null;
		}
		if (propertyName == null) { // property name == null --> MySQL metrics
			return entry.getValue();
		}
		return ((Map) entry.getValue()).get(propertyName); // Alluxio metrics with inner Map that contains the value
	}

	private void sendMessage(String metricName, String message, Session session) throws JMSException {
		Message msg = session.createTextMessage(message);
		Topic topic = session.createTopic(metricName);
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);
	}

	private Connection initializeConnection() throws JMSException {
		ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsUrl);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		return connection;
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				log.error("Closing JMS connection failed: {}", e.getMessage(), e);
			}
		}
	}

	private String initializeJmsServerUrl() {
		String jmsUrl = System.getProperties().getProperty("jmsUrl");

		if (StringUtils.isBlank(jmsUrl)) {
			throw new IllegalArgumentException("No URL for JMS server set.");
		}
		return jmsUrl;
	}

	/**
	 * Collects MySQL metrics and stores them.
	 */
	public void collectMySqlMetrics() {
		mySqlMetrics.clear();

		for (String metric : metricsProperties.getMysql()) {
			String value = readMySqlMetric(metric);
			mySqlMetrics.put(metric, value);
		}
	}

	private String readMySqlMetric(String metricName) {
		List<Map<String, Object>> result = jdbcTemplate.queryForList("SHOW GLOBAL STATUS like '" + metricName + '\'');

		if (CollectionUtils.isNotEmpty(result)) {
			// the metric names should be direct hits if configured correctly, so we expect
			// only one result
			Map<String, Object> row = result.get(0);
			String value = (String) row.get("Value");
			return value;
		}
		return null;
	}

}
