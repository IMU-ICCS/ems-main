package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cloudiator.rest.model.Communication;
import io.github.cloudiator.rest.model.Optimization;
import io.github.cloudiator.rest.model.Requirement;
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
public class Job implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("tasks")
    private List<Task> tasks = null;
    @JsonProperty("communications")
    private List<Communication> communications = null;
    @JsonProperty("requirements")
    private List<Requirement> requirements = null;
    @JsonProperty("optimization")
    private Optimization optimization;
    @JsonProperty("id")
    private String id;
    @JsonProperty("owner")
    private String owner;
}
