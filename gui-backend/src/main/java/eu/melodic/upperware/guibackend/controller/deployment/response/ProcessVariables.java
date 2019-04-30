package eu.melodic.upperware.guibackend.controller.deployment.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessVariables {

    private VariableStatus cpCreationResultCode;
    private VariableStatus cpSolutionResultCode;
    private VariableStatus applicationDeploymentResultCode;
    private VariableStatus discoveryServiceResult;
    private ProcessState processState;
}
