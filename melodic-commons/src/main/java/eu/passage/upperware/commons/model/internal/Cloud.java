package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Cloud implements Serializable {
    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("cloudType")
    private CloudType cloudType;
    @JsonProperty("api")
    private Api api;
    @JsonProperty("credential")
    private CloudCredential credential;
    @JsonProperty("cloudConfiguration")
    private CloudConfiguration cloudConfiguration;
    @JsonProperty("id")
    private String id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("state")
    private CloudState state;
    @JsonProperty("diagnostics")
    private String diagnostics;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
