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
public class Deployment implements Serializable {
    @JsonProperty("nodeName")
    private String nodeName;
    @JsonProperty("instanceId")
    private String instanceId;
    @JsonProperty("cloudProviderName")
    private String cloudProviderName;
    @JsonProperty("locationName")
    private String locationName;
    @JsonProperty("imageProviderId")
    private String imageProviderId;
    @JsonProperty("hardwareProviderId")
    private String hardwareProviderId;
    @JsonProperty("nodeAccessToken")
    private String nodeAccessToken;
    @JsonProperty("emsDeployment")
    private EmsDeploymentRequest emsDeployment;
}
