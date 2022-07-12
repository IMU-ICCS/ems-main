package eu.melodic.upperware.adapter.communication.activemq.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InstanceStatus {
    BUSY("busy"),
    IDLE("idle"),
    NOT_DEFINED("notDefined");

    private String type;
}
