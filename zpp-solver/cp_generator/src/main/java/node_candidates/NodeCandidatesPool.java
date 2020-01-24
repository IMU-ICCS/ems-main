package node_candidates;


import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.cache.NodeCandidates;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;
import nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;


import java.util.*;
import java.util.stream.Collectors;


public class NodeCandidatesPool {
    private Map<Integer, List<VMConfiguration>> vmConfigurations;
    private Map<Integer, List<GeographicCoordinate>> vmLocations;

    public NodeCandidatesPool(NodeCandidates nodeCandidates) {
        vmConfigurations = new HashMap<>();
        vmLocations = new HashMap<>();
        parseNodeCandidates(nodeCandidates);
    }
    private void postVMLocation(int provider, GeographicCoordinate loc) {
        if (!vmLocations.containsKey(provider)) {
            vmLocations.put(provider, new ArrayList<>());
        }
        vmLocations.get(provider).add(loc);
    }

    private void postVMConfiguration(int provider, VMConfiguration conf) {
        if (!vmConfigurations.containsKey(provider)) {
            vmConfigurations.put(provider, new ArrayList<>());
        }
        vmConfigurations.get(provider).add(conf);
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
            node.get(provider).forEach((element) -> {
                postVMLocation(provider,
                        locationFromNodeCandidate(element));
                postVMConfiguration(provider,
                        new VMConfiguration(element.getHardware().getCores(), element.getHardware().getRam(),
                                element.getHardware().getDisk().intValue()));
            }))
        );
    }

    private List<NumericValueInterface> getProviderDomain() {
        return Arrays.stream(vmConfigurations.keySet().toArray()).map(
                provider -> (NumericValueInterface) new IntegerValue((Integer) provider)
        ).distinct().collect(Collectors.toList());
    }

    private List<NumericValueInterface> getGeographicCoordinateDomain(VariableType type) {
        return Arrays.stream(vmLocations.values().toArray()).map(
                list -> ((List<GeographicCoordinate>) list).stream().map(
                        location -> type == VariableType.LONGITUDE? new DoubleValue(location.getLongitude()) : new DoubleValue(location.getLatitude())
                ).collect(Collectors.toList())
        ).flatMap(l -> l.stream()).distinct().collect(Collectors.toList());
    }

    private List<NumericValueInterface> getVMConfigurationDomain(VariableType type) {
        return Arrays.stream(vmConfigurations.values().toArray()).map(
                list -> ((List<VMConfiguration>) list).stream().map(
                        configuration ->{
                                switch(type) {
                                    case CORES:
                                        return new LongValue(configuration.getCores());
                                    case RAM:
                                        return new LongValue(configuration.getRam());
                                    default: // STORAGE
                                        return new DoubleValue(configuration.getDisk());
                                }}
                ).collect(Collectors.toList())
        ).flatMap(l -> l.stream()).distinct().collect(Collectors.toList());
    }

    public List<NumericValueInterface> getDomain(VariableType type) {
        if (type == VariableType.PROVIDER) {
            return getProviderDomain();
        } else if (type == VariableType.LATITUDE || type == VariableType.LONGITUDE) {
            return getGeographicCoordinateDomain(type);
        } else {
            return getVMConfigurationDomain(type);
        }
    }

    public List<VMConfiguration> getAllConfigurations() {
        return vmConfigurations.keySet().stream().map(provider -> vmConfigurations.get(provider))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<GeographicCoordinate> getAllLocations() {
        return vmLocations.keySet().stream().map(provider -> vmLocations.get(provider))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
}
