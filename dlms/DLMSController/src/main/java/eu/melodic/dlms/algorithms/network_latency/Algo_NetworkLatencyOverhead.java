package eu.melodic.dlms.algorithms.network_latency;

import eu.melodic.dlms.db.model.CloudProvider;
import eu.melodic.dlms.db.model.NetworkLatencyKey;
import eu.melodic.dlms.db.model.NetworkLatencyMatrix;
import eu.melodic.dlms.db.model.Region;
import eu.melodic.dlms.db.repository.CloudProviderRepository;
import eu.melodic.dlms.db.repository.NetworkLatencyRepository;
import eu.melodic.dlms.db.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
public class Algo_NetworkLatencyOverhead {
    private Map<String, List<NetworkLatencyMatrix>> networkLatencyMatrixConcurrentMap = new ConcurrentHashMap<>();
    private final NetworkLatencyRepository networkLatencyRepository;
    private final CloudProviderRepository cloudProviderRepository;
    private final RegionRepository regionRepository;

    public void includeLatency(String regionA, String cloudServiceProviderA, String regionB, String cloudServiceProviderB, String executionId)
    {
        log.debug("includeLatency params: A: {}, {}, B: {}, {}", regionA, cloudServiceProviderA, regionB, cloudServiceProviderB);

        Long cloudServiceProviderAId = Optional.ofNullable(cloudProviderRepository.findByName(cloudServiceProviderA)).map(CloudProvider::getId).orElse(0L);
        if(cloudServiceProviderAId == 0L) {
            log.error("Cannot include latency - Cloud Service Provider A \"{}\" has to exist in the database.", cloudServiceProviderA);
            return;
        }
        Region regionAEntity = regionRepository.findByNameAndCloudProviderId(regionA, cloudServiceProviderAId);
        if(Objects.isNull(regionAEntity)) {
            log.error("Cannot include latency - Region A \"{}\" entity has to exist in the database.", regionA);
            return;
        }

        Long cloudServiceProviderBId = Optional.ofNullable(cloudProviderRepository.findByName(cloudServiceProviderB)).map(CloudProvider::getId).orElse(0L);
        if(cloudServiceProviderBId == 0L) {
            log.error("Cannot include latency - Cloud Service Provider B \"{}\" has to exist in the database.", cloudServiceProviderB);
            return;
        }
        Region regionBEntity = regionRepository.findByNameAndCloudProviderId(regionB, cloudServiceProviderBId);
        if(Objects.isNull(regionBEntity)) {
            log.error("Cannot include latency - Region B \"{}\" entity has to exist in the database.", regionB);
            return;
        }

        NetworkLatencyKey pk;
        if (regionAEntity.getId() < regionBEntity.getId()) {
            pk = new NetworkLatencyKey(regionAEntity, regionBEntity);
        } else {
            pk = new NetworkLatencyKey(regionBEntity, regionAEntity);
        }

        Optional<NetworkLatencyMatrix> networkLatencyMatrix = networkLatencyRepository.findById(pk);

        if(!networkLatencyMatrix.isPresent()) {
            log.error("Network latency does not exist in Network Latency Matrix for region entities: A: {}, B: {}", regionAEntity, regionBEntity);
            return;
        } else {
            networkLatencyMatrixConcurrentMap.computeIfAbsent(executionId, mf -> new ArrayList<>()).add(networkLatencyMatrix.get());
            log.debug("Network latency found (value in millis: {}) in Network Latency Matrix for region entities: A: {}, B: {}", networkLatencyMatrix.get().getNetworkLatencyMillis(), regionAEntity, regionBEntity);
        }
    }

    public double calculateUtilityBasedOnNetworkLatency(String executionId) {
        double  totalLatency = 0;
        double utility = 0;
        int latencyCount = 0;

        log.debug("Calculating utility for executionId {} based on collected network latencies: {}", executionId, networkLatencyMatrixConcurrentMap.get(executionId));

        if(Objects.isNull(networkLatencyMatrixConcurrentMap.get(executionId))) {
            log.debug("No data in Network Latency Matrix for executionId {}", executionId);
            return 0;
        }

        totalLatency = networkLatencyMatrixConcurrentMap.get(executionId).stream().mapToDouble(networkLatencyMatrix -> networkLatencyMatrix.getNetworkLatencyMillis().doubleValue()).sum();
        latencyCount = networkLatencyMatrixConcurrentMap.get(executionId).size();

        if(totalLatency == 0) {
            log.debug("Total network latency cannot be 0 ms (there is always some latency in the network!). Collected latencies: {} for executionId: {}", latencyCount, executionId);
        } else {
            BigDecimal minLatencyMillis = networkLatencyRepository.minNetworkLatencyMillis();
            BigDecimal maxLatencyMillis = networkLatencyRepository.maxNetworkLatencyMillis();

            BigDecimal effectiveMinLatencyMillis = networkLatencyMatrixConcurrentMap.get(executionId)
                    .stream()
                    .map(networkLatencyMatrix -> networkLatencyMatrix.getNetworkLatencyMillis().compareTo(minLatencyMillis) != 1 ? networkLatencyMatrix.getNetworkLatencyMillis() : minLatencyMillis)
                    .min(BigDecimal::compareTo)
                    .orElse(null);
            BigDecimal effectiveMaxLatencyMillis = networkLatencyMatrixConcurrentMap.get(executionId)
                    .stream()
                    .map(networkLatencyMatrix -> networkLatencyMatrix.getNetworkLatencyMillis().compareTo(maxLatencyMillis) == 1 ? networkLatencyMatrix.getNetworkLatencyMillis() : maxLatencyMillis)
                    .min(BigDecimal::compareTo)
                    .orElse(null);
            log.debug("Min and max network latencies for executionId {}: minLatencyMillis: {}, maxLatencyMillis: {}, effectiveMinLatencyMillis: {}, effectiveMaxLatencyMillis: {}"
                    ,executionId
                    ,minLatencyMillis
                    ,maxLatencyMillis
                    ,effectiveMinLatencyMillis
                    ,effectiveMaxLatencyMillis);

            if(Objects.nonNull(effectiveMinLatencyMillis) && Objects.nonNull(effectiveMaxLatencyMillis)) {
                if (effectiveMinLatencyMillis.equals(effectiveMaxLatencyMillis)) { //only one value
                    utility = 1;
                } else {
                    utility = (totalLatency - (latencyCount * effectiveMinLatencyMillis.doubleValue())) / (latencyCount * (effectiveMaxLatencyMillis.doubleValue() - effectiveMinLatencyMillis.doubleValue()));
                }
            } else {
                log.debug("Null reference found, effectiveMinLatencyMillis: {}, effectiveMaxLatencyMillis: {}", effectiveMinLatencyMillis, effectiveMaxLatencyMillis);
            }

            log.debug("Calculated utility for executionId {}: {}", executionId, utility);
        }
        return utility;
    }

    public void cleanup(String executionId) {
        networkLatencyMatrixConcurrentMap.remove(executionId);
    }
}
