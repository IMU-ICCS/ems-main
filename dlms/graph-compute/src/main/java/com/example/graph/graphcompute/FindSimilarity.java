package com.example.graph.graphcompute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.web.client.RestTemplate;

import com.example.graph.graphcompute.model.AtomVertex;
import com.example.graph.graphcompute.model.BoundEdge;
import com.example.graph.graphcompute.model.LocationZone;
import com.example.graph.graphcompute.util.Jaccard;

// need to look at the hashmap storing graphs
public class FindSimilarity {
	private static final String URL_DCZONE = "http://localhost:8090/input/dc";
//	private static String key = "";
	private static Map<String, Set<Integer>> fingerPrintMap = new HashMap<>();
	private static Map<UndirectedGraph<AtomVertex, BoundEdge>, Set<Integer>> currentFingerPrintMap = new HashMap<>();

	public static void main(String[] args) {
		Map<String, String> locZoneMap = readLocZone();
		currentFingerPrintMap = new HashMap<>();
		List<UndirectedGraph<AtomVertex, BoundEdge>> structureGraphList = readGraph(locZoneMap);

		// compute similarity between all graph pairs
		for (int i = 0; i < structureGraphList.size(); i++) {
			Set<Integer> fingerprints1 = currentFingerPrintMap.get(structureGraphList.get(i));
			for (int j = i + 1; j < structureGraphList.size(); j++) {
				Set<Integer> fingerprints2 = currentFingerPrintMap.get(structureGraphList.get(j));
				double similarity = Jaccard.calculateSimilarity(fingerprints1, fingerprints2) * 100.;
				System.out.println("Similarity between Graph" + i + " and Graph" + j + " is " + similarity + "%");
			}
		}
	}

	private static Set<Integer> fingerprintSet;

	public static Set<Integer> getFingerprintSet(UndirectedGraph<AtomVertex, BoundEdge> structureGraph) {
		fingerprintSet = new TreeSet<>();
		Set<AtomVertex> atoms = structureGraph.vertexSet();
		for (AtomVertex a : atoms) {
			dfsAllPathTravel(new ArrayList<>(), null, a, structureGraph);
		}
		System.out.println("\n");
		return fingerprintSet;
	}

	private static void dfsAllPathTravel(List<AtomVertex> path, AtomVertex lastVertex, AtomVertex vertex,
			UndirectedGraph<AtomVertex, BoundEdge> structureGraph) {

		List<AtomVertex> nextPath = new ArrayList<>();
		if (lastVertex != null)
			nextPath.add(lastVertex);
		nextPath.add(vertex);

		fingerprintSet.add(Arrays.deepHashCode(nextPath.toArray()));

		List<AtomVertex> neighbors = Graphs.neighborListOf(structureGraph, vertex);
		for (AtomVertex n : neighbors) {
			if (nextPath.size() <= 12 && !nextPath.contains(n)) {
				dfsAllPathTravel(nextPath, vertex, n, structureGraph);
			}
		}
	}

