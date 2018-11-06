package eu.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if a datasource ID could not be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NameNotFoundException extends RuntimeException {

	/**
	 * Creates an instance using the given datasource ID.
	 */
	public NameNotFoundException(String name) {
		super("Datasource with Name " + name + " does not exist");
	}
}
