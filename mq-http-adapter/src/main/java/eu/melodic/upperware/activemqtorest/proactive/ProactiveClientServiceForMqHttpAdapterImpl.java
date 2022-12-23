package eu.melodic.upperware.activemqtorest.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Deployment;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceForMqHttpAdapterImpl implements ProactiveClientServiceForMqHttpAdapter {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public List<Deployment> getAllNodes() {
        try {
            return proactiveClientConnectorService.fetchNodes();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
