package node_candidates;


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
    private Map<Integer, List<Pair<VMConfiguration, GeographicCoordinate>>> vmConfigurationsAndLocations = new HashMap<>();

    public NodeCandidatesPool(NodeCandidates nodeCandidates) {
        parseNodeCandidates(nodeCandidates);
    }

    public List<NumericValueInterface> getProviderDomain() {
        return Arrays.stream(vmConfigurationsAndLocations.keySet().toArray())
                .map(provider -> (NumericValueInterface) new IntegerValue((Integer) provider))
                .distinct().collect(Collectors.toList());
    }

    public List<Pair<VMConfiguration, GeographicCoordinate>> getAllConfigurationsAndLocations() {
        return vmConfigurationsAndLocations.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    private void postVMConfigurationAndLocation(int provider, VMConfiguration configuration, GeographicCoordinate location) {
        if (!vmConfigurationsAndLocations.containsKey(provider)) {
            vmConfigurationsAndLocations.put(provider, new ArrayList<>());
        }
        if (!vmConfigurationsAndLocations.get(provider).contains(new Pair<>(configuration, location))) {
            vmConfigurationsAndLocations.get(provider).add(new Pair<>(configuration, location));
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

    private void parseNodeCandidates(NodeCandidates nodeCandidates) {
        nodeCandidates.get().values().forEach( node -> node.keySet().forEach( provider ->
            node.get(provider).forEach((element) ->
                postVMConfigurationAndLocation(provider,
                        new VMConfiguration(
                                element.getHardware().getCores(),
                                element.getHardware().getRam(),
                                element.getHardware().getDisk().intValue()
                        ),
                        locationFromNodeCandidate(element)
                )))
        );
    }
}
