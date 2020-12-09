package eu.melodic.upperware.adapter.exception;

public class ProactiveClientException extends RuntimeException {
    public ProactiveClientException(String message) {
        super(message);
    }

    public ProactiveClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
