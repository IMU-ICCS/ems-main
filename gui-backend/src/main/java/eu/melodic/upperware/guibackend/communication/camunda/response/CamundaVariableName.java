package eu.melodic.upperware.guibackend.communication.camunda.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CamundaVariableName {
    DISCOVERY_SERVICE_RESULT("discoveryServiceResult"),
    CP_CREATION_RESULT_CODE("cpCreationResultCode"),
    CP_SOLUTION_RESULT_CODE("cpSolutionResultCode"),
    APPLICATION_DEPLOYMENT_RESULT_CODE("applicationDeploymentResultCode"),
    USE_EXISTING_CP("useExistingCP"),
    PROCESS_STATE("processState"),
    APPLICATION_ID("applicationId"),
    CP_CDO_PATH("cpCdoPath"),
    DEPLOYED_SOLUTION_ID("deployedSolutionId"),
    DEPLOYMENT_INSTANCE_NAME("deploymentInstanceName");

    public final String label;

}
