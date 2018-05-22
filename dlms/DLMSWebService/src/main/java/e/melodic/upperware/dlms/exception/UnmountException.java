package e.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for the unmount command.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnmountException extends RuntimeException {

    /**
     * Creates an instance using the given message.
     */
    public UnmountException(String message) {
        super(message);
    }
}
