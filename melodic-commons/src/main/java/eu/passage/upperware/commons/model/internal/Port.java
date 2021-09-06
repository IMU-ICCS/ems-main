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
public class Port implements Serializable {
    @JsonProperty("value")
    private Integer value;
    @JsonProperty("requestedName")
    private String requestedName;
}
