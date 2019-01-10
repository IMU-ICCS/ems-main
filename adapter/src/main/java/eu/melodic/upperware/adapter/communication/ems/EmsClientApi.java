package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.interfaces.ems.Monitor;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class EmsClientApi implements EmsApi {

    private RestTemplate restTemplate;
    private AdapterProperties adapterProperties;


    @Override
    public List<Monitor> getMonitors(String applicationId) {
        MonitorList monitorList = restTemplate.getForObject(adapterProperties.getEms().getUrl(), MonitorList.class);
        return monitorList != null ? monitorList.getMonitors() : Collections.emptyList();
    }
}
