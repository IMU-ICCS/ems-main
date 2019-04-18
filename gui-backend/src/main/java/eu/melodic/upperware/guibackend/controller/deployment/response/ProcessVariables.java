package eu.melodic.upperware.guibackend.controller.deployment.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVariables {

    private VariableStatus cpCreationResultCode;
    private VariableStatus cpSolutionResultCode;
    private VariableStatus applySolutionResultCode;
    private VariableStatus applicationDeploymentResultCode;
    private ProcessState processState;
}
