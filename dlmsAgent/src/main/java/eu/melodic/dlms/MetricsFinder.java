package eu.melodic.dlms;

import java.util.Map;

import eu.melodic.dlms.data.Counters;
import eu.melodic.dlms.data.Gauges;
import eu.melodic.dlms.data.Metrics;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class to filter a specific metric from the collection of metrics.
 */
@NoArgsConstructor
@Slf4j
public final class MetricsFinder {

	/**
	 * Searches for the metric with the given metricName in the collection of
	 * metrics (parameter metrics) and returns the stored value if successful. If
	 * the metric does not exist it returns null.
	 */
	public static Object findMetric(Metrics metrics, String metricName) {
		Gauges gauges = metrics.getGauges();
		Object value = findValueInGauges(gauges, metricName);
		log.info("found value for '{}' after Gauges: {}", metricName, value);

		if (value == null) {
			Counters counters = metrics.getCounters();
			value = findValueInCounters(counters, metricName);
			log.info("found value for '{}' after Counters: {}", metricName, value);
		}

		if (value == null) {
			log.info("No data found for {}", metricName);
		}
		return value;
	}

	private static Object findValueInGauges(Gauges gauges, String metricName) {
		return findValueInProperties(gauges.getProperties(), metricName, "value");
	}

	private static Object findValueInCounters(Counters counters, String metricName) {
		return findValueInProperties(counters.getProperties(), metricName, "value");
	}

	private static Object findValueInProperties(Map<String, Object> propertyMap, String metricName,
			String valuePropertyName) {
		if (propertyMap == null || propertyMap.isEmpty()) {
			return null;
		}
		final Object result = propertyMap.get(metricName);
		return result == null ? null : ((Map) result).get(valuePropertyName);
	}

}
