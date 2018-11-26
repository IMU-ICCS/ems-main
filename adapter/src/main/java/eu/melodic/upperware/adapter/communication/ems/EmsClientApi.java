package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.interfaces.ems.Monitor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class EmsClientApi implements EmsApi {

    private RestTemplate restTemplate;

    @Override
    public List<Monitor> getMonitors(String applicationId) {

        MonitorList monitorList = restTemplate.getForObject(
                "http://158.39.75.140:8099/monitors",
                MonitorList.class);

        return monitorList.getMonitors();
    }
}
