package eu.melodic.upperware.guibackend.service.process;

import eu.melodic.models.services.adapter.DifferenceRequestImpl;
import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.adapter.AdapterApi;
import eu.melodic.upperware.guibackend.communication.camunda.CamundaApi;
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

    private CamundaApi camundaApi;
    private CdoService cdoService;
    private AdapterApi adapterApi;

    public CpModelResponse getCpModel(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        if (processVariables.containsKey(CamundaVariableName.CP_CDO_PATH.label)) {
            String cpCdoPath = processVariables.get(CamundaVariableName.CP_CDO_PATH.label).getValue();
            log.info("CpCdoPath: {}", cpCdoPath);

            String applicationId = processVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue();
            log.info("Application id: {}", applicationId);

            return cdoService.getCpModelResponse(cpCdoPath, applicationId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exist for process with id: %s", processId));
        }
    }

    public CpSolutionResponse getCpSolutionForProcess(String processId) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable cpCdoPath doesn't exist for process with id: %s", processId));
        }
    }

    public DifferenceResponse getDeploymentDifference(String processId, String token) {
        Map<String, CamundaVariableResponseItem> processVariables = camundaApi.getProcessVariables(processId);
        String applicationId = processVariables.get(CamundaVariableName.APPLICATION_ID.label).getValue();
        if (processVariables.containsKey(CamundaVariableName.DEPLOYMENT_INSTANCE_NAME.label)) {
            String deploymentInstanceName = processVariables.get(CamundaVariableName.DEPLOYMENT_INSTANCE_NAME.label).getValue();
            log.info("DeploymentInstanceName: {} for applicationId: {} already exist", deploymentInstanceName, applicationId);
            DifferenceRequestImpl differenceRequest = createDifferenceRequest(applicationId, deploymentInstanceName);
            return adapterApi.getDifference(differenceRequest, token);
        } else {
            log.info("Deployment difference not created yet");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Variable deploymentInstanceName doesn't exist for application: %s", applicationId));
        }
    }

    private DifferenceRequestImpl createDifferenceRequest(String applicationId, String deploymentInstanceName) {
        DifferenceRequestImpl differenceRequest = new DifferenceRequestImpl();
        differenceRequest.setApplicationId(applicationId);
        differenceRequest.setDeploymentInstanceName(deploymentInstanceName);
        return differenceRequest;
    }
}
