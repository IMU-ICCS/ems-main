package eu.melodic.upperware.guibackend.communication.camunda;

import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaProcesInstanceResponse;
import eu.melodic.upperware.guibackend.communication.camunda.response.CamundaVariableResponseItem;
import eu.melodic.upperware.guibackend.communication.commons.RestCommunicationService;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CamundaClientApi extends RestCommunicationService implements CamundaApi {

    private GuiBackendProperties guiBackendProperties;

    public CamundaClientApi(RestTemplate restTemplate, GuiBackendProperties guiBackendProperties) {
        super(restTemplate);
        this.guiBackendProperties = guiBackendProperties;
    }

    @Override
    public Map<String, CamundaVariableResponseItem> getProcessVariables(String processId) {
        String camundaUrl = "http://" + guiBackendProperties.getCamunda().getHost() +
                ":" + guiBackendProperties.getCamunda().getPort() +
                "/rest/engine/default/process-instance/" + processId + "/variables";
        ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>> responseType =
                new ParameterizedTypeReference<Map<String, CamundaVariableResponseItem>>() {
                };
        return getResponse(camundaUrl, responseType, null, "Camunada", HttpMethod.GET).getBody();
    }

    @Override
    public List<CamundaProcesInstanceResponse> getProcessInstances() {
        String camundaUrl = "http://" + guiBackendProperties.getCamunda().getHost() +
                ":" + guiBackendProperties.getCamunda().getPort() + "/rest/engine/default/process-instance";
        ParameterizedTypeReference<List<CamundaProcesInstanceResponse>> responseType =
                new ParameterizedTypeReference<List<CamundaProcesInstanceResponse>>() {
                };
        return getResponse(camundaUrl, responseType, null, "Camunada", HttpMethod.GET).getBody();
    }
}
