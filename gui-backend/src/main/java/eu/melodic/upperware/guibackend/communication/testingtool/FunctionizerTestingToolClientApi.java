package eu.melodic.upperware.guibackend.communication.testingtool;

import eu.melodic.upperware.guibackend.communication.commons.RestCommunicationService;
import eu.melodic.upperware.guibackend.communication.commons.ServiceName;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class FunctionizerTestingToolClientApi extends RestCommunicationService implements FunctionizerTestingToolApi {

    private GuiBackendProperties guiBackendProperties;

    public FunctionizerTestingToolClientApi(RestTemplate restTemplate, GuiBackendProperties guiBackendProperties) {
        super(restTemplate);
        this.guiBackendProperties = guiBackendProperties;
    }

    @Override
    public Object runTests(String token) {
        String requestUrl = guiBackendProperties.getFunctionizerTestingTool().getUrl() + "/test";
        ParameterizedTypeReference<Object> responseType =
            new ParameterizedTypeReference<Object>() {
            };
        HttpEntity<Void> request = createEmptyHttpEntityWithAuthorizationHeader(token);
        ResponseEntity<Object> response = getResponse(
            requestUrl,
            responseType,
            request,
            ServiceName.FUNCTIONIZER_TESTING_TOOL.name,
            HttpMethod.POST
        );
        return response.getBody();
    }
}
