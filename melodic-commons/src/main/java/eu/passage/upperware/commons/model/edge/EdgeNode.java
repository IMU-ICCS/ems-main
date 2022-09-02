package eu.passage.upperware.commons.model.edge;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.passage.upperware.commons.model.internal.IpAddress;
import eu.passage.upperware.commons.model.internal.LoginCredential;
import eu.passage.upperware.commons.model.internal.NodeProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class EdgeNode implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("systemArch")
    private String systemArch;
    @JsonProperty("scriptURL")
    private String scriptURL;
    @JsonProperty("jarURL")
    private String jarURL;
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
    @JsonProperty("NodeCandidate")
    private String nodeCandidate;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("allocated")
    private Boolean allocated;
    @JsonProperty("jobId")
    private String jobId;
    @JsonProperty("id")
    private String id;

}
