package eu.melodic.upperware.adapter.service.Instance_no_provider;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InstanceStatus {
    @SerializedName("busy") BUSY,
    @SerializedName("idle") IDLE,
    NOT_KNOWN
}
