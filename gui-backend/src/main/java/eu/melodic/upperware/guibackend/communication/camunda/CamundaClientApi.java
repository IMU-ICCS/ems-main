package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessState;
import eu.melodic.upperware.guibackend.controller.deployment.response.ProcessVariables;
import eu.melodic.upperware.guibackend.controller.deployment.response.VariableStatus;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

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
        ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>> responseType =
                new ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>>() {
                };
        ResponseEntity<Map<String, CamundaVariableResponseItem>> processVariablesResponse = null;

        try {
            processVariablesResponse = restTemplate.exchange(camundaUrl, HttpMethod.GET, null, responseType);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem by checking process status in Camunda. Camunda not working. Please try again.");
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested process doesn't exist");
        }

        if (processVariablesResponse.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem by checking process status in Camunda. Camunda not working. Please try again.");
        }

        checkErrorsInVariablesResponse(processVariablesResponse.getBody());

        return mapCamundaResponseToProcessVariables(processVariablesResponse.getBody());
    }

    private void checkErrorsInVariablesResponse(Map<String, CamundaVariableResponseItem> camundaResponse) {
        camundaResponse.forEach((variableName, camundaVariableResponseItem) -> {
            if (camundaVariableResponseItem.getValue().equals("ERROR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Error in deployment process. Variable: %s in error state", variableName));
            }
        });
    }


    private ProcessVariables mapCamundaResponseToProcessVariables(Map<String, CamundaVariableResponseItem> camundaVariables) {
        return ProcessVariables.builder()
                .applicationDeploymentResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault("applicationDeploymentResultCode", null)))
                .cpCreationResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault("cpCreationResultCode", null)))
                .cpSolutionResultCode(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault("cpSolutionResultCode", null)))
                .discoveryServiceResult(mapCamundaVariableToVariableStatus(camundaVariables.getOrDefault("discoveryServiceResult", null)))
                .processState(camundaVariables.containsKey("processState") ? ProcessState.valueOf(camundaVariables.get("processState").getValue()) : ProcessState.UNKNOWN)
                .build();
    }

    private VariableStatus mapCamundaVariableToVariableStatus(CamundaVariableResponseItem camundaVariableResponseItem) {
        return camundaVariableResponseItem == null ? VariableStatus.UNKOWN : VariableStatus.valueOf(camundaVariableResponseItem.getValue());
    }
}
