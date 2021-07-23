package eu.passage.upperware.commons.model.byon;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.passage.upperware.commons.model.internal.IpAddress;
import eu.passage.upperware.commons.model.internal.LoginCredential;
import eu.passage.upperware.commons.model.internal.NodeProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ByonNode implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("loginCredential")
    private LoginCredential loginCredential;
    @JsonProperty("ipAddresses")
    private List<IpAddress> ipAddresses = null;
    @JsonProperty("nodeProperties")
    private NodeProperties nodeProperties;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("diagnostic")
    private String diagnostic;
    @JsonProperty("nodeCandidate")
    private String nodeCandidate;
    @JsonProperty("id")
    private String id;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("allocated")
    private Boolean allocated;
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
