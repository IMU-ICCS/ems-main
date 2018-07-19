package eu.melodic.dlms;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used if no collection of metrics is present.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMetricsException extends RuntimeException {

    public NoMetricsException() {
        super("No metrics present");
    }
}
