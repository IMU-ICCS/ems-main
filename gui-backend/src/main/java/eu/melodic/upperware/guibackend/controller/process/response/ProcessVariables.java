package eu.melodic.upperware.guibackend.controller.process.response;

import eu.melodic.upperware.guibackend.controller.common.VariableStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessVariables {

    private VariableStatus cpCreationResultCode;
    private VariableStatus cpSolutionResultCode;
    private VariableStatus applicationDeploymentResultCode;
    private VariableStatus discoveryServiceResult;
    private VariableStatus processState;
    private boolean isReconfigurationProcess;
    private String applicationId;
    private boolean isSimulation;
}
