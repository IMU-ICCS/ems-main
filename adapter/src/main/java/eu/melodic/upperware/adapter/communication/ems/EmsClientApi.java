package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.services.adapter.Monitor;
import eu.melodic.models.services.adapter.MonitorsDataRequest;
import eu.melodic.models.services.adapter.MonitorsDataRequestImpl;
import eu.melodic.models.services.adapter.MonitorsDataResponse;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class EmsClientApi implements EmsApi {

    private RestTemplate restTemplate;
    private AdapterProperties adapterProperties;

    @Override
    public List<Monitor> getMonitors(String applicationId, Watermark watermark, String authorization) {
        HttpEntity entity = createHttpEntity(applicationId, watermark, authorization);

        ResponseEntity<MonitorsDataResponse> exchange = restTemplate.exchange(adapterProperties.getEms().getUrl(), HttpMethod.POST, entity, MonitorsDataResponse.class);

        if (exchange.getStatusCode() != HttpStatus.OK){
            throw new AdapterException("Could not get monitors. HttpStatus: " + exchange.getStatusCode());
        }

        if (exchange.getBody() == null) {
            throw new AdapterException("Could not get monitors. Body is null");
        }

        return exchange.getBody().getMonitors();
    }

    private HttpEntity<MonitorsDataRequest> createHttpEntity(String applicationId, Watermark watermark, String authorization) {
        HttpHeaders headers = createHttpHeaders(authorization);
        MonitorsDataRequest request = createRequest(applicationId, watermark);
        return new HttpEntity<>(request, headers);
    }

    private HttpHeaders createHttpHeaders(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotBlank(authorization)){
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
        }
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private MonitorsDataRequest createRequest(String applicationId, Watermark watermark) {
        MonitorsDataRequest request = new MonitorsDataRequestImpl();
        request.setWatermark(watermark);
        request.setApplicationId(applicationId);
        return request;
    }
}
