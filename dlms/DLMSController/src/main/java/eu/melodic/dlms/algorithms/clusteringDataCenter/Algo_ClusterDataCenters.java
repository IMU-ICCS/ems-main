package eu.melodic.dlms.algorithms.clusteringDataCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.db.model.TwoDataCenterCombination;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.TwoDataCenterCombinationRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * Algo to cluster the datacenters to different zones
 * This result from the database will be used in graph similarity algorithm later on
 */
@RequiredArgsConstructor
@Slf4j
@Getter
@Setter
public class Algo_ClusterDataCenters {
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	protected final TwoDataCenterCombinationRepository twoDataCenterCombinationRepository;
	@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
	protected final DataCenterZoneRepository dataCenterZoneRepository;
	
	//configuration parameters
	protected int numCluster;
	protected String clusteringMethod;
	
	/**
	 * Starting method
	 */
	public int cluster() {
		List<TwoDataCenterCombination> twoDataCenterCombList = twoDataCenterCombinationRepository.findAll();
		// data exists
		if (twoDataCenterCombList.size() > 0) {
			Map<String, Map<String, Double>> dcValMap = convertHashMap(twoDataCenterCombList);

			// select the clustering method
			switch (clusteringMethod) {
			case "AffinityPropagation":
				ClusterAfffinityPropagation clusterAfffinityPropagation = new ClusterAfffinityPropagation(
						twoDataCenterCombinationRepository, dataCenterZoneRepository, dcValMap);
				clusterAfffinityPropagation.run();
				break;
			case "PAMClustering":
				ClusterPAMClustering pamClustering = new ClusterPAMClustering(twoDataCenterCombinationRepository,
						dataCenterZoneRepository, dcValMap, this.numCluster);
				pamClustering.run();
				break;
			default:
				log.error("Invalid clustering method selected");
				return -1;
			}
		}else {
			log.debug("No historical data between two data centers exist");
			return -1;
		}
		log.debug("Algo_ClusterDataCenters has finished execution");
		return 0;
	}
	
	/**
	 * Hash map to store the data in a form of hash map
	 */
	public Map<String, Map<String, Double>> convertHashMap(List<TwoDataCenterCombination> twoDataCenterCombList) {
		Map<String, Map<String, Double>> dcValMap = new HashMap<>();
		// first look through dc1 -> dc2
		// which has a higher priority
		for (TwoDataCenterCombination twoDataCenterComb : twoDataCenterCombList) {
			Map<String, Double> toMap = new HashMap<>();
			String dc1 = twoDataCenterComb.getTwoDCKey().getDc1Id().toString();
			String dc2 = twoDataCenterComb.getTwoDCKey().getDc2Id().toString();
			double combValue = twoDataCenterComb.getCombValue();
			// key exists then get map
			if (dcValMap.containsKey(dc1))
				toMap = dcValMap.get(dc1);
			toMap.put(dc2, combValue);
			dcValMap.put(dc1, toMap);
		}
		// second look through dc2 -> dc1
		// add record only if dc1 -> dc2 link does
		for (TwoDataCenterCombination twoDataCenterComb : twoDataCenterCombList) {
			Map<String, Double> toMap = new HashMap<>();
			String dc1 = twoDataCenterComb.getTwoDCKey().getDc1Id().toString();
			String dc2 = twoDataCenterComb.getTwoDCKey().getDc2Id().toString();
			double combValue = twoDataCenterComb.getCombValue();
			// key exists then get map
			if (dcValMap.containsKey(dc2))
				toMap = dcValMap.get(dc2);
			if (!toMap.containsKey(dc1)) {
				toMap.put(dc1, combValue);
				dcValMap.put(dc1, toMap);
			}
		}
		return dcValMap;
	}


}
