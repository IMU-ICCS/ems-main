package eu.melodic.upperware.cp_sampler.generator;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.cp_sampler.expressions.Expression;
import eu.melodic.upperware.cp_sampler.expressions.VariableExpression;
import eu.melodic.upperware.cp_sampler.node_candidates.NodeCandidatesPool;
import eu.melodic.upperware.cp_sampler.utils.NamesProvider;
import eu.melodic.upperware.cp_sampler.utils.priors.Priors;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

/*
    Generates variables domains, and variables themselves;
    Each component has a full set of variables
 */
public class VariableGenerator {
    private NodeCandidatesPool candidatesPool;
    private Random random = new Random();
    @Getter
    private int componentsCount;
    @Getter
    private Map<Integer, List<NumericValueInterface>> variableDomains = new HashMap<>();
    @Getter
    private Map<Integer, List<VMConfiguration>> componentToConfiguration= new HashMap<>();
    @Getter
    private Map<Integer, List<GeographicCoordinate>> componentToLocation = new HashMap<>();

    public VariableGenerator(NodeCandidatesPool candidatesPool, int componentsCount) {
        this.componentsCount = componentsCount;
        this.candidatesPool = candidatesPool;
        sampleVariableDomains();
    }

    public Map<String, List<NumericValueInterface>> getDomains() {
        Map<String, List<NumericValueInterface>> result = new HashMap<>();
        IntStream.range(0, componentsCount * NamesProvider.VARIABLES_PER_COMPONENT)
                .forEach( variable -> result.put(NamesProvider.getVariableName(variable), getVariableDomain(variable)));
        return result;
    }

    Pair<VariableExpression, VariableExpression> samplePairOfVariables() {
        int component2 = random.nextInt(componentsCount);
        int component1 = random.nextInt(componentsCount);
        List<Integer> availableVars = IntStream.range(0, NamesProvider.VARIABLES_PER_COMPONENT).boxed().collect(Collectors.toList());
        return new Pair<>(
                new VariableExpression(NamesProvider.getVariableName(
                        availableVars.get(random.nextInt(availableVars.size())) + component1 * NamesProvider.VARIABLES_PER_COMPONENT)),
                new VariableExpression(NamesProvider.getVariableName(
                        availableVars.get(random.nextInt(availableVars.size())) + component2 * NamesProvider.VARIABLES_PER_COMPONENT))
        );
    }

    List<VariableExpression> sampleVariablesForLongExpression() {
        List<Integer> availableVars = IntStream.range(0, NamesProvider.VARIABLES_PER_COMPONENT).boxed().collect(Collectors.toList());
        int variable = availableVars.get(random.nextInt(availableVars.size()));
        return IntStream.range(0, componentsCount).mapToObj(
                component -> new VariableExpression(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + variable))
        ).collect(Collectors.toList());
    }

    Expression sampleVariableExpression() {
        return new VariableExpression(NamesProvider.getVariableName(random.nextInt(getVariableCount())));
    }

    <T> List<T> removeRandomElementsFromList(List<T> list, int minSize) {
        if (list.size() <= minSize) return list;
        int newSize = minSize + random.nextInt(list.size() - minSize);
        while (list.size() > newSize) {
            list.remove(random.nextInt(list.size()));
        }
        return list;
    }

    private List<NumericValueInterface> getVariableDomain(int variable) {
        int component = NamesProvider.variableNameToComponent(NamesProvider.getVariableName(variable));
        VariableType type = NamesProvider.variableNameToType(NamesProvider.getVariableName(variable));
        if (type == VariableType.PROVIDER || type == VariableType.CARDINALITY) {
            return variableDomains.get(variable);
        } else if (type == VariableType.LONGITUDE || type == VariableType.LATITUDE) {
            return getLocationElementDomain(component, type);
        } else {
            return getConfigurationElementDomain(component, type);
        }
    }

    private List<NumericValueInterface> getLocationElementDomain(int component, VariableType type) {
        return componentToLocation.get(component).stream()
                .map( location -> type == VariableType.LATITUDE ? location.getLatitude() : location.getLongitude())
                .map(LongValue::new).distinct().collect(Collectors.toList());
    }

    private int getVariableCount() {
        return componentsCount* NamesProvider.VARIABLES_PER_COMPONENT;
    }

    private List<NumericValueInterface> getConfigurationElementDomain(int component, VariableType type) {
        switch(type) {
            case CORES:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getCores)
                        .map(LongValue::new).distinct().collect(Collectors.toList());
            case RAM:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getRam)
                        .map(LongValue::new).distinct().collect(Collectors.toList());
            case STORAGE:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getDisk)
                        .map(LongValue::new).distinct().collect(Collectors.toList());
            default:
                throw new RuntimeException("Invalid variable type");
        }
    }

    private void sampleConfigurationAndLocationDomains() {
        List<Pair<VMConfiguration, GeographicCoordinate>> VMs = candidatesPool.getAllConfigurationsAndLocations();
        IntStream.range(0, componentsCount).forEach(component -> {
                    List<Pair<VMConfiguration, GeographicCoordinate>> machines = removeRandomElementsFromList(new ArrayList<>(VMs), Priors.MIN_DOMAIN_SIZE);
                    componentToConfiguration.put(component, machines.stream().map(Pair::getValue0).collect(Collectors.toList()));
                    componentToLocation.put(component, machines.stream().map(Pair::getValue1).collect(Collectors.toList()));
                });
    }

    private int sampleDomainSize(int fullDomainSize) {
        int minSampleBound = (int) (Priors.MIN_DOMAIN_PERCENTAGE * ((double) fullDomainSize));
        if (minSampleBound == 0) return 1;
        int increaseOverMin  = random.nextInt( fullDomainSize - minSampleBound);
       return increaseOverMin + minSampleBound;
    }

    private List<NumericValueInterface> sampleCardinalityDomain() {
        int maxCard = random.nextInt(Priors.MAX_CARDINALITY - Priors.MIN_CARDINALITY) + 1;
        return IntStream.range(1, maxCard + 1).mapToObj(IntegerValue::new).collect(Collectors.toList());
    }

    private List<NumericValueInterface> sampleProviderDomain() {
        return candidatesPool.getProviderDomain();
    }

    private List<NumericValueInterface> sampleVariableDomain(int var) {
        VariableType type = NamesProvider.mapIndexToType(var % NamesProvider.VARIABLES_PER_COMPONENT);
        if (type == VariableType.CARDINALITY) {
            return sampleCardinalityDomain();
        } else {
            return sampleProviderDomain();
        }
    }

    private void sampleVariableDomains() {
        sampleConfigurationAndLocationDomains();
        range(0, getVariableCount()).filter(variable ->
                        NamesProvider.variableNameToType(NamesProvider.getVariableName(variable)) == VariableType.PROVIDER ||
                        NamesProvider.variableNameToType(NamesProvider.getVariableName(variable)) == VariableType.CARDINALITY
                ).forEach(
                variable -> variableDomains.put(variable, sampleVariableDomain(variable))
        );
    }
}
