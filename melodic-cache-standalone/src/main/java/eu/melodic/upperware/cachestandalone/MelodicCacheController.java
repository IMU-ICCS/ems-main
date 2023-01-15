package eu.melodic.upperware.cachestandalone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MelodicCacheController {

    private final MelodicCacheCoordinator melodicCacheCoordinator;

    @GetMapping(value = "/getCheapest")
    public NodeCandidate getCheapest(@RequestParam int providerId,
                                     @RequestParam String cdoResourcePath,
                                     @RequestParam String softwareComponentName,
                                     @RequestParam boolean shouldReload) {
        log.info("Received on 'getCheapest': providerId={} cdoResourcePath={}, softwareComponentName={} shouldReload={}",
                providerId, cdoResourcePath, softwareComponentName, shouldReload);
        melodicCacheCoordinator.reloadNodeCandidatesIfNeeded(cdoResourcePath, shouldReload);
        return melodicCacheCoordinator.getCheapest(softwareComponentName, providerId, cdoResourcePath);
    }

    @GetMapping(value = "/getNodeCandidates")
    public List<NodeCandidate> getNodeCandidates(@RequestParam int providerId,
                                                 @RequestParam String cdoResourcePath,
                                                 @RequestParam String softwareComponentName,
                                                 @RequestParam boolean shouldReload) {
        log.info("Received on 'getNodeCandidates': providerId={} cdoResourcePath={}, softwareComponentName={} shouldReload={}",
                providerId, cdoResourcePath, softwareComponentName, shouldReload);
        melodicCacheCoordinator.reloadNodeCandidatesIfNeeded(cdoResourcePath, shouldReload);
        return melodicCacheCoordinator.getNodeCandidates(softwareComponentName, providerId, cdoResourcePath);
    }

}
