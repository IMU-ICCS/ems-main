package eu.melodic.upperware.cachestandalone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MelodicCacheController {

    private final MelodicCacheCoordinator melodicCacheCoordinator;

    @GetMapping(value = "/getCheapest/{providerId}/{cdoResourcePath}/{softwareComponentName}/{shouldReload}", consumes = APPLICATION_JSON_VALUE)
    public NodeCandidate getCheapest(@PathVariable int providerId,
                                     @PathVariable String cdoResourcePath,
                                     @PathVariable String softwareComponentName,
                                     @PathVariable boolean shouldReload) {
        melodicCacheCoordinator.reloadNodeCandidatesIfNeeded(cdoResourcePath, shouldReload);
        return melodicCacheCoordinator.getCheapest(softwareComponentName, providerId, cdoResourcePath);
    }

    @GetMapping(value = "/getNodeCandidates/{providerId}/{cdoResourcePath}/{softwareComponentName}/{shouldReload}", consumes = APPLICATION_JSON_VALUE)
    public List<NodeCandidate> getNodeCandidates(@PathVariable int providerId,
                                                 @PathVariable String cdoResourcePath,
                                                 @PathVariable String softwareComponentName,
                                                 @PathVariable boolean shouldReload) {
        melodicCacheCoordinator.reloadNodeCandidatesIfNeeded(cdoResourcePath, shouldReload);
        return melodicCacheCoordinator.getNodeCandidates(softwareComponentName, providerId, cdoResourcePath);
    }

}
