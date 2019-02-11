package eu.melodic.dlms.algorithms.clusteringDataCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.algorithms.clusteringDataCenter.clusteringAlgo.AffinityPropagation;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.TwoDataCenterCombinationRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Cluster without requiring number of clusters
 */
@Slf4j
public class ClusterAfffinityPropagation extends Algo_ClusterDataCenters{
	Map<String, Map<String, Double>> dcValMap;
	
	public ClusterAfffinityPropagation(TwoDataCenterCombinationRepository twoDataCenterCombinationRepository,
			DataCenterZoneRepository dataCenterZoneRepository, Map<String, Map<String, Double>> dcValMap) {
		super(twoDataCenterCombinationRepository, dataCenterZoneRepository);
		this.dcValMap = dcValMap;
	}
	
	/**
	 * Start method
	 */
	public void run() {
		List<String> dataCenters = getListDataCenter(dcValMap);
		double[][] matrix = convertMatrix(dcValMap, dataCenters);
		AffinityPropagation affinityPropagation = new AffinityPropagation(matrix);
		List<AffinityPropagation.ClusterIds> clusterIdList = affinityPropagation.run();
		saveDataCenterZone(clusterIdList, dataCenters);
	}

	/**
	 * Save the datacenterId and connected zone
	 */
	public void saveDataCenterZone(List<AffinityPropagation.ClusterIds> clusterIdList, List<String> dataCenters) {
		int i = 1;
		List<DataCenterZone> dataCenterZoneList = new ArrayList<>();
		for (AffinityPropagation.ClusterIds clusterId : clusterIdList) {
			for (Integer item : clusterId.getDataCenterIdList()) {
				long dcId = Long.parseLong(dataCenters.get(item));
				DataCenterZone dataCenterZone = new DataCenterZone(dcId,dataCenters.get(item), i);
				dataCenterZoneList.add(dataCenterZone);
			}
			// change to different zone 
			i ++;
		}		
		dataCenterZoneRepository.saveAll(dataCenterZoneList);
		log.debug("Successfully clustered the data centers using Affinity propagation");
	}

	
	/**
	 * Returns a list of datacenter names
	 */
	public List<String> getListDataCenter(Map<String, Map<String, Double>> dcValMap) {
		return new ArrayList<>(dcValMap.keySet());
	}

	/**
	 * Put data in a n*n dimensional matrix
	 */
	public double[][] convertMatrix(Map<String, Map<String, Double>> dcValMap, List<String> dataCenters) {
		double ret[][] = new double[dcValMap.size()][dcValMap.size()];
		for (int i = 0; i < dataCenters.size(); i++) {
			for (int j = 0; j < dataCenters.size(); j++) {
				// Data in ith row and ith column should not be used
				if (i == j) {
					ret[i][j] = 0;
					continue;
				}
				// connection to other datacenter exists
				if (dcValMap.get(dataCenters.get(i)).containsKey(dataCenters.get(j)))
					ret[i][j] = dcValMap.get(dataCenters.get(i)).get(dataCenters.get(j));
				else
					ret[i][j] = -1; // no connection
			}
		}
		return ret;
	}
	
}
