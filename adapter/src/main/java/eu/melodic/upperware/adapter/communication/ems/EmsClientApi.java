package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class EmsClientApi implements EmsApi {

    private RestTemplate restTemplate;
    private AdapterProperties adapterProperties;

    @Override
    public MonitorList getMonitors(String applicationId, String authorization) {
        HttpEntity entity = createHttpEntity(authorization);

        return restTemplate.exchange(adapterProperties.getEms().getUrl(), HttpMethod.GET, entity, MonitorList.class).getBody();
    }

    private HttpEntity createHttpEntity(String authorization) {
        HttpHeaders headers = createHttpHeaders(authorization);
        return new HttpEntity(headers);
    }

    private HttpHeaders createHttpHeaders(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorization);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
