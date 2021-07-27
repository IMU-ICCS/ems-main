package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CommandsInstallation implements Serializable {
    @JsonProperty("preInstall")
    private String preInstall;
    @JsonProperty("install")
    private String install;
    @JsonProperty("postInstall")
    private String postInstall;
    @JsonProperty("preStart")
    private String preStart;
    @JsonProperty("start")
    private String start;
    @JsonProperty("postStart")
    private String postStart;
    @JsonProperty("preStop")
    private String preStop;
    @JsonProperty("stop")
    private String stop;
    @JsonProperty("postStop")
    private String postStop;
    @JsonProperty("updateCmd")
    private String updateCmd;
    @JsonProperty("operatingSystemType")
    private OperatingSystem operatingSystemType;
}
