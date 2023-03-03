package eu.paasage.upperware.profiler.generator.communication.impl;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;
import eu.paasage.upperware.profiler.generator.communication.ProactiveClientServiceForGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.NodeCandidate;
import org.ow2.proactive.sal.model.Requirement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceForGeneratorImpl implements ProactiveClientServiceForGenerator {

    private static final int NUMBER_OF_REPEATS_FOR_NODE_CANDIDATES = 20;
    private static final int DELAY_BETWEEN_REQUESTS = 5000;
    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public List<NodeCandidate> findNodeCandidates(List<Requirement> requirements) {
        List<NodeCandidate> nodeCandidates = new LinkedList<>();
        boolean isAnyAsyncNodeCandidatesProcessesInProgress = true;
        int requestNo = 0;
        try {
            while (isAnyAsyncNodeCandidatesProcessesInProgress && (requestNo < NUMBER_OF_REPEATS_FOR_NODE_CANDIDATES)) {
                log.info("Checking if nodeCandidates downlaod process is finished. Trye: {}", requestNo);
                isAnyAsyncNodeCandidatesProcessesInProgress = proactiveClientConnectorService.isAnyAsyncNodeCandidatesProcessesInProgress();
                Thread.sleep(DELAY_BETWEEN_REQUESTS);
                requestNo++;
            }
            if (isAnyAsyncNodeCandidatesProcessesInProgress) {
                throw new RuntimeException("NodeCandidates are not yet present inside proactive scheduler");
            }
            nodeCandidates = proactiveClientConnectorService.fetchNodeCandidates(requirements);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ProactiveClientException e2) {
            log.error("Error message body: {}", e2.getMessage());
        }
        return nodeCandidates;
    }
}
