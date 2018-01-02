package eu.paasage.upperware.profiler.generator.error;

/**
 * Created by pszkup on 11.12.17.
 */
public class GeneratorException extends RuntimeException {


    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
