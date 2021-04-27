package eu.passage.upperware.commons.proactive.client;

public class ProactiveClientException extends RuntimeException {
    public ProactiveClientException(String message) {
        super(message);
    }

    public ProactiveClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
