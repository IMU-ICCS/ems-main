package eu.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for the copy command.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CopyException extends RuntimeException {

    /**
     * Creates an instance using the given message.
     */
    public CopyException(String message) {
        super(message);
    }
}
