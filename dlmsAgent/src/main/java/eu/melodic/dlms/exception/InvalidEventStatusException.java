package eu.melodic.dlms.exception;

import eu.melodic.dlms.Event;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if an event's status is not allowed for the requested action.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidEventStatusException extends RuntimeException {

	/**
	 * Creates an instance using the given event ID.
	 */
	public InvalidEventStatusException(Event event) {
		super("Invalid event status " + event.getStatus());
	}
}
