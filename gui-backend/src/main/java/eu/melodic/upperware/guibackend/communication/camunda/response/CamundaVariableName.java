package eu.melodic.upperware.guibackend.communication.camunda.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CamundaVariableName {
    DISCOVERY_SERVICE_RESULT("discoveryServiceResult"),
    CP_CREATION_RESULT_CODE("cpCreationResultCode"),
    CP_SOLUTION_RESULT_CODE("cpSolutionResultCode"),
    APPLICATION_DEPLOYMENT_RESULT_CODE("applicationDeploymentResultCode"),
    PROCESS_STATE("processState");


    public final String label;

}
