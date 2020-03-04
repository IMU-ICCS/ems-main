package eu.melodic.upperware.cp_sampler.node_candidates;


import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;


public class NodeCandidatesPool {
    private Map<Integer, List<Pair<VMConfiguration, GeographicCoordinate>>> vmConfigurationsAndLocations;

    public NodeCandidatesPool(NodeCandidates nodeCandidates) {
        vmConfigurationsAndLocations = parseNodeCandidates(nodeCandidates);
    }

    public List<NumericValueInterface> getProviderDomain() {
        return Arrays.stream(vmConfigurationsAndLocations.keySet().toArray())
                .map(provider -> (NumericValueInterface) new IntegerValue((Integer) provider))
                .distinct().collect(Collectors.toList());
    }

    public List<Pair<VMConfiguration, GeographicCoordinate>> getAllConfigurationsAndLocations() {
        return vmConfigurationsAndLocations.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    private Pair<VMConfiguration, GeographicCoordinate> extractConfigurationLocationFromNodeCandidate(NodeCandidate nodeCandidate) {
        return new Pair<>(
                new VMConfiguration(
                    nodeCandidate.getHardware().getCores(),
                    nodeCandidate.getHardware().getRam(),
                    nodeCandidate.getHardware().getDisk().intValue()
                ),
                locationFromNodeCandidate(nodeCandidate));
    }

    private void postVMConfigurationAndLocation(Map<Integer, List<Pair<VMConfiguration, GeographicCoordinate>>> nodes,
                                                int provider, Pair<VMConfiguration, GeographicCoordinate> VMData) {
        if (!nodes.containsKey(provider)) {
            nodes.put(provider, new ArrayList<>());
        }
        if (!nodes.get(provider).contains(VMData)) {
            nodes.get(provider).add(VMData);
        }
    }

    private GeographicCoordinate locationFromNodeCandidate(NodeCandidate nodeCandidate) {
        if (nodeCandidate.getLocation() == null || nodeCandidate.getLocation().getGeoLocation() == null) {
            return new GeographicCoordinate(100, 100);
        } else {
            return new GeographicCoordinate(
                    (long)(100 * nodeCandidate.getLocation().getGeoLocation().getLatitude()),
                    (long)(100 *nodeCandidate.getLocation().getGeoLocation().getLongitude())
            );
        }
    }

    private Map<Integer, List<Pair<VMConfiguration, GeographicCoordinate>>> parseNodeCandidates(NodeCandidates nodeCandidates) {
        Map<Integer, List<Pair<VMConfiguration, GeographicCoordinate>>> parsedNodes = new HashMap<>();
        nodeCandidates.get().values().forEach(node -> node.keySet().forEach( provider ->
            node.get(provider).forEach((element) ->
                postVMConfigurationAndLocation(parsedNodes, provider, extractConfigurationLocationFromNodeCandidate(element)
                )))
        );
        return parsedNodes;
    }
}
