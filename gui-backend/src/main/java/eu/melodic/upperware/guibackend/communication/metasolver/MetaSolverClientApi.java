package eu.melodic.upperware.guibackend.communication.metasolver;

import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.services.adapter.DifferenceRequestImpl;
import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.adapter.AdapterApi;
import eu.melodic.upperware.guibackend.communication.commons.RestCommunicationService;
import eu.melodic.upperware.guibackend.communication.commons.ServiceName;
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
    public MetricsNamesResponse getMetricNames() {
        String metaSolverUrl = guiBackendProperties.getMetaSolver().getUrl() + "/getMetricNames";
        ParameterizedTypeReference<MetricsNamesResponse> responseType =
                new ParameterizedTypeReference<MetricsNamesResponse>() {
                };
        return getResponse(metaSolverUrl, responseType, null, ServiceName.METASOLVER.name, HttpMethod.GET).getBody();
    }
}
