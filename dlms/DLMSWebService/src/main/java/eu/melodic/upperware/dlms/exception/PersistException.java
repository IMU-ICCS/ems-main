package eu.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for problems while persisting data.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PersistException extends RuntimeException {

    /**
     * Creates an instance using the given message.
     */
    public PersistException(String message) {
        super(message);
    }
}
