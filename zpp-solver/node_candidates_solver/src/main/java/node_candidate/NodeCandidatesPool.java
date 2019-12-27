package node_candidate;

import cp_components.PTMover;
import eu.paasage.upperware.metamodel.cp.VariableType;
import nc_wrapper.NCWrapper;
import variable_orderer.VariableTypeOrderer;

import java.util.*;

public class NodeCandidatesPool {
    private NCWrapper ncWrapper;

    private List<String> components;
     /*
        Provider -> VM specification
        NodeCandidates must be sorted lexicographically in ascending order
     */
    private Map<Integer, List<VMConfiguration>> vmConfigurations;
    /*
        Provider -> VM location.
        Coordinates must be sorted lexicographically in ascending order
     */
    private Map<Integer, List<GeographicCoordinate>> vmLocations;

    public NodeCandidatesPool(NCWrapper ncWrapper) {
        this.components = ncWrapper.getComponents();
        this.ncWrapper = ncWrapper;
    }

    public void postVMConfiguration(int provider, VMConfiguration configuration) {
        if (!vmConfigurations.containsKey(provider)) {
            vmConfigurations.put(provider, new ArrayList<VMConfiguration>());
        }
        vmConfigurations.get(provider).add(configuration);
    }

    public void postVMLocation(int provider, GeographicCoordinate location) {
        if (!vmLocations.containsKey(provider)) {
            vmLocations.put(provider, new ArrayList<GeographicCoordinate>());
        }
        vmLocations.get(provider).add(location);
    }

    public void initNodeCandidates() {
        for ( Integer key : vmConfigurations.keySet()) {
            Collections.sort(vmConfigurations.get(key));
        }
        for ( Integer key : vmLocations.keySet()) {
            Collections.sort(vmLocations.get(key));
        }
    }
    private Collection<VMConfiguration> getVMConfigurationOrItsNeighbours(int provider, VMConfiguration conf) {
        if (Collections.binarySearch(vmConfigurations.get(provider), conf) >= 0) {
            return Arrays.asList(conf);
        } else {
            return getVMConfigurationNeighbours(provider, conf);
        }
    }

    private Collection<GeographicCoordinate> getVMLocationOrItsNeighbours(int provider, GeographicCoordinate loc) {
        if (Collections.binarySearch(vmLocations.get(provider), loc) >= 0) {
            return Arrays.asList(loc);
        } else {
            return getVMLocationNeighbours(provider, loc);
        }
    }

    private Collection<VMConfiguration> getVMConfigurationNeighbours(int provider, VMConfiguration conf) {
        int index = Collections.binarySearch(vmConfigurations.get(provider), conf);
        Collection<VMConfiguration> result = new ArrayList<>();
        if(index >= 0) {
            if (index > 0) {
                result.add(vmConfigurations.get(provider).get(index-1));
            }
            if (index + 1 < vmConfigurations.get(provider).size()) {
                result.add(vmConfigurations.get(provider).get(index+1));
            }
        } else {
            index = (-index) - 1;
            if (index > 0) {
                result.add(vmConfigurations.get(provider).get(index-1));
            }
            if (index < vmConfigurations.get(provider).size()) {
                result.add(vmConfigurations.get(provider).get(index));
            }
        }
        return result;
    }

    private Collection<GeographicCoordinate> getVMLocationNeighbours(int provider, GeographicCoordinate loc) {
        Collection<GeographicCoordinate> result = new ArrayList<>();
        int index = Collections.binarySearch(vmLocations.get(provider), loc);
        if(index >= 0) {
            if (index > 0) {
                result.add(vmLocations.get(provider).get(index-1));
            }
            if (index + 1 < vmLocations.get(provider).size()) {
                result.add(vmLocations.get(provider).get(index+1));
            }
        } else {
            index = (-index) - 1;
            if (index > 0) {
                result.add(vmLocations.get(provider).get(index-1));
            }
            if (index < vmLocations.get(provider).size()) {
                result.add(vmLocations.get(provider).get(index));
            }
        }
        return result;
    }


