package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class ProactiveClientServiceImpl implements ProactiveClientService {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public Long stopJob(String jobId) {
        try {
            return proactiveClientConnectorService.stopJob(Collections.singletonList(jobId));
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1L;
        }
    }
}
