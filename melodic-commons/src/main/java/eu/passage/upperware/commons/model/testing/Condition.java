package eu.passage.upperware.commons.model.testing;

import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public enum Condition {
    EQUALS(String::equals),
    STARTS_WITH(String::startsWith),
    ENDS_WITH(String::endsWith),
    CONTAINS_SUBSTRING(String::contains),
    EQUALS_IGNORE_CASE(String::equalsIgnoreCase),
    MATCHES_REGEX(String::matches);

    private final BiFunction<String, String, Boolean> method;


    Condition(BiFunction<String, String, Boolean> method) {
        this.method = method;
    }
}
