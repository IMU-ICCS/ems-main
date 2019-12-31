package eu.melodic.upperware.guibackend.communication.metasolver;

import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesRequest;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesResponse;
import eu.melodic.upperware.guibackend.communication.commons.RestCommunicationService;
import eu.melodic.upperware.guibackend.communication.commons.ServiceName;
import eu.melodic.upperware.guibackend.controller.simulation.request.SimulationRequest;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MetaSolverClientApi extends RestCommunicationService implements MetaSolverApi {

    private GuiBackendProperties guiBackendProperties;

    public MetaSolverClientApi(RestTemplate restTemplate, GuiBackendProperties guiBackendProperties) {
        super(restTemplate);
        this.guiBackendProperties = guiBackendProperties;
    }

    @Override
    public MetricsNamesResponse getMetricNames(String applicationId, String token) {
        String metaSolverUrl = guiBackendProperties.getMetaSolver().getUrl() + "/getMetricNames/" + applicationId;
        ParameterizedTypeReference<MetricsNamesResponse> responseType =
                new ParameterizedTypeReference<MetricsNamesResponse>() {
                };
        HttpEntity<SimulatedMetricValuesRequest> request =
                createHttpEntityWithAuthorizationHeader(null, token);
        return getResponse(metaSolverUrl, responseType, request, ServiceName.METASOLVER.name, HttpMethod.GET).getBody();
    }

    @Override
    public SimulatedMetricValuesResponse simulateReconfiguration(SimulatedMetricValuesRequest simulatedMetricValuesRequest,
                                                              String token) {
        String metaSolverUrl = guiBackendProperties.getMetaSolver().getUrl() + "/simulateReconfiguration";
        ParameterizedTypeReference<SimulatedMetricValuesResponse> responseType =
                new ParameterizedTypeReference<SimulatedMetricValuesResponse>() {
                };
        HttpEntity<SimulatedMetricValuesRequest> request =
                createHttpEntityWithAuthorizationHeader(simulatedMetricValuesRequest, token);
        return getResponse(metaSolverUrl, responseType, request, ServiceName.METASOLVER.name, HttpMethod.POST).getBody();
    }
}
