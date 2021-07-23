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
public class Hardware implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("providerId")
    private String providerId;
    @JsonProperty("cores")
    private Integer cores;
    @JsonProperty("ram")
    private Long ram;
    @JsonProperty("disk")
    private Double disk;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("state")
    private DiscoveryItemState state;
    @JsonProperty("owner")
    private String owner;

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
