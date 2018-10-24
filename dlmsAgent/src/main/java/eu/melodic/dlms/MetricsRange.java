package eu.melodic.dlms;

/**
 * Enum representing a range of metrics that is to be collected by the program.
 * This can be either Alluxio-only, MySQL-only or both.
 */
public enum MetricsRange {

	ALLUXIO, MY_SQL, ALL;

}
