package node_candidate;
/*
    This class uses node candidates to provide moves in the search space.
    (Move is defined by cp_components.PTMover class).
    Variables corresponding to one component are split into 4 independent parts:
    - cardinality
    - GeographicLocation
    - VMConfiguration (triple cores, ram, storage)
    - provider

    As a result some combinations may not be actually available in the nodes candidates (because
    longitude and latitude are treated as independent from VMConfiguration).
    Our motivation for the preceding is the ease of implementation.
    There are 4 types of moves:
    - cardinality - decrease / increase one component cardinality by one (if permitted by domain)
    - VMConfiguration change - change VMConfiguration of one component to one of its neighbours -
        a neighbour of a configuration is a direct predecessor or successor of the configuration in
        lexicographical order (by triple (cores, ram, storage)).
    - GeographicLocation change - analogously as in VMConfiguration
    - Provider change - change of provider, if either GeographicLocation or VMConfiguration is not
        available for the new provider, change it to its neighbour.

    Posting node candidates:
    Configurations and locations are posted separately using postVMConfiguration, postVMLocation methods.
    We assume that if provider has at least one VMConfiguration then it also has at least one location and vice-versa.
    Once all node candidates have been posted, initNodeCandidates method must be called.

    Masks:
    Some of the VMConfigurations may not belong to a given component's domain. For each component we
    iterate through available configurations and remember whether given configuration is feasible.
    This information is stored in vmConfigurationMasks. Masks are applied to configuration lists (i.e.
    infeasible configuration are ignored) whenever search for
    neighbours is to be conducted.
    Analogous masks are kept for GeographicCoordinates.
 */
