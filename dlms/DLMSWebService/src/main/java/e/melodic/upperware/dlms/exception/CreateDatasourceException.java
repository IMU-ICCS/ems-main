package e.melodic.upperware.dlms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for problems during datasource creation.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CreateDatasourceException extends RuntimeException {

    /**
     * Creates an instance using the given message.
     */
    public CreateDatasourceException(String message) {
        super(message);
    }
}
