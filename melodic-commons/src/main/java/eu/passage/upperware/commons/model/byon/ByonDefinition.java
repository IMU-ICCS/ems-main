package eu.passage.upperware.commons.model.byon;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.passage.upperware.commons.model.internal.IpAddress;
import eu.passage.upperware.commons.model.internal.LoginCredential;
import eu.passage.upperware.commons.model.internal.NodeProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ByonDefinition implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("loginCredential")
    private LoginCredential loginCredential;
    @JsonProperty("ipAddresses")
    private List<IpAddress> ipAddresses;
    @JsonProperty("nodeProperties")
    private NodeProperties nodeProperties;
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
