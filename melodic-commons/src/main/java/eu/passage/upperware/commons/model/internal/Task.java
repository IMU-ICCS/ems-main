package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("taskId")
    private String taskId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("installation")
    private CommandsInstallation installation;
    @JsonProperty("dockerEnvironment")
    private DockerEnvironment dockerEnvironment;
    @JsonProperty("portsToOpen")
    private List<Port> portsToOpen;
    @JsonProperty("parentTasks")
    private List<String> parentTasks;
    @JsonProperty("deployments")
    private List<Deployment> deployments;
}
