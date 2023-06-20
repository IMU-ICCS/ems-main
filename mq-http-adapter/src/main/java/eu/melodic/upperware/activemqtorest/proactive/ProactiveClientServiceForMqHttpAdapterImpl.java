package eu.melodic.upperware.activemqtorest.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Deployment;
import org.ow2.proactive.sal.model.PACloud;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceForMqHttpAdapterImpl implements ProactiveClientServiceForMqHttpAdapter {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public List<Deployment> getAllNodes() {
        Map<String, PACloud> cloudsById = getAllClouds().stream()
                .collect(Collectors.toMap(
                        PACloud::getCloudId,
                        Function.identity()
                ));
        if (cloudsById.isEmpty()) {
            return Collections.emptyList();
        }
        List<Deployment> deployments;
        try {
            deployments = proactiveClientConnectorService.fetchNodes();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
        return deployments.stream()
                .peek(deployment -> deployment.setPaCloud(cloudsById.get(deployment.getPaCloud().getCloudId())))
                .collect(Collectors.toList());
    }
    public List<PACloud> getAllClouds() {
        try {
            return proactiveClientConnectorService.fetchClouds();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
