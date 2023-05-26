package eu.melodic.upperware.adapter.service.Instance_no_provider;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InstanceStatus {
    @SerializedName("1") BUSY,
    @SerializedName("0") IDLE,
    NOT_KNOWN
}
