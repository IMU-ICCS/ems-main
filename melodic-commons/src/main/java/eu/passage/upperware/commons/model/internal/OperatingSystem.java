package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class OperatingSystem implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("operatingSystemFamily")
    private OperatingSystemFamily operatingSystemFamily;
    @JsonProperty("operatingSystemArchitecture")
    private OperatingSystemArchitecture operatingSystemArchitecture;
    @JsonProperty("operatingSystemVersion")
    private BigDecimal operatingSystemVersion;
}
