package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cloudiator.rest.model.*;
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
public class Task implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("ports")
    private List<Port> ports = null;
    @JsonProperty("interfaces")
    private List<TaskInterface> interfaces = null;
    @JsonProperty("optimization")
    private Optimization optimization;
    @JsonProperty("requirements")
    private List<Requirement> requirements = null;
    @JsonProperty("behaviour")
    private Behaviour behaviour;
}
