package eu.melodic.upperware.adapter.service.Instance_no_provider.communication;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CheckIfComponentBusyMessage {

    @JsonProperty("componentName")
    @NotNull
    private String componentInstanceName;

    @JsonProperty("componentStatus")
    @NotNull
    private ComponentStatus componentStatus;
}
