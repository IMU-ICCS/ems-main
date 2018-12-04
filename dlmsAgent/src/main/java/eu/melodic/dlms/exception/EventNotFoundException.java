package eu.melodic.dlms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if an event could not be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventNotFoundException extends RuntimeException {

	/**
	 * Creates an instance using the given event name.
	 */
	public EventNotFoundException(String eventName) {
		super("Event with name " + eventName + " could not be found");
	}
}
