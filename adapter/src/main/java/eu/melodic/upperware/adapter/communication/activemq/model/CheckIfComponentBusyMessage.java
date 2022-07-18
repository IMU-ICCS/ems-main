package eu.melodic.upperware.adapter.communication.activemq.model;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CheckIfComponentBusyMessage {

    @SerializedName("instanceName")
    @NotNull
    private String componentInstanceName;

    @SerializedName("instanceStatus")
    @NotNull
    private InstanceStatus instanceStatus;
}
