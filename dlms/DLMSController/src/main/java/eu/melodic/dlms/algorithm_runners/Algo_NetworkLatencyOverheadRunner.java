package eu.melodic.dlms.algorithm_runners;

import eu.melodic.dlms.AlgorithmRunner;
import eu.melodic.dlms.DlmsControllerApplication;
import eu.melodic.dlms.algorithms.network_latency.Algo_NetworkLatencyOverhead;
import eu.melodic.dlms.utility.common.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.common.DlmsConfigurationElement;
import io.github.cloudiator.rest.model.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class Algo_NetworkLatencyOverheadRunner extends AlgorithmRunner {
    private Algo_NetworkLatencyOverhead algo;

    @Override
    public void initialize(DlmsControllerApplication application) {
        algo = new Algo_NetworkLatencyOverhead(application.getNetworkLatencyRepository(), application.getCpRepository(), application.getRegionRepository());
    }

    @Override
    public int update(Object... parameters) {
        return 0;
    }

    @Override
    public double queryResults(DlmsConfigurationConnection diff) {
        log.info("Calculating utility from Algo_NetworkLatencyOverheadRunner");
        Collection<DlmsConfigurationElement> proposed = diff.getProposedConfiguration();
        Map<String, List<String>> compConMap = diff.getCompConMap();
        log.debug("compConMap size: {}", compConMap.size());
        final String executionId = UUID.randomUUID().toString();
        log.info("executionId is: {}", executionId);

        double utility = 0;

        for (Map.Entry<String, List<String>> comp : compConMap.entrySet()) {
            String fromComp = comp.getKey();
            List<String> toCompList = comp.getValue();
            log.info("Calculating utility from component {} to component(s) {}", fromComp, toCompList);
            DlmsConfigurationElement fromElement = getComp(proposed, fromComp);

            if (!isEmpty(fromElement)) {
                for (String toComp : toCompList) {
                    DlmsConfigurationElement toElement = getComp(proposed, toComp);
                    if (!isEmpty(toElement)) {
                        log.debug("Algo_NetworkLatencyOverheadRunner.queryResults NodeCandidate fromElement location: {}, {}, {}, {}, {}, {}",
                                fromElement.getNodeCandidate().getLocation().getLocationScope().getValue(),
                                fromElement.getNodeCandidate().getLocation().getName(),
                                fromElement.getNodeCandidate().getLocation().getProviderId(),
                                fromElement.getNodeCandidate().getLocation().getParent().getLocationScope().getValue(),
                                fromElement.getNodeCandidate().getLocation().getParent().getProviderId(),
                                fromElement.getNodeCandidate().getLocation().getParent().getName());
                        log.debug("Algo_NetworkLatencyOverheadRunner.queryResults NodeCandidate toElement location: {}, {}, {}, {}, {}, {}",
                                toElement.getNodeCandidate().getLocation().getLocationScope().getValue(),
                                toElement.getNodeCandidate().getLocation().getName(),
                                toElement.getNodeCandidate().getLocation().getProviderId(),
                                toElement.getNodeCandidate().getLocation().getParent().getLocationScope().getValue(),
                                toElement.getNodeCandidate().getLocation().getParent().getProviderId(),
                                toElement.getNodeCandidate().getLocation().getParent().getName());
                        String regionA;
                        String regionB;
                        if(fromElement.getNodeCandidate().getLocation().getLocationScope() == Location.LocationScopeEnum.REGION){
                            regionA = fromElement.getNodeCandidate().getLocation().getName();
                        }
                        else {
                            regionA = fromElement.getNodeCandidate().getLocation().getParent().getName();
                        }
                        if(toElement.getNodeCandidate().getLocation().getLocationScope() == Location.LocationScopeEnum.REGION){
                            regionB = toElement.getNodeCandidate().getLocation().getName();
                        }
                        else {
                            regionB = toElement.getNodeCandidate().getLocation().getParent().getName();
                        }
                        log.debug("regionA: {}, regionB: {}", regionA, regionB);
                        algo.includeLatency(
                                regionA,
                                fromElement.getNodeCandidate().getCloud().getApi().getProviderName(),
                                regionB,
                                toElement.getNodeCandidate().getCloud().getApi().getProviderName(),
                                executionId
                        );
                    } else {
                        log.error("Variable toElement of type DlmsConfigurationElement is empty for component: {}", toComp);
                    }
                }
            } else {
                log.error("Variable fromElement of type DlmsConfigurationElement is empty for component: {}", fromComp);
            }
        }
        utility = algo.calculateUtilityBasedOnNetworkLatency(executionId);
        algo.cleanup(executionId);
        return utility;
    }
}
