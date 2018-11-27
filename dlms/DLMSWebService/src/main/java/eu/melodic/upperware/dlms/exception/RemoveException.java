package eu.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for the remove command.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RemoveException extends RuntimeException {

	/**
	 * Creates an instance using the given message.
	 */
	public RemoveException(String message) {
		super(message);
	}
}
