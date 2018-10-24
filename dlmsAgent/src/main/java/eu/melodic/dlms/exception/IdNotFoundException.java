package eu.melodic.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if an event ID could not be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNotFoundException extends RuntimeException {

    /**
     * Creates an instance using the given event ID.
     */
    public IdNotFoundException(long id) {
        super("Datasource with ID " + id + " does not exist");
    }
}
