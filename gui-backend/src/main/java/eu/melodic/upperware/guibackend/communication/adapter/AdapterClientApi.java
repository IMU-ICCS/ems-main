package eu.melodic.upperware.guibackend.communication.adapter;

import eu.melodic.models.services.adapter.DifferenceRequestImpl;
import eu.melodic.models.services.adapter.DifferenceResponse;
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
public class AdapterClientApi extends RestCommunicationService implements AdapterApi {

    private GuiBackendProperties guiBackendProperties;

    public AdapterClientApi(RestTemplate restTemplate, GuiBackendProperties guiBackendProperties) {
        super(restTemplate);
        this.guiBackendProperties = guiBackendProperties;
    }

    @Override
    public DifferenceResponse getDifference(DifferenceRequestImpl differenceRequest, String token) {
        String adapterUrl = guiBackendProperties.getAdapter().getUrl() + "/difference";
        ParameterizedTypeReference<DifferenceResponse> responseType =
                new ParameterizedTypeReference<DifferenceResponse>() {
                };
        HttpEntity<DifferenceRequestImpl> request = createHttpEntityWithAuthorizationHeader(differenceRequest, token);
        return getResponse(adapterUrl, responseType, request, ServiceName.ADAPTER.name, HttpMethod.POST).getBody();
    }
}
