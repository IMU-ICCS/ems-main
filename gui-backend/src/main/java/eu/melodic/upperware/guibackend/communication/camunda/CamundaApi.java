package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessVariables;

public interface CamundaApi {

    ProcessVariables getProcessVariables(String processId);
}
