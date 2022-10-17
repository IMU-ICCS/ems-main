package eu.passage.upperware.commons.model.edge;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.passage.upperware.commons.model.internal.IpAddress;
import eu.passage.upperware.commons.model.internal.LoginCredential;
import eu.passage.upperware.commons.model.internal.NodeProperties;
import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class EdgeDefinition {
    @JsonProperty("id")
    private long id;
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
    private List<IpAddress> ipAddresses;
    @JsonProperty("nodeProperties")
    private NodeProperties nodeProperties;
}
