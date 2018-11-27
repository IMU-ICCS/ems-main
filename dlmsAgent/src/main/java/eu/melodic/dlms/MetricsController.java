package eu.melodic.dlms;

import java.util.Date;
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
	public void sendMetrics() {
		Connection connection = null;
		try {
			connection = initializeConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			if (hasAlluxioMetrics()) {
				log.info("Alluxio metrics found");
				Map<String, Object> gaugesMap = metrics.getGauges().getProperties();
				Map<String, Object> countersMap = metrics.getCounters().getProperties();

				extractAndSendMetrics(gaugesMap, "value", session);
				extractAndSendMetrics(countersMap, "count", session);
			}

			if (!mySqlMetrics.isEmpty()) {
				log.info("MySQL metrics found");
				extractAndSendMetrics(mySqlMetrics, null, session);
			}
		} catch (JMSException e) {
			log.error("JMS sending failed: {}", e.getMessage(), e);
		} finally {
			closeConnection(connection);
		}
	}

	private boolean hasAlluxioMetrics() {
		return metrics != null
				&& (!metrics.getGauges().getProperties().isEmpty() || !metrics.getCounters().getProperties().isEmpty());
	}

	private void extractAndSendMetrics(Map<String, Object> map, String propertyName, Session session)
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
