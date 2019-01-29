package eu.melodic.dlms.algorithms.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;

import eu.melodic.dlms.db.model.DataCenterZone;
import eu.melodic.dlms.db.repository.DataCenterZoneRepository;
import lombok.RequiredArgsConstructor;

/**
 * Not completed yet
 * 
 */

@RequiredArgsConstructor
public class Algo_DlmsTotalUtility {
	private final DataCenterZoneRepository dataCenterZoneRepository;

	private Set<Integer> fingerprintSet;
	private int maxDepth = 30;
	private String[] graphCurrent;
	private String[] graphProposed;
	private static Map<UndirectedGraph<AtomVertex, BoundEdge>, Set<Integer>> currentFingerPrintMap;
	
	
	/**
	 * Starting method
	 * Not yet completed
	 */
	public double calculateUtility() {
		currentFingerPrintMap = new HashMap<>();
		
		Map<String, String> locZoneMap = readLocZone();
//		List<UndirectedGraph<AtomVertex, BoundEdge>> structureGraphList = readGraph(locZoneMap, graphCurrent);
		
		return 0;
	}

	/**
	 * Read the graph
	 * Not completed yet
	 */
	public List<UndirectedGraph<AtomVertex, BoundEdge>> readGraph(Map<String, String> locZoneMap) {
		List<UndirectedGraph<AtomVertex, BoundEdge>> structureGraphList = new ArrayList<>();

		return null;
	}

	/**
	 * Map all the data centers to zones
	 */

	public Map<String, String> readLocZone() {
		Map<String, String> dcZoneMap = new HashMap<>();
		List<DataCenterZone> dcZoneList = dataCenterZoneRepository.findAll();
		for (DataCenterZone dcZone : dcZoneList)
			dcZoneMap.put(Long.toString(dcZone.getDataCenterId()), String.valueOf(dcZone.getZone()));

		return dcZoneMap;
	}

	/**
	 * Get fingerprint set
	 * 
	 */
	public Set<Integer> getFingerprintSet(UndirectedGraph<AtomVertex, BoundEdge> structureGraph) {
		fingerprintSet = new TreeSet<>();
		Set<AtomVertex> atoms = structureGraph.vertexSet();
		for (AtomVertex a : atoms) {
			dfsAllPathTravel(new ArrayList<>(), null, a, structureGraph);
		}
		return fingerprintSet;
	}

	/**
	 * Depth first search to all path
	 */
	private void dfsAllPathTravel(List<AtomVertex> path, AtomVertex lastVertex, AtomVertex vertex,
			UndirectedGraph<AtomVertex, BoundEdge> structureGraph) {

		List<AtomVertex> nextPath = new ArrayList<>();
		if (lastVertex != null)
			nextPath.add(lastVertex);
		nextPath.add(vertex);

		fingerprintSet.add(Arrays.deepHashCode(nextPath.toArray()));

		List<AtomVertex> neighbors = Graphs.neighborListOf(structureGraph, vertex);
		for (AtomVertex n : neighbors) {
			if (nextPath.size() <= maxDepth && !nextPath.contains(n)) {
				dfsAllPathTravel(nextPath, vertex, n, structureGraph);
			}
		}
	}

}