    private VMConfiguration extractVMConfiguration(int component, List<Integer> assignment) {
        int index = component * VariableTypeOrderer.variablesPerComponent;
        return new VMConfiguration(
                assignment.get(index + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES)),
                assignment.get(index + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM)),
                assignment.get(index + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE))
        );
    }

    private GeographicCoordinate extractVMLocation(int component, List<Integer> assignment) {
        int index = component * VariableTypeOrderer.variablesPerComponent;
        return new GeographicCoordinate(
                assignment.get(index + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE)),
                assignment.get(index + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE))
        );
    }

    private int extractProvider(int component, List<Integer> assignment) {
        return assignment.get(VariableTypeOrderer.variablesPerComponent * component);
    }

    private List<Integer> updateComponentConfiguration(List<Integer> assignment, int component,
                                                       int provider, VMConfiguration vmConfiguration,
                                                       GeographicCoordinate vmLocation) {
        int index = component * VariableTypeOrderer.variablesPerComponent;
        List<Integer> newAssignment = new ArrayList<>(assignment);

        newAssignment.set(index, provider);

        newAssignment.set(index + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE),
                vmLocation.getLatitude());
        newAssignment.set(index + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE),
                vmLocation.getLongitude());

        newAssignment.set(index + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES),
                vmConfiguration.getCores());
        newAssignment.set(index + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM),
                vmConfiguration.getRam());
        newAssignment.set(index + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE),
                vmConfiguration.getDisk());

        return newAssignment;
    }

    private Collection<PTMover> getAllVMConfigurationChangeMoves(List<Integer> assignment, int component) {
        Collection<PTMover> result = new ArrayList<>();
        VMConfiguration conf = extractVMConfiguration(component, assignment);
        int provider = extractProvider(component, assignment);
        Collection<VMConfiguration> neighbours = getVMConfigurationNeighbours(provider, conf);
        if (neighbours.isEmpty()) {
            return result;
        }
        for (VMConfiguration n : neighbours) {
            result.add(new PTMover(
                    assignment,
                    updateComponentConfiguration(assignment,
                            component, provider, n, extractVMLocation(component, assignment))
                    ));
        }
        return result;
    }

    private Collection<PTMover> getAllVMLocationChangeMoves(List<Integer> assignment, int component) {
        Collection<PTMover> result = new ArrayList<>();
        GeographicCoordinate loc = extractVMLocation(component, assignment);
        int provider = extractProvider(component, assignment);
        Collection<GeographicCoordinate> neighbours = getVMLocationNeighbours(provider, loc);
        if (neighbours.isEmpty()) {
            return result;
        }
        for (GeographicCoordinate n : neighbours) {
            result.add(new PTMover(
                    assignment,
                    updateComponentConfiguration(assignment,
                            component, provider, extractVMConfiguration(component, assignment), n)
            ));
        }
        return result;
    }

    private Collection<PTMover> getAllCardinalityChangeMoves(List<Integer> assignment, int component) {
        int index = component * VariableTypeOrderer.variablesPerComponent
                + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY);
        int maxCardinality = ncWrapper.getMaxValue(index);
        int minCardinality = ncWrapper.getMinValue(index);
        Collection<PTMover> result = new ArrayList<>();
        if (assignment.get(index) < maxCardinality) {
            List<Integer> newAssignment = new ArrayList<>(assignment);
            newAssignment.set(index, assignment.get(index) + 1);
            result.add(new PTMover(assignment, newAssignment));
        }
        if (assignment.get(index) > minCardinality) {
            List<Integer> newAssignment = new ArrayList<>(assignment);
            newAssignment.set(index, assignment.get(index) - 1);
            result.add(new PTMover(assignment, newAssignment));
        }
        return result;
    }

    private Collection<PTMover> getAllProviderChangeMoves(List<Integer> assignment, int component, int provider) {
        Collection<PTMover> result = new ArrayList<>();
        Collection<VMConfiguration> confs =
                getVMConfigurationOrItsNeighbours(provider, extractVMConfiguration(component, assignment));
        for (VMConfiguration conf : confs) {
            Collection<GeographicCoordinate> locs = getVMLocationOrItsNeighbours( provider, extractVMLocation(component, assignment));
            for (GeographicCoordinate loc : locs) {
                result.add(new PTMover(
                        assignment,
                        updateComponentConfiguration(
                                assignment,
                                component,
                                provider,
                                conf,
                                loc
                        )
                ));
            }
        }
        return result;
    }

    private Collection<PTMover> getAllProviderChangeMoves(List<Integer> assignment, int component) {
        int provider = extractProvider(component, assignment);
        int maxProvider = ncWrapper.getMaxValue(component * VariableTypeOrderer.variablesPerComponent);
        int minProvider = ncWrapper.getMinValue(component * VariableTypeOrderer.variablesPerComponent);

        return null;
    }

    public Collection<PTMover> getAllMovesComponent(List<Integer> assignment, int component) {
        Collection<PTMover> result = getAllVMConfigurationChangeMoves(assignment, component);
        result.addAll(getAllVMLocationChangeMoves(assignment, component));
        result.addAll(getAllCardinalityChangeMoves(assignment, component));
        result.addAll(getAllProviderChangeMoves(assignment, component));
        return result;
    }

    public List<PTMover> getAllMoves(List<Integer> assignment) {
        List<PTMover> moves = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            moves.addAll(getAllMovesComponent(assignment, i));
        }
        return moves;
    }


}
