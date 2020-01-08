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
import cp_wrapper.utils.numeric_value_impl.DoubleValue;
import cp_wrapper.utils.numeric_value_impl.IntegerValue;
import cp_wrapper.utils.numeric_value_impl.LongValue;
import eu.paasage.upperware.metamodel.cp.VariableType;
import nc_wrapper.DomainProvider;
import variable_orderer.VariableTypeOrderer;

import java.util.*;

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

    private <T extends Comparable<T> > Collection<T> getNeighbours(List<T> list, T element, List<Boolean> mask) {
        if (mask.size() !=list.size()) {
            throw new RuntimeException("Mask and list must be of equal length");
        }
        int index = Collections.binarySearch(list, element);
        Collection<T> result = new ArrayList<>();
        if(index >= 0) {
            if (index > 0) {
                int i = index - 1;
                while(i >= 0 && !mask.get(i)) i--;
                if (i >= 0)  {
                    result.add(list.get(i));
                }
            }
            if (index + 1 < list.size()) {
                int i = index + 1;
                while (i < list.size() && !mask.get(i)) i++;
                if (i < list.size()) {
                    result.add(list.get(i));
                }
            }
        } else {
            index = (-index) - 1;
            if (index > 0) {
                int i = index - 1;
                while(i >= 0 && !mask.get(i)) i--;
                if (i >= 0)  {
                    result.add(list.get(i));
                }
            }
            if (index < list.size()) {
                int i = index;
                while (i < list.size() && !mask.get(i)) i++;
                if (i < list.size()) {
                    result.add(list.get(i));
                }
            }
        }
        return result;
    }

    private Collection<VMConfiguration> getVMConfigurationNeighbours(int provider, VMConfiguration conf, int component) {
        if (!vmConfigurations.containsKey(provider)) {
            return new ArrayList<>();
        }
        return getNeighbours(vmConfigurations.get(provider), conf, vmConfigurationMasks.get(component).get(provider));
    }

    private Collection<GeographicCoordinate> getVMLocationNeighbours(int provider, GeographicCoordinate loc, int component) {
        if (!vmLocations.containsKey(provider)) {
            return new ArrayList<>();
        }
        return getNeighbours(vmLocations.get(provider), loc, vmLocationMasks.get(component).get(provider));
    }

    private Collection<PTMover> getAllVMConfigurationChangeMoves(PTSolution assignment, int component) {
        Collection<PTMover> result = new ArrayList<>();
        VMConfiguration conf = assignment.extractVMConfiguration(component);
        int provider = assignment.extractProvider(component);
        Collection<VMConfiguration> neighbours = getVMConfigurationNeighbours(provider, conf, component);
        for (VMConfiguration n : neighbours) {
            result.add(new PTMover(
                    assignment,
                    assignment.updateComponentConfiguration(
                            component, provider, n, assignment.extractVMLocation(component),
                            assignment.extractCardinality(component))
                    ));
        }
        return result;
    }

    private Collection<PTMover> getAllVMLocationChangeMoves(PTSolution assignment, int component) {
        Collection<PTMover> result = new ArrayList<>();
        GeographicCoordinate loc = assignment.extractVMLocation(component);
        int provider = assignment.extractProvider(component);
        Collection<GeographicCoordinate> neighbours = getVMLocationNeighbours(provider, loc, component);
        for (GeographicCoordinate n : neighbours) {
            result.add(new PTMover(
                    assignment,
                    assignment.updateComponentConfiguration(
                            component, provider, assignment.extractVMConfiguration(component), n,
                            assignment.extractCardinality(component))
            ));
        }
        return result;
    }

    private Collection<PTMover> getAllCardinalityChangeMoves(PTSolution assignment, int component) {
        int index = component * VariableTypeOrderer.variablesPerComponent
                + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY);
        Collection<PTMover> result = new ArrayList<>();
        if (assignment.extractCardinality(component) < ((IntegerValue) domainHandler.getMaxValue(index)).getValue()) {
            result.add(new PTMover(assignment, assignment.updateComponentConfiguration(
                    component, assignment.extractProvider(component), assignment.extractVMConfiguration(component),
                    assignment.extractVMLocation(component), assignment.extractCardinality(component) + 1))
            );
        }
        if (assignment.extractCardinality(component) > ((IntegerValue) domainHandler.getMinValue(index)).getValue()) {
            result.add(new PTMover(assignment, assignment.updateComponentConfiguration(
                    component, assignment.extractProvider(component), assignment.extractVMConfiguration(component),
                    assignment.extractVMLocation(component), assignment.extractCardinality(component) - 1))
            );
        }
        return result;
    }

    private Collection<PTMover> getAllProviderChangeMoves(PTSolution assignment, int component, int provider) {
        Collection<PTMover> result = new ArrayList<>();
        Collection<VMConfiguration> confs =
                getVMConfigurationOrItsNeighbours(provider, assignment.extractVMConfiguration(component), component);
        for (VMConfiguration conf : confs) {
            Collection<GeographicCoordinate> locs =
                    getVMLocationOrItsNeighbours( provider, assignment.extractVMLocation(component), component);
            for (GeographicCoordinate loc : locs) {
                result.add(new PTMover(
                        assignment,
                        assignment.updateComponentConfiguration(
                                component,
                                provider,
                                conf,
                                loc,
                                assignment.extractCardinality(component)
                        )
                ));
            }
        }
        return result;
    }

    private Collection<PTMover> getAllProviderChangeMoves(PTSolution assignment, int component) {
        int provider = assignment.extractProvider(component);
        int maxProvider = ((IntegerValue) domainHandler.getMaxValue(component * VariableTypeOrderer.variablesPerComponent)).getValue();
        int minProvider = ((IntegerValue) domainHandler.getMinValue(component * VariableTypeOrderer.variablesPerComponent)).getValue();
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

    public List<PTMover> getAllMoves(PTSolution assignment) {
        List<PTMover> moves = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            moves.addAll(getAllMovesComponent(assignment, i));
        }
        return moves;
    }

    private int getRandomProvider(Random random) {
        Set<Integer> provs = vmConfigurations.keySet();
        return provs.stream().skip(random.nextInt(provs.size())).findFirst().orElse(null);
    }

    private int getRandomCardinality(Random random, int component) {
        int index = component * VariableTypeOrderer.variablesPerComponent
                + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY);
        int max = ((IntegerValue) domainHandler.getMaxValue(index)).getValue();
        int min = ((IntegerValue) domainHandler.getMinValue(index)).getValue();
        if (max == min) return min;
        return min + random.nextInt(max-min);
    }

    public PTSolution generateRandom(Random random) {
        PTSolution sol = new PTSolution(new HashMap<>());
        for (int i = 0; i < components.size(); i++) {
            int prov = getRandomProvider(random);
            sol = sol.updateComponentConfiguration(
                    i,
                    prov,
                    vmConfigurations.get(prov).get(random.nextInt(vmConfigurations.get(prov).size())),
                    vmLocations.get(prov).get(random.nextInt(vmLocations.get(prov).size())),
                    getRandomCardinality(random, i)
            );
        }
        return sol;
    }

    private boolean isInDomain(VMConfiguration conf, int component) {
        return domainHandler.isInDomain( new LongValue(conf.getCores()),
                component * VariableTypeOrderer.variablesPerComponent + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES))
                && domainHandler.isInDomain( new LongValue(conf.getRam()),
                component * VariableTypeOrderer.variablesPerComponent + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM))
                && domainHandler.isInDomain( new DoubleValue(conf.getDisk()),
                component * VariableTypeOrderer.variablesPerComponent + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE));
    }

    private boolean isInDomain(GeographicCoordinate loc, int component) {
        return domainHandler.isInDomain( new DoubleValue(loc.getLatitude()),
                component * VariableTypeOrderer.variablesPerComponent + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE))
                && domainHandler.isInDomain( new DoubleValue(loc.getLongitude()),
                component * VariableTypeOrderer.variablesPerComponent + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE));
    }

    private Map<Integer, List<Boolean>> getConfigurationMask(int component) {
        Map<Integer, List<Boolean>> result = new HashMap<>();
        for (Integer key : vmConfigurations.keySet()) {
            result.put(key, new ArrayList<>());
            List<VMConfiguration> confs = vmConfigurations.get(key);
            for (int i = 0; i < confs.size(); i++) {
                boolean mask = isInDomain(confs.get(i), component);
                result.get(key).add(mask);
            }
        }
        return result;
    }

    private Map<Integer, List<Boolean>> getLocationMask(int component) {
        Map<Integer, List<Boolean>> result = new HashMap<>();
        for (Integer key : vmLocations.keySet()) {
            result.put(key, new ArrayList<>());
            List<GeographicCoordinate> locs = vmLocations.get(key);
            for (int i = 0; i < locs.size(); i++) {
                boolean mask = isInDomain(locs.get(i), component);
                result.get(key).add(mask);
            }
        }
        return result;
    }

    private void fillMasks() {
        vmConfigurationMasks = new HashMap<>();
        vmLocationMasks = new HashMap<>();
        for (int i = 0; i < components.size(); i++) {
            vmConfigurationMasks.put(i, getConfigurationMask(i));
            vmLocationMasks.put(i, getLocationMask(i));
        }
    }
}
