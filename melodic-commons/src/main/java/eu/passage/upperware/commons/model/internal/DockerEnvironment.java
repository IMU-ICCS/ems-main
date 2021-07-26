package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class DockerEnvironment implements Serializable {
    @JsonProperty("dockerImage")
    private String dockerImage;
    @JsonProperty("port")
    private String port;
    @JsonProperty("environmentVars")
    private Map<String, String> environmentVars;
}
