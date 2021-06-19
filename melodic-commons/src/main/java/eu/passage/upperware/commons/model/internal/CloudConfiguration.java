package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
public class CloudConfiguration implements Serializable {
    @JsonProperty("nodeGroup")
    private String nodeGroup;
    @JsonProperty("properties")
    private Map<String, String> properties = null;
}
