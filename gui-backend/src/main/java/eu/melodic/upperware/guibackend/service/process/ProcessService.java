package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.upperware.guibackend.communication.camunda.CamundaClientApi;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableName;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.service.cdo.CdoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessService {

    private CamundaClientApi camundaClientApi;
    private CdoService cdoService;

    public CpModelResponse getCpModel(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaClientApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.CP_CDO_PATH.label)) {
            String cpCdoPath = processVariables.get(CamundaVariableName.CP_CDO_PATH.label).getValue();
            log.info("CpCdoPath: {}", cpCdoPath);

            String applicationId = processVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue();
            log.info("Application id: {}", applicationId);

            return cdoService.getCpModelResponse(cpCdoPath, applicationId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exists for process with id: %s", processId));
        }
    }

    public CpSolutionResponse getCpSolutionForProcess(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaClientApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.CP_CDO_PATH.label)) {
            String cpCdoPath = processVariables.get(CamundaVariableName.CP_CDO_PATH.label).getValue();
            log.info("CpCdoPath: {}", cpCdoPath);
            if (processVariables.containsKey(CamundaVariableName.DEPLOYED_SOLUTION_ID.label)) {
                log.info("Cp solution already deployed");
                return cdoService.getCpSolution(cpCdoPath, Integer.valueOf(processVariables.get(CamundaVariableName.DEPLOYED_SOLUTION_ID.label).getValue()));
            } else {
                log.info("Cp solution not deployed yet. Getting last available solution");
                return cdoService.getCpSolution(cpCdoPath, null);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exists for process with id: %s", processId));
        }
    }
}
