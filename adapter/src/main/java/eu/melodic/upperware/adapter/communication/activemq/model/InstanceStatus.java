package eu.melodic.upperware.adapter.communication.activemq.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InstanceStatus {
    @SerializedName("busy") BUSY,
    @SerializedName("idle") IDLE,
    NOT_DEFINED

}
