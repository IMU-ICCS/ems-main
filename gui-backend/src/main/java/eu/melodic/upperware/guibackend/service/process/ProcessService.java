package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.upperware.guibackend.communication.camunda.CamundaClientApi;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaProcesInstanceResponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessService {

    private CamundaClientApi camundaClientApi;
    private static final List<String> MONITORING_VARIABLES = Arrays.asList("discoveryServiceResult", "cpCreationResultCode",
            "cpSolutionResultCode", "applicationDeploymentResultCode", "processState");

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
                .processState(processVariablesMap.containsKey("processState") ? ProcessState.valueOf(processVariablesMap.get("processState").getValue()) : null)
                .finishDate(processVariablesMap.containsKey("processFinishDate") ? processVariablesMap.get("processFinishDate").getValue() : null)
                .build();
    }

    private void checkErrorsInNotMonitoringVariablesResponse(Map<String, CamundaVariableResponseItem> camundaResponse) {
        camundaResponse.forEach((variableName, camundaVariableResponseItem) -> {
            if (!MONITORING_VARIABLES.contains(variableName) && "ERROR".equals(camundaVariableResponseItem.getValue())) {
                throw new CamundaErrorVariableException(variableName, String.format("Error in Camunda variable %s", variableName));
            }
        });
    }

    private ProcessVariables mapCamundaResponseToProcessVariables(Map<String, CamundaVariableResponseItem> camundaVariables) {
        ProcessVariables result = new ProcessVariables();
        result.setDiscoveryServiceResult(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(MONITORING_VARIABLES.get(0), null), null));
        result.setCpCreationResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(MONITORING_VARIABLES.get(1), null), result.getDiscoveryServiceResult()));
        result.setCpSolutionResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(MONITORING_VARIABLES.get(2), null), result.getCpCreationResultCode()));
        result.setApplicationDeploymentResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault(MONITORING_VARIABLES.get(3), null), result.getCpSolutionResultCode()));
        result.setProcessState(mapProcessStateToVariableStatus(camundaVariables.containsKey(MONITORING_VARIABLES.get(4)) ? ProcessState.valueOf(camundaVariables.get(MONITORING_VARIABLES.get(4)).getValue()) : ProcessState.UNKNOWN, result.getApplicationDeploymentResultCode()));
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
        } else if (processState == ProcessState.STARTED) {
            return VariableStatus.UNKNOWN;
        } else if (processState == ProcessState.FINISHED) {
            return VariableStatus.SUCCESS;
        } else {
            return VariableStatus.valueOf(processState.name());
        }
    }
}
