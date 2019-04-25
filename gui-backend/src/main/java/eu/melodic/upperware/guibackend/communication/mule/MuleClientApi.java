package eu.melodic.upperware.guibackend.communication.mule;

import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.controller.deployment.response.NcQueryErrorResponse;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MuleClientApi implements MuleApi {

    private RestTemplate restTemplate;
    private GuiBackendProperties guiBackendProperties;

    @Override
    public DeploymentResponse createDeploymentProcess(DeploymentProcessRequest deploymentProcessRequest) {
        HttpEntity entity = createHttpEntity(deploymentProcessRequest);

        String muleUrl = guiBackendProperties.getEsb().getUrl() + "/api/frontend/deploymentProcess";
        ResponseEntity<DeploymentResponse> processResponse;

        try {
            processResponse = restTemplate.exchange(muleUrl, HttpMethod.POST, entity, DeploymentResponse.class);

            if (processResponse.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(processResponse.getStatusCode(), "Problem with starting your application");
            }

            // fail authorization as Melodic user
            if (processResponse.getBody() != null && processResponse.getBody().getProcessAuthorizationStatus() != 200) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials for Melodic");
            }

            // Cloudiator service not working
            if (processResponse.getBody() != null && processResponse.getBody().getCreateCloudsResponse() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in connection with internal service. Please try again or restart Melodic machine.");
            }

            // empty Node Candidates - invalid provider credentials or slow connection
            if (processResponse.getBody() != null && processResponse.getBody().getProcessCreationResult() == null) {
                ObjectMapper mapper = new ObjectMapper();
                NcQueryErrorResponse ncQueryErrorResponse = mapper.convertValue(processResponse.getBody().getNcQueryResponse(), new TypeReference<NcQueryErrorResponse>() {
                });
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Error by getting node candidates for provider %s: %s. " +
                                "Your provider credentials are invalid or connection is to slow.",
                        ncQueryErrorResponse.getProviderName(), ncQueryErrorResponse.getError()));
            }

            // catch exception if Mule not working
        } catch (ResourceAccessException ex) {
            log.error("Error by connection with Mule service: {}", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in connection with internal service. Please try again or restart Melodic machine");
        }

        return processResponse.getBody();
    }


    private HttpEntity<DeploymentProcessRequest> createHttpEntity(DeploymentProcessRequest deploymentProcessRequest) {
        HttpHeaders httpHeaders = createHttpHeaders();
        return new HttpEntity<>(deploymentProcessRequest, httpHeaders);
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
