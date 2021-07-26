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
public class EmsDeploymentRequest implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("authorizationBearer")
    private String authorizationBearer;
    @JsonProperty("baguetteIp")
    private String baguetteIp;
    @JsonProperty("baguettePort")
    private int baguettePort;
    @JsonProperty("targetOs")
    private OperatingSystemFamily targetOs;
    @JsonProperty("targetType")
    private EmsDeploymentTargetType targetType;
    @JsonProperty("targetName")
    private String targetName;
    @JsonProperty("targetProvider")
    private EmsDeploymentTargetProvider targetProvider;
    @JsonProperty("location")
    private String location;
    @JsonProperty("isUsingHttps")
    private boolean isUsingHttps;
    @JsonProperty("nodeId")
    private String nodeId;
}
