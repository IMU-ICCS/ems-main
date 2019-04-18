package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariablesResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessState;
import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessVariables;
import eu.melodic.upperware.guibackend.controller.deployment.response.VariableStatus;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamundaClientApi implements CamundaApi {

    private RestTemplate restTemplate;
    private GuiBackendProperties guiBackendProperties;

    @Override
    public ProcessVariables getProcessVariables(String processId) {
        String camundaUrl = "http://" + guiBackendProperties.getCamunda().getHost() +
                ":" + guiBackendProperties.getCamunda().getPort() +
                "/rest/engine/default/process-instance/" + processId + "/variables";
        ResponseEntity<CamundaVariablesResponse> processVariablesResponse = null;

        try {
            processVariablesResponse = restTemplate.exchange(camundaUrl, HttpMethod.GET, null, CamundaVariablesResponse.class);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem by checking process status in Camunda. Camunda not working. Please try again.");
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested process doesn't exist");
        }

        if (processVariablesResponse.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem by checking process status in Camunda. Camunda not working. Please try again.");
        }
        return mapCamundaResponseToProcessVariables(processVariablesResponse.getBody());
    }

    private ProcessVariables mapCamundaResponseToProcessVariables(CamundaVariablesResponse camundaVariables) {
        return ProcessVariables.builder()
                .applicationDeploymentResultCode(camundaVariables.getApplicationDeploymentResultCode() == null ? VariableStatus.UNKOWN : VariableStatus.valueOf(camundaVariables.getApplicationDeploymentResultCode().getValue()))
                .applySolutionResultCode(camundaVariables.getApplySolutionResultCode() == null ? VariableStatus.UNKOWN : VariableStatus.valueOf(camundaVariables.getApplySolutionResultCode().getValue()))
                .cpCreationResultCode(camundaVariables.getCpCreationResultCode() == null ? VariableStatus.UNKOWN : VariableStatus.valueOf(camundaVariables.getCpCreationResultCode().getValue()))
                .cpSolutionResultCode(camundaVariables.getCpSolutionResultCode() == null ? VariableStatus.UNKOWN : VariableStatus.valueOf(camundaVariables.getCpSolutionResultCode().getValue()))
                .processState(camundaVariables.getProcessState() == null ? ProcessState.UNKNOWN : ProcessState.valueOf(camundaVariables.getProcessState().getValue()))
                .build();
    }
}
