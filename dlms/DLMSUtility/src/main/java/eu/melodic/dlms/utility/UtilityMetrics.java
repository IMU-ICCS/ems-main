package eu.melodic.dlms.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean used as data interface between UtilityGenerator and DlmsController to exchange algorithm results.
 *
 * <p><b>TODO: The redundant version in the current DlmsController project is to be removed on integration!</b>
 */
public class UtilityMetrics {

	private Map<String, Double> results = null;

	/**
	 * Exclusive constructor for mapping by the Spring RestTemplate.
	 */
	private UtilityMetrics() {
	}

	/**
	 * Constructor taking a map of Strings (CAMEL IDs) and double values (returned by the algorithms).
	 */
	public UtilityMetrics(Map<String, Double> results) {
		this.results = new HashMap<>(results);
	}

	public Double getResult(String key) {
		return results.get(key);
	}

	public Map<String, Double> getResults() {
		return new HashMap<>(results);
	}
}
