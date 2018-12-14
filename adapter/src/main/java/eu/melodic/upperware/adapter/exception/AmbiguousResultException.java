package eu.melodic.upperware.adapter.exception;

public class AmbiguousResultException extends AdapterException {

    public AmbiguousResultException(String message) {
        super(message);
    }

    public AmbiguousResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