	// read graphs in some format and store
	public static List<UndirectedGraph<AtomVertex, BoundEdge>> readGraph(Map<String, String> locZoneMap) {
		List<UndirectedGraph<AtomVertex, BoundEdge>> structureGraphList = new ArrayList<>();
		// first graph
		List<String> keyList = new ArrayList<String>();
//		String[] graph1 = { "singapore", "ws", "bs", "ds2", "ny", "ds1", "ohio" };
		String[] graph1 = { "11", "ws", "bs", "ds2", "ny", "ds1", "ohio" };
		for (String item : graph1)
			keyList.add(item);
		String[] graph1_org = graph1.clone();

		graph1 = modifyGraphLocation(graph1, locZoneMap);
		UndirectedGraph<AtomVertex, BoundEdge> structureGraph = new SimpleWeightedGraph<>(BoundEdge.class);
		List<AtomVertex> vertices = new ArrayList<>();
		for (String item : graph1) {
			AtomVertex atomVertex = new AtomVertex(item);
			vertices.add(atomVertex);
			structureGraph.addVertex(atomVertex);
		}
		structureGraph.addEdge(vertices.get(0), vertices.get(1), new BoundEdge(0));
		structureGraph.addEdge(vertices.get(1), vertices.get(2), new BoundEdge(0));
		structureGraph.addEdge(vertices.get(2), vertices.get(3), new BoundEdge(0));
		structureGraph.addEdge(vertices.get(3), vertices.get(4), new BoundEdge(0));
		structureGraph.addEdge(vertices.get(1), vertices.get(5), new BoundEdge(0));
		structureGraph.addEdge(vertices.get(5), vertices.get(6), new BoundEdge(0));
		structureGraphList.add(structureGraph);

		keyList.add(sortTwoItems(graph1_org[0], graph1_org[1]));
		keyList.add(sortTwoItems(graph1_org[1], graph1_org[2]));
		keyList.add(sortTwoItems(graph1_org[2], graph1_org[3]));
		keyList.add(sortTwoItems(graph1_org[3], graph1_org[4]));
		keyList.add(sortTwoItems(graph1_org[1], graph1_org[5]));
		keyList.add(sortTwoItems(graph1_org[5], graph1_org[6]));
		Collections.sort(keyList);

		String key = "";
		Set<Integer> fingerprints1;
		for (String item : keyList)
			key += item;
		if (fingerPrintMap.containsKey(key))
			fingerprints1 = fingerPrintMap.get(key);
		else {
			fingerprints1 = getFingerprintSet(structureGraph);
			fingerPrintMap.put(key, fingerprints1);
		}
		currentFingerPrintMap.put(structureGraph, fingerprints1);

		// second graph
		keyList = new ArrayList<String>();
//		String[] graph2 = { "ohio", "ds1", "ny", "ds2", "singapore", "ws", "bs" };
		String[] graph2 = { "ohio", "ds1", "ny", "ds2", "23", "ws", "bs" };
		for (String item : graph2)
			keyList.add(item);
		String[] graph2_org = graph2.clone();

		graph2 = modifyGraphLocation(graph2, locZoneMap);
		UndirectedGraph<AtomVertex, BoundEdge> structureGraph2 = new SimpleWeightedGraph<>(BoundEdge.class);
		vertices = new ArrayList<>();
		for (String item : graph2) {
			AtomVertex atomVertex = new AtomVertex(item);
			vertices.add(atomVertex);
			structureGraph2.addVertex(atomVertex);
		}
		structureGraph2.addEdge(vertices.get(0), vertices.get(1), new BoundEdge(0));
		structureGraph2.addEdge(vertices.get(2), vertices.get(3), new BoundEdge(0));
		structureGraph2.addEdge(vertices.get(4), vertices.get(5), new BoundEdge(0));
		structureGraph2.addEdge(vertices.get(5), vertices.get(6), new BoundEdge(0));
		structureGraph2.addEdge(vertices.get(5), vertices.get(1), new BoundEdge(0));
		structureGraph2.addEdge(vertices.get(3), vertices.get(6), new BoundEdge(0));
		structureGraphList.add(structureGraph2);

		keyList.add(sortTwoItems(graph2_org[0], graph2_org[1]));
		keyList.add(sortTwoItems(graph2_org[2], graph2_org[3]));
		keyList.add(sortTwoItems(graph2_org[4], graph2_org[5]));
		keyList.add(sortTwoItems(graph2_org[5], graph2_org[6]));
		keyList.add(sortTwoItems(graph2_org[5], graph2_org[1]));
		keyList.add(sortTwoItems(graph2_org[3], graph2_org[6]));
		Collections.sort(keyList);

		key = "";
		Set<Integer> fingerprints2;
		for (String item : keyList)
			key += item;
		if (fingerPrintMap.containsKey(key))
			fingerprints2 = fingerPrintMap.get(key);
		else {
			fingerprints2 = getFingerprintSet(structureGraph2);
			fingerPrintMap.put(key, fingerprints2);
		}
		currentFingerPrintMap.put(structureGraph2, fingerprints2);

		return structureGraphList;
	}

	public static String sortTwoItems(String item1, String item2) {
		String ret = "";
		String[] items = { item1, item2 };
		Arrays.sort(items);
		ret = items[0] + "," + items[1];
		return ret;
	}

	public static String[] modifyGraphLocation(String[] graph, Map<String, String> locZoneMap) {
		String[] retGraph = new String[graph.length];
		for (int i = 0; i < graph.length; i++) {
			String val = graph[i];
			if (locZoneMap.containsKey(val))
				val = locZoneMap.get(val);
			retGraph[i] = val;
		}
		return retGraph;
	}

	public static Map<String, String> readLocZone() {
		Map<String, String> locZoneMap = new HashMap<String, String>();

		RestTemplate restTemplate = new RestTemplate();
		// Send request with GET method and default Headers
		LocationZone[] locationZoneArray;
		locationZoneArray = restTemplate.getForObject(URL_DCZONE, LocationZone[].class);

		for (LocationZone locationZone : locationZoneArray)
			locZoneMap.put(locationZone.getId(), locationZone.getZone());

		return locZoneMap;
	}

}
