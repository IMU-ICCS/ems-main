package eu.melodic.dlms.algorithms.clusteringDataCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.melodic.dlms.algorithms.clusteringDataCenter.clusteringAlgo.PAMClustering;
import eu.melodic.dlms.algorithms.clusteringDataCenter.clusteringAlgo.PAMClustering.Cluster;
import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import eu.melodic.dlms.db.repository.TwoDataCenterCombinationRepository;
import lombok.extern.slf4j.Slf4j;
/**
 * Cluster requiring number of clusters
 */
@Slf4j
public class ClusterPAMClustering extends Algo_ClusterDataCenters {
	private final int numCluster; // configuration parameter

	Map<String, Map<String, Double>> dcValMap;
	
	public ClusterPAMClustering(TwoDataCenterCombinationRepository twoDataCenterCombinationRepository,
			DataCenterZoneRepository dataCenterZoneRepository, Map<String, Map<String, Double>> dcValMap,
			int numCluster) {
		super(twoDataCenterCombinationRepository, dataCenterZoneRepository);
		this.dcValMap = dcValMap;
		this.numCluster = numCluster;
	}

	/**
	 * Start method
	 */
	public void run() {
		PAMClustering clustering = new PAMClustering();
		clustering.setNumCluster(this.numCluster);
		clustering.setDataSetToOtherValuesMap(this.dcValMap);
		List<PAMClustering.Cluster> clusterList = clustering.run();
		saveDataCenterZone(clusterList);
	}

	/**
	 * Save the datacenterId and connected zone
	 */
	public void saveDataCenterZone(List<Cluster> clusterList) {
		int i = 1;
		List<DataCenterZone> dataCenterZoneList = new ArrayList<>();
		for (Cluster cluster : clusterList) {
			for (String item : cluster.getDataCenterList()) {
				long dcId = Long.parseLong(item);
				DataCenterZone dataCenterZone = new DataCenterZone(dcId, i);
				dataCenterZoneList.add(dataCenterZone);
			}
			// change to different zone
			i++;
		}
		dataCenterZoneRepository.saveAll(dataCenterZoneList);
		log.debug("Successfully clustered the data centers using PAM clustering");
	}

}
