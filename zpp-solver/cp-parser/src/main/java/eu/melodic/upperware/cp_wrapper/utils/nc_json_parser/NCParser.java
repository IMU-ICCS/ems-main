package eu.melodic.upperware.cp_wrapper.utils.nc_json_parser;

import com.google.gson.Gson;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import io.github.cloudiator.rest.model.NodeCandidate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NCParser {
    private Gson gson = new Gson();
    private CacheService<NodeCandidates> filecacheService = new FilecacheService();

    public NodeCandidates parse(String pathToJSON, Collection<String> componentNames) throws FileNotFoundException {
        List<NodeCandidate> nodes = Arrays.asList(gson.fromJson(new FileReader(pathToJSON), NodeCandidate[].class));
        List<String> providers = nodes.parallelStream().map(node -> getNodeProvider(node)).distinct().collect(Collectors.toList());
        Map<String, Map<Integer, List<NodeCandidate>>> candidates = new HashMap<>();
        componentNames.forEach(component -> candidates.put(component, getNodesForComponent(nodes, providers)));
        return NodeCandidates.of(candidates);
    }

    public void saveNodeCandidatesToFile(NodeCandidates nodeCandidates, String filePath) {
        filecacheService.store(filePath, nodeCandidates);
    }

    private String getNodeProvider(NodeCandidate node) {
        return node.getCloud().getApi().getProviderName();
    }

    private Map<Integer, List<NodeCandidate>> getNodesForComponent(List<NodeCandidate> nodes, List<String> providers) {
        Map<Integer, List<NodeCandidate>> result = new HashMap<>();
        IntStream.range(0, providers.size()).forEach(index -> result.put(index, new ArrayList<>()));
        nodes.forEach(node -> {
            int provider = providers.indexOf(getNodeProvider(node));
            result.get(provider).add(node);
        });
        return result;
    }
}

