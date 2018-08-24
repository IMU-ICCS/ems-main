package eu.melodic.dlms;

/**
 * Enum representing an event status.
 * This can be idle (not run yet, ready to start), running, finished (run successfully) or error (run but did not finish successfully).
 */
public enum EventStatus {

	IDLE, RUNNING, FINISHED, ERROR;

}
