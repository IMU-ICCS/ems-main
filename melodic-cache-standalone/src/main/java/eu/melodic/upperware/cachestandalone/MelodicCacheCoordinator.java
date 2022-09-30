package eu.melodic.upperware.cachestandalone;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.CacheUtils;
import eu.melodic.cache.NodeCandidates;
import lombok.RequiredArgsConstructor;
import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class MelodicCacheCoordinator {

    private final CacheService<NodeCandidates> cacheService;

    //cdoResourcePath -> nodeCandidates
    private Map<String, NodeCandidates> nodeCandidatesByPath = new HashMap<>();

    NodeCandidate getCheapest(String vmName, int providerIndex, String cdoResourcePath) {
        return (NodeCandidate) nodeCandidatesByPath.get(cdoResourcePath).getCheapest(vmName, providerIndex, new Predicate[0]).get();
    }

    List<NodeCandidate> getNodeCandidates(String vmName, int providerIndex, String cdoResourcePath) {
        return nodeCandidatesByPath.get(cdoResourcePath).get(vmName, providerIndex, new Predicate[0]);
    }


    void reloadNodeCandidatesIfNeeded(String cdoResourcePath, boolean shouldReload) {
        if (shouldReload || !nodeCandidatesByPath.containsKey(cdoResourcePath)) {
            NodeCandidates nodeCandidates = cacheService.load(CacheUtils.createCacheKey(cdoResourcePath));
            if (nodeCandidates != null) {
                nodeCandidatesByPath.put(cdoResourcePath, nodeCandidates);
            }
        }
    }

}
