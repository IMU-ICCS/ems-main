package eu.melodic.upperware.adapter.communication.activemq.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CheckIfComponentBusyMessage {

    @JsonProperty("instanceName")
    @NotNull
    private String componentInstanceName;

    @JsonProperty("instanceStatus")
    @NotNull
    private InstanceStatus instanceStatus;
}
