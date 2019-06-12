package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaProcesInstanceResponse;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
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

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamundaClientApi implements CamundaApi {

    private RestTemplate restTemplate;
    private GuiBackendProperties guiBackendProperties;

    @Override
    public Map<String, CamundaVariableResponseItem> getProcessVariables(String processId) {
        String camundaUrl = "http://" + guiBackendProperties.getCamunda().getHost() +
                ":" + guiBackendProperties.getCamunda().getPort() +
                "/rest/engine/default/process-instance/" + processId + "/variables";
        ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>> responseType =
                new ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>>() {
                };
        return getResponse(camundaUrl, responseType).getBody();
    }

    @Override
    public List<CamundaProcesInstanceResponse> getProcessInstances() {
        String camundaUrl = "http://" + guiBackendProperties.getCamunda().getHost() +
                ":" + guiBackendProperties.getCamunda().getPort() + "/rest/engine/default/process-instance";
        ParameterizedTypeReference<List<CamundaProcesInstanceResponse>> responseType =
                new ParameterizedTypeReference<List<CamundaProcesInstanceResponse>>() {
                };
        return getResponse(camundaUrl, responseType).getBody();
    }

    private <T> ResponseEntity<T> getResponse(String camundaUrl, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> camundaResponse = null;

        try {
            camundaResponse = restTemplate.exchange(camundaUrl, HttpMethod.GET, null, responseType);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in communication with Camunda. Camunda not working. Please try again.");
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested process doesn't exist");
        }

        if (camundaResponse.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in communication with Camunda. Camunda not working. Please try again.");
        }

        return camundaResponse;
    }

}
