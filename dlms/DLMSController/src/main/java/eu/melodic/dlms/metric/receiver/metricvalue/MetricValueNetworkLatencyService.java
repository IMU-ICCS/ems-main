package eu.melodic.dlms.metric.receiver.metricvalue;

import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.NetworkLatencyKey;
import eu.melodic.dlms.db.model.NetworkLatencyMatrix;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.NetworkLatencyRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MetricValueNetworkLatencyService {
    @Autowired
    private final NetworkLatencyRepository networkLatencyRepository;
    private final CloudProviderRepository cpRepository;
    private final RegionRepository regionRepository;

    public void saveOrUpdateNetworkLatencies(String dlmsAgentRegion, String dlmsAgentCSP, List<Map<String, BigDecimal>> latencies) {
        latencies.forEach(LatencyPerRegion -> LatencyPerRegion.forEach((region, latency) -> latencyUpsert(dlmsAgentRegion, dlmsAgentCSP, region, latency)));
    }

    private void latencyUpsert(String dlmsAgentRegion, String dlmsAgentCSP, String componentCSPRegion, BigDecimal latency) {
        // for DLMS Agent
        Long dlmsAgentCSPId = storeCSPAndRetrieveCSPId(dlmsAgentCSP, true);
        Region dlmsAgentRegionEntity = storeAndRetrieveRegion(dlmsAgentRegion, dlmsAgentCSPId);
        // for Component
        Long componentCSPId = storeCSPAndRetrieveCSPId(componentCSPRegion.split("::")[0], true);
        Region componentRegionEntity = storeAndRetrieveRegion(componentCSPRegion.split("::")[1], componentCSPId);

        NetworkLatencyKey pk;
        if (dlmsAgentRegionEntity.getId() < componentRegionEntity.getId()) { // this is to keep the A->B region relation always the same, even when the regions are swapped between Agent and Component - i.e. Region_A -> Region_B has the same latency as Region_B -> Region_A
            pk = new NetworkLatencyKey(dlmsAgentRegionEntity, componentRegionEntity);
        } else {
            pk = new NetworkLatencyKey(componentRegionEntity, dlmsAgentRegionEntity);
        }

        Optional<NetworkLatencyMatrix> networkLatencyMatrix = networkLatencyRepository.findById(pk);

        if(!networkLatencyMatrix.isPresent()) {
            networkLatencyMatrix = Optional.of(new NetworkLatencyMatrix(pk, latency, LocalDateTime.now()));
        } else {
            networkLatencyMatrix.get().setNetworkLatencyMillis(latency);
        }
        networkLatencyRepository.saveAndFlush(networkLatencyMatrix.get());
    }

    private Long storeCSPAndRetrieveCSPId(String name, boolean isPublic) {
        if (!cpRepository.existsByName(name)) {
            CloudProvider cloudProvider = new CloudProvider(name, isPublic);
            return cpRepository.save(cloudProvider).getId();
        }
        return cpRepository.findByName(name).getId();
    }

    private Region storeAndRetrieveRegion(String name, Long cpId) {
        if (!regionRepository.existsByNameAndCloudProviderId(name, cpId)) {
            Region region = new Region(name, cpId);
            return regionRepository.save(region);
        }
        return regionRepository.findByNameAndCloudProviderId(name, cpId);
    }
}