import cp_components.PTMover;
import cp_components.PTSolution;
import eu.paasage.upperware.metamodel.cp.VariableType;
import nc_wrapper.DomainProvider;
import node_candidate.node_candidate_element.GeographicCoordinate;
import node_candidate.node_candidate_element.VMConfiguration;
import variable_orderer.ComponentVariableOrderer;
import variable_orderer.VariableTypeOrderer;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class NodeCandidatesPool {
    private DomainProvider domainHandler;
    private List<String> components;
     /*
        Provider -> VM specification
        Must be sorted lexicographically
     */
    private Map<Integer, List<VMConfiguration>> vmConfigurations;
    /*
        Provider -> VM location.
        Must be sorted lexicographically
     */
    private Map<Integer, List<GeographicCoordinate>> vmLocations;
    /*
        component -> provider -> True if configuration is in domain
     */
    private Map<Integer, Map<Integer, List<Boolean>>> vmConfigurationMasks;
    /*
            component -> provider -> True if location is in domain
    */
    private Map<Integer, Map<Integer, List<Boolean>>> vmLocationMasks;

    public NodeCandidatesPool(DomainProvider domainHandler, List<String> components) {
        this.components = components;
        this.domainHandler = domainHandler;
        vmConfigurations = new HashMap<>();
        vmLocations = new HashMap<>();
    }

    public void postVMConfiguration(int provider, VMConfiguration configuration) {
        if (!vmConfigurations.containsKey(provider)) {
            vmConfigurations.put(provider, new ArrayList<>());
        }
        vmConfigurations.get(provider).add(configuration);
    }

    public void postVMLocation(int provider, GeographicCoordinate location) {
        if (!vmLocations.containsKey(provider)) {
            vmLocations.put(provider, new ArrayList<>());
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
        fillMasks();
    }

    private void fillMasks() {
        vmConfigurationMasks = new HashMap<>();
        vmLocationMasks = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            vmConfigurationMasks.put(i, getConfigurationMask(i));
            vmLocationMasks.put(i, getLocationMask(i));
        }
    }

    private Collection<VMConfiguration> getVMConfigurationOrItsNeighbours(int provider, VMConfiguration conf, int component) {
        if (!vmConfigurations.containsKey(provider)) {
            return new ArrayList<>();
        }
        if (Collections.binarySearch(vmConfigurations.get(provider), conf) >= 0) {
            return Arrays.asList(conf);
        } else {
            return getVMConfigurationNeighbours(provider, conf, component);
        }
    }

    public List<PTMover> getAllMoves(PTSolution assignment) {
        return IntStream.range(0, components.size()).mapToObj(
                component -> getAllMovesComponent(assignment, component)
        ) .collect(toList()).stream().flatMap(l -> l.stream()).collect(toList());
    }

    private Collection<GeographicCoordinate> getVMLocationOrItsNeighbours(int provider, GeographicCoordinate loc, int component) {
        if (!vmLocations.containsKey(provider)) {
            return new ArrayList<>();
        }
        if (Collections.binarySearch(vmLocations.get(provider), loc) >= 0) {
            return Arrays.asList(loc);
        } else {
            return getVMLocationNeighbours(provider, loc, component);
        }
    }

    private <T extends Comparable<T>> Collection<T> getElementNeighbours(List<T> list, T element) {
        int index = Collections.binarySearch(list, element);
        Collection<T> result = new ArrayList<>();
        boolean notFoundInList = index < 0;
        if (notFoundInList) {
            index = (-index) - 1;
        }
        if (index > 0) {
            result.add(list.get(index - 1));
        }
        if (notFoundInList && index < list.size()) {
            result.add(list.get(index));
        } else if (!notFoundInList && index + 1 < list.size()) {
            result.add(list.get(index + 1));
        }
        return result;
    }

    private <T extends Comparable<T>> List<T> applyMaskToList(List<T> list, List<Boolean> mask) {
        return list.stream().filter(
                element -> mask.get(list.indexOf(element))
        ).collect(toList());
    }

    private <T extends Comparable<T> > Collection<T> getNeighbours(List<T> list, T element, List<Boolean> mask) {
        if (mask.size() !=list.size()) {
            throw new RuntimeException("Mask and list must be of equal length");
        }
       return getElementNeighbours(applyMaskToList(list, mask), element);
    }

    private Collection<VMConfiguration> getVMConfigurationNeighbours(int provider, VMConfiguration configuration, int component) {
        if (!vmConfigurations.containsKey(provider)) {
            return new ArrayList<>();
        }
        return getNeighbours(vmConfigurations.get(provider), configuration, vmConfigurationMasks.get(component).get(provider));
    }

    private Collection<GeographicCoordinate> getVMLocationNeighbours(int provider, GeographicCoordinate location, int component) {
        if (!vmLocations.containsKey(provider)) {
            return new ArrayList<>();
        }
        return getNeighbours(vmLocations.get(provider), location, vmLocationMasks.get(component).get(provider));
    }

    private Collection<PTMover> getAllVMConfigurationChangeMoves(PTSolution assignment, int component) {
        int provider = assignment.extractProvider(component);
        return getVMConfigurationNeighbours(provider, assignment.extractVMConfiguration(component), component).stream()
                .map( configuration ->
                            new PTMover(
                                    assignment,
                                    assignment.updateComponentConfiguration(
                                            component, provider, configuration, assignment.extractVMLocation(component),
                                            assignment.extractCardinality(component))
                            )
                ).collect(toList());
    }

    private Collection<PTMover> getAllVMLocationChangeMoves(PTSolution assignment, int component) {
        int provider = assignment.extractProvider(component);
        return getVMLocationNeighbours(provider, assignment.extractVMLocation(component), component).stream()
                .map(location ->
                    new PTMover(
                        assignment,
                        assignment.updateComponentConfiguration(
                            component, provider, assignment.extractVMConfiguration(component), location,
                            assignment.extractCardinality(component))
                )
        ).collect(toList());
    }

    private Collection<PTMover> getAllCardinalityChangeMoves(PTSolution assignment, int component) {
        int index = component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY);
        Collection<PTMover> result = new ArrayList<>();
        if (assignment.extractCardinality(component) < domainHandler.getMaxValue(index).getIntValue()) {
            result.add(new PTMover(assignment, assignment.updateComponentConfiguration(
                    component, assignment.extractProvider(component), assignment.extractVMConfiguration(component),
                    assignment.extractVMLocation(component), assignment.extractCardinality(component) + 1))
            );
        }
        if (assignment.extractCardinality(component) > domainHandler.getMinValue(index).getIntValue()) {
            result.add(new PTMover(assignment, assignment.updateComponentConfiguration(
                    component, assignment.extractProvider(component), assignment.extractVMConfiguration(component),
                    assignment.extractVMLocation(component), assignment.extractCardinality(component) - 1))
            );
        }
        return result;
    }

    private Collection<PTMover> getAllProviderChangeMoves(PTSolution assignment, int component, int provider) {
        return getVMConfigurationOrItsNeighbours(provider, assignment.extractVMConfiguration(component), component).stream()
                .map( configuration ->
                            getVMLocationOrItsNeighbours( provider, assignment.extractVMLocation(component), component).stream()
                                    .map( location ->
                                                    new PTMover(
                                                    assignment,
                                                    assignment.updateComponentConfiguration(
                                                            component,
                                                            provider,
                                                            configuration,
                                                            location,
                                                            assignment.extractCardinality(component)
                                                    ))
                                    ).collect(toList())
                ).flatMap(List::stream).collect(toList());
    }

    private Collection<PTMover> getAllProviderChangeMoves(PTSolution assignment, int component) {
        int provider = assignment.extractProvider(component);
        int maxProvider = domainHandler.getMaxValue(component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT).getIntValue();
        int minProvider = domainHandler.getMinValue(component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT).getIntValue();
        Collection<PTMover> result = new ArrayList<>();
        if (provider < maxProvider) {
            result.addAll(getAllProviderChangeMoves(assignment, component, provider + 1));
        }
        if (provider > minProvider) {
            result.addAll(getAllProviderChangeMoves(assignment, component, provider - 1));
        }
        return result;
    }

    private Collection<PTMover> getAllMovesComponent(PTSolution assignment, int component) {
        Collection<PTMover> result = getAllVMConfigurationChangeMoves(assignment, component);
        result.addAll(getAllVMLocationChangeMoves(assignment, component));
        result.addAll(getAllCardinalityChangeMoves(assignment, component));
        result.addAll(getAllProviderChangeMoves(assignment, component));
        return result;
    }

    public PTSolution generateRandom(Random random) {
        PTSolution solution = new PTSolution(new HashMap<>());
        for (int component = 0; component < components.size(); component++) {
            int provider = getRandomProvider(random);
            solution = solution.updateComponentConfiguration(
                    component,
                    provider,
                    vmConfigurations.get(provider).get(random.nextInt(vmConfigurations.get(provider).size())),
                    vmLocations.get(provider).get(random.nextInt(vmLocations.get(provider).size())),
                    getRandomCardinality(random, component)
            );
        }
        return solution;
    }

    private int getRandomProvider(Random random) {
        Set<Integer> provs = vmConfigurations.keySet();
        return provs.stream().skip(random.nextInt(provs.size())).findFirst().orElse(null);
    }

    private int getRandomCardinality(Random random, int component) {
        int index = component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY);
        int max = domainHandler.getMaxValue(index).getIntValue();
        int min = domainHandler.getMinValue(index).getIntValue();
        if (max == min) return min;
        return min + random.nextInt(max-min);
    }

    private boolean isInDomain(VariableValueKeeperInterface values, int component) {
        return values.getValues(component).stream().allMatch(
                value -> domainHandler.isInDomain(value.getValue0(), value.getValue1())
        );
    }

    private <T extends VariableValueKeeperInterface> Map<Integer, List<Boolean>> getMask(int component, Map<Integer, List<T>> map) {
        Map<Integer, List<Boolean>> result = new HashMap<>();
        map.keySet().forEach(
              key -> {
                  result.put(key, new ArrayList<>());
                  map.get(key).forEach( value -> result.get(key).add(isInDomain(value, component)));
              }
        );
        return result;
    }

    private Map<Integer, List<Boolean>> getConfigurationMask(int component) {
        return getMask(component, vmConfigurations);
    }

    private Map<Integer, List<Boolean>> getLocationMask(int component) {
       return getMask(component, vmLocations);
    }
}
