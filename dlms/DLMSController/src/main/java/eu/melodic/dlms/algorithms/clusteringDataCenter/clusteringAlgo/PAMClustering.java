package eu.melodic.dlms.algorithms.clusteringDataCenter.clusteringAlgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * Need to manually enter the number of clusters
 */
@Getter
@Setter
public class PAMClustering {

	private int numCluster;
	private List<String> centroidList = new ArrayList<>(); // list of centers
	private Map<String, Map<String, Double>> dataSetToOtherValuesMap = new HashMap<>();
	// map of clusters
	private Map<Integer, Cluster> clusterMap = new HashMap<>();

	/**
	 * primary method
	 */
	public List<Cluster> run() {
		boolean isChanged = true;
		// build phase
		init(this.dataSetToOtherValuesMap);
		while (isChanged) {
			this.clusterMap = new HashMap<>();
			assignCluster(this.dataSetToOtherValuesMap);
			// change phase
			// change all the cluster
			isChanged = changeCenter();
		}
		return getClusters(this.clusterMap);
	}

	/**
	 * return this clusters
	 */
	public List<Cluster> getClusters(Map<Integer, Cluster> clusterMap) {
		return new ArrayList<>(clusterMap.values());
	}

	/**
	 * change cluster center
	 */
	private boolean changeCenter() {
		boolean isChanged = false;
		List<String> newCentroidList = new ArrayList<String>();
		for (Map.Entry<Integer, Cluster> entry : clusterMap.entrySet()) {
			Cluster cluster = entry.getValue();
			newCentroidList.add(cluster.getDataCenterList().get(assignToCluster(cluster)));
		}
		isChanged = !listEqualsIgnoreOrder(newCentroidList, centroidList);
		centroidList = newCentroidList;

		return isChanged;
	}

	private static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
		return new HashSet<>(list1).equals(new HashSet<>(list2));
	}

	/**
	 * assign datacenter to cluster
	 */
	private int assignToCluster(Cluster cluster) {
		int bestCenter = 0;
		double similarity = 0;
		double bestSimilarity = Double.MAX_VALUE;

		for (int i = 0; i < cluster.getDataCenterList().size(); i++) {
			similarity = 0;
			for (int j = 0; j < cluster.getDataCenterList().size(); j++) {
				if (i == j)
					continue;
				similarity += calculateDistance(cluster.getDataCenterList().get(i), cluster.getDataCenterList().get(j));
			}
			if (similarity < bestSimilarity) {
				bestSimilarity = similarity;
				bestCenter = i;
			}
		}
		return bestCenter;
	}

	/**
	 * distance between two points
	 */
	private double calculateDistance(String center, String to) {
		double distance = 0;
		Map<String, Double> dcFromCenter = dataSetToOtherValuesMap.get(center);
		if (dcFromCenter.containsKey(to)) {
			distance = dcFromCenter.get(to);
		} else { // if dist from cent to cluster does not exist but only other way
			if (dataSetToOtherValuesMap.containsKey(to)) {
				dcFromCenter = dataSetToOtherValuesMap.get(to);
				// if not enough data then distance cannot be found
				if (dcFromCenter.containsKey(center))
					distance = dcFromCenter.get(center);
			}
		}
		return distance;
	}
	/**
	 * distribute datacenters to different clusters
	 */
	private void assignCluster(Map<String, Map<String, Double>> dataSetToOtherValuesMap) {
		double max = Double.MAX_VALUE;
		double min = max;
		double distance = 0.0;
		int addToCluster = -1;

		for (Map.Entry<String, Map<String, Double>> entry : dataSetToOtherValuesMap.entrySet()) {
			min = max; // reset
			for (int i = 0; i < centroidList.size(); i++) {
				// if this cluster was actually selected as a cluster center
				if (centroidList.get(i).equalsIgnoreCase(entry.getKey())) {
					min = 0.0;
					addToCluster = i;
					break;
				} else {
					// compute distance from cluster center
					distance = calculateDistance(centroidList.get(i), entry.getKey());
					if (distance < min) {
						min = distance;
						addToCluster = i;
					}
				}
			}
			Cluster cluster = new Cluster();
			List<String> dataCenterList = new ArrayList<>();
			if (this.clusterMap.containsKey(addToCluster)) {
				cluster = this.clusterMap.get(addToCluster);
				dataCenterList = cluster.getDataCenterList();
			}
			dataCenterList.add(entry.getKey());
			cluster.setDataCenterList(dataCenterList);
			this.clusterMap.put(addToCluster, cluster);
		}

	}

	/**
	 * initialization
	 */
	private void init(Map<String, Map<String, Double>> dataSetToOtherValuesMap) {
		List<String> keysAsArray = new ArrayList<String>(dataSetToOtherValuesMap.keySet());

		// add unique datacenter name to centroidlist
		Collections.shuffle(keysAsArray);
		this.centroidList = keysAsArray.stream().limit(numCluster).collect(Collectors.toList());
	}

	@Getter
	@Setter
	public class Cluster {
		private List<String> dataCenterList;

		@Override
		// to help in debug
		public String toString() {
			return this.dataCenterList.toString();
		}
	}

}
