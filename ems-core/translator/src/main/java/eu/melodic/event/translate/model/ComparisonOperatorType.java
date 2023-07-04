package eu.melodic.event.translate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ComparisonOperatorType {
    GREATER_THAN("GREATER_THAN", ">"),
    GREATER_EQUAL_THAN("GREATER_EQUAL_THAN", ">="),
    LESS_THAN("LESS_THAN", "<"),
    LESS_EQUAL_THAN("LESS_EQUAL_THAN", "<="),
    EQUAL("EQUAL", "="),
    NOT_EQUAL("NOT_EQUAL", "<>");

    private final String name;
    private final String operator;
}
