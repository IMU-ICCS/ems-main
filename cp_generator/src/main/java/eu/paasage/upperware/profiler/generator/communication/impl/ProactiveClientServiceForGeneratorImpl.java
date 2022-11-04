package eu.paasage.upperware.profiler.generator.communication.impl;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import eu.paasage.upperware.profiler.generator.communication.ProactiveClientServiceForGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceForGeneratorImpl implements ProactiveClientServiceForGenerator {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public List<NodeCandidate> findNodeCandidates(List<Requirement> requirements) {

        try {
            return proactiveClientConnectorService.fetchNodeCandidates(requirements);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
