package eu.melodic.upperware.guibackend.communication.commons;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ServiceName {
    CAMUNDA("Camunda"),
    JWT_SERVER("Authorization service"),
    ADAPTER("Adapter"),
    METASOLVER("MetaSolver");

    public final String name;
}
