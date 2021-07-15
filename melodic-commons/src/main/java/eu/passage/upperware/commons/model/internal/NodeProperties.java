package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NodeProperties implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("providerId")
    private String providerId;
    @JsonProperty("numberOfCores")
    private Integer numberOfCores;
    @JsonProperty("memory")
    private Long memory;
    @JsonProperty("disk")
    private Float disk;
    @JsonProperty("operatingSystem")
    private OperatingSystem operatingSystem;
    @JsonProperty("geoLocation")
    private GeoLocation geoLocation;
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
