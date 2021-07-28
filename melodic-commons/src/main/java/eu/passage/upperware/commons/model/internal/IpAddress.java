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
public class IpAddress implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("ipAddressType")
    private IpAddressType ipAddressType;
    @JsonProperty("ipVersion")
    private IpVersion ipVersion;
    @JsonProperty("value")
    private String value;
}
