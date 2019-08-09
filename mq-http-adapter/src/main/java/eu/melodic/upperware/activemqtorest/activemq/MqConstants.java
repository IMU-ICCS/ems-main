package eu.melodic.upperware.activemqtorest.activemq;

public class MqConstants {
	public static final String TIMESTAMP = "timestamp";
	public static final String VALUE = "metricValue";
	public static final String LEVEL = "level";
	public static final String VM_NAME = "vmName";
	public static final String TOPIC_PREFIX = "topic://";
	public static final String PRODUCER_HOST ="producer-host";

	public static final String VALUE_SEPARATOR_DEFAULT = "=";
	public static final String VALUE_SEPARATOR_JSON = ":";

	public static final String KEY_VALUE_PAIR_SEPARATOR = ",";

	public static final String ALL_DESTINATIONS = "*";

	public static final String DEFAULT_VALUE_WHEN_EMPTY = "0";

	public static final String META_MESSAGE_IDENTIFIER = "message=";
	public static final String META_TIMESTAMP_IDENTIFIER = ", timestamp=";
}
