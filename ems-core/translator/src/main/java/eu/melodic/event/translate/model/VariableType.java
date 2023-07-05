package eu.melodic.event.translate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VariableType {
    CPU("cpu"),
    CORES("cores"),
    RAM("ram"),
    STORAGE("storage"),
    PROVIDER("provider"),
    CARDINALITY("cardinality"),
    OS("os"),
    LOCATION("location"),
    LATITUDE("latitude"),
    LONGITUDE("longitude");

    private final String name;
}
