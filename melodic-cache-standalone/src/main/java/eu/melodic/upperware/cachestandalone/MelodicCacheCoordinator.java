package eu.melodic.upperware.cachestandalone;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cachestandalone.exception.NodeCandidatesNotFound;
import lombok.RequiredArgsConstructor;
import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class MelodicCacheCoordinator {

    @Qualifier("memcacheService")
    private final CacheService<NodeCandidates> cacheService;

    //cdoResourcePath -> nodeCandidates
    private Map<String, NodeCandidates> nodeCandidatesByPath = new HashMap<>();

    NodeCandidate getCheapest(String vmName, int providerIndex, String cdoResourcePath) {
        Optional<NodeCandidate> nodeCandidate = nodeCandidatesByPath.get(cdoResourcePath).getCheapest(vmName, providerIndex, new Predicate[0]);
        if (!nodeCandidate.isPresent()) {
            throw new NodeCandidatesNotFound();
        }
        return nodeCandidate.get();
    }

    List<NodeCandidate> getNodeCandidates(String vmName, int providerIndex, String cdoResourcePath) {
        return nodeCandidatesByPath.get(cdoResourcePath).get(vmName, providerIndex, new Predicate[0]);
    }


    void reloadNodeCandidatesIfNeeded(String cdoResourcePath, boolean shouldReload) {
        if (shouldReload || !nodeCandidatesByPath.containsKey(cdoResourcePath)) {
            NodeCandidates nodeCandidates = cacheService.load(cdoResourcePath);
            if (nodeCandidates == null) {
                throw new NodeCandidatesNotFound();
            }
            nodeCandidatesByPath.put(cdoResourcePath, nodeCandidates);
        }
    }

}
