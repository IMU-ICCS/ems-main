package eu.melodic.upperware.guibackend.communication.camunda.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CamundaVariablesResponse {
    private CamundaVariableResponseItem cpCreationResultCode;
    private CamundaVariableResponseItem cpSolutionResultCode;
    private CamundaVariableResponseItem applySolutionResultCode;
    private CamundaVariableResponseItem applicationDeploymentResultCode;
    private CamundaVariableResponseItem processState;
}
