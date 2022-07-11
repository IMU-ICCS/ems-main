package eu.melodic.upperware.adapter.service.Instance_no_provider.communication.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ComponentStatus {
    BUSY("busy"),
    IDLE("idle"),
    NOT_DEFINED("notDefined");

    private String type;
}
