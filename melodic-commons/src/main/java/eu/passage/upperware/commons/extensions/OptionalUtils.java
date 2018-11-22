package eu.passage.upperware.commons.extensions;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class OptionalUtils {

    public static <T> UnaryOperator<T> peek(Consumer<T> c) {
        return x -> {
            c.accept(x);
            return x;
        };
    }
}
