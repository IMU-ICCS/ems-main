package eu.melodic.cache.exception;

/**
 * Created by pszkup on 04.01.18.
 */
public class CacheException extends RuntimeException {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
