package eu.melodic.upperware.guibackend.controller.deployment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentResponse {

    private ProcessCreationResultItem processCreationResult;
    private int processAuthorizationStatus;
}
