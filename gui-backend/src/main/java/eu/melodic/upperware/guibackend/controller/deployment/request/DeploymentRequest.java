package eu.melodic.upperware.guibackend.controller.deployment.request;

import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentRequest {

    private String applicationId;
    private String username;
    private String isSimulation;
    private List<CloudDefinition> cloudDefinitions;
}
