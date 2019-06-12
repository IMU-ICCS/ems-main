package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaProcesInstanceResponse;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;

import java.util.List;
import java.util.Map;

public interface CamundaApi {

    Map<String, CamundaVariableResponseItem> getProcessVariables(String processId);

    List<CamundaProcesInstanceResponse> getProcessInstances();
}
