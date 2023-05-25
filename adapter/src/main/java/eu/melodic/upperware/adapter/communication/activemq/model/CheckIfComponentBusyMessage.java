package eu.melodic.upperware.adapter.communication.activemq.model;

import com.google.gson.annotations.SerializedName;
import eu.melodic.upperware.adapter.service.Instance_no_provider.InstanceStatus;
import lombok.Data;


@Data
public class CheckIfComponentBusyMessage {

    @SerializedName("instanceName")
    private String componentInstanceName;

    @SerializedName("metricValue")
    private InstanceStatus instanceStatus;
}
