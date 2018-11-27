package eu.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if a datasource ID could not be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNotFoundException extends RuntimeException {

	/**
	 * Creates an instance using the given datasource ID.
	 */
	public IdNotFoundException(long id) {
		super("Datasource with ID " + id + " does not exist");
	}
}
