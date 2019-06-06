package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.upperware.guibackend.communication.camunda.CamundaClientApi;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaProcesInstanceResponse;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableName;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.controller.common.ProcessState;
import eu.melodic.upperware.guibackend.controller.common.VariableStatus;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessVariables;
import eu.melodic.upperware.guibackend.exception.CamundaErrorVariableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessCamundaService {

    private CamundaClientApi camundaClientApi;

    public ProcessVariables getProcessVariables(String processId) {
        Map<String, CamundaVariableResponseItem> processVariablesMap = camundaClientApi.getProcessVariables(processId);
        checkErrorsInNotMonitoringVariablesResponse(processVariablesMap);
        return mapCamundaResponseToProcessVariables(processVariablesMap);
    }

    public List<ProcessInstanceResponse> getAllProcessesData() {
        List<CamundaProcesInstanceResponse> processInstances = camundaClientApi.getProcessInstances();
        log.info("Process instances: ");
        return processInstances.stream()
                .map(CamundaProcesInstanceResponse::getId)
                .map(this::getProcessInstanceResponse)
                .collect(Collectors.toList());
    }


    private ProcessInstanceResponse getProcessInstanceResponse(String processId) {
        log.info("GET info about process with id: {}", processId);
        Map<String, CamundaVariableResponseItem> processVariablesMap = camundaClientApi.getProcessVariables(processId);
        return mapCamundaResponseToProcessInstanceResponse(processId, processVariablesMap);
    }

    private ProcessInstanceResponse mapCamundaResponseToProcessInstanceResponse(String processId, Map<String, CamundaVariableResponseItem> processVariablesMap) {
        return ProcessInstanceResponse.builder()
                .processId(processId)
                .applicationId(processVariablesMap.get("applicationId").getValue())
                .processState(processVariablesMap.containsKey("processState") ? ProcessState.valueOf(processVariablesMap.get("processState").getValue()) : ProcessState.STARTED)
                .finishDate(processVariablesMap.containsKey("processFinishDate") ? processVariablesMap.get("processFinishDate").getValue() : null)
                .build();
    }

    private void checkErrorsInNotMonitoringVariablesResponse(Map<String, CamundaVariableResponseItem> camundaResponse) {
        camundaResponse.forEach((variableName, camundaVariableResponseItem) -> {
            if (!isMonitoredVariable(variableName) && "ERROR".equals(camundaVariableResponseItem.getValue())) {
                throw new CamundaErrorVariableException(variableName, String.format("Error in Camunda variable %s", variableName));
            }
        });
    }

    private boolean isMonitoredVariable(String variableName) {
        for (CamundaVariableName value : CamundaVariableName.values()) {
            if (value.label.equals(variableName)) {
                return true;
            }
        }
        return false;
    }

    private ProcessVariables mapCamundaResponseToProcessVariables(Map<String, CamundaVariableResponseItem> camundaVariables) {
        ProcessVariables result = new ProcessVariables();
        result.setDiscoveryServiceResult(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(CamundaVariableName.DISCOVERY_SERVICE_RESULT.label, null), null));
        result.setCpCreationResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(CamundaVariableName.CP_CREATION_RESULT_CODE.label, null), result.getDiscoveryServiceResult()));
        result.setCpSolutionResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(CamundaVariableName.CP_SOLUTION_RESULT_CODE.label, null), result.getCpCreationResultCode()));
        result.setApplicationDeploymentResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(CamundaVariableName.APPLICATION_DEPLOYMENT_RESULT_CODE.label, null), result.getCpSolutionResultCode()));
        result.setProcessState(mapProcessStateToVariableStatus(camundaVariables.containsKey(CamundaVariableName.PROCESS_STATE.label) ? ProcessState.valueOf(camundaVariables.get(CamundaVariableName.PROCESS_STATE.label).getValue()) : ProcessState.UNKNOWN, result.getApplicationDeploymentResultCode()));
        result.setReconfigurationProcess(camundaVariables.containsKey(CamundaVariableName.USE_EXISTING_CP.label) ? Boolean.valueOf(camundaVariables.get(CamundaVariableName.USE_EXISTING_CP.label).getValue()) : false);
        result.setApplicationId(camundaVariables.containsKey(CamundaVariableName.APPLICATION_ID.label) ? camundaVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue() : "");
        return result;
    }

    private VariableStatus mapCamundaVariableToVariableStatus(CamundaVariableResponseItem camundaVariableResponseItem, VariableStatus previousVariableStatus) {
        if (camundaVariableResponseItem == null && (previousVariableStatus == VariableStatus.SUCCESS || previousVariableStatus == null)) {
            return VariableStatus.ACTIVE;
        } else {
            return camundaVariableResponseItem != null ? VariableStatus.valueOf(camundaVariableResponseItem.getValue()) : VariableStatus.UNKNOWN;
        }
    }

    private VariableStatus mapProcessStateToVariableStatus(ProcessState processState, VariableStatus applicationDeploymetStatus) {
        if (applicationDeploymetStatus == VariableStatus.SUCCESS && processState == ProcessState.STARTED) {
            return VariableStatus.ACTIVE;
        } else if (processState == null || processState == ProcessState.STARTED) {
            return VariableStatus.UNKNOWN;
        } else if (processState == ProcessState.FINISHED) {
            return VariableStatus.SUCCESS;
        } else {
            return VariableStatus.valueOf(processState.name());
        }
    }
}
