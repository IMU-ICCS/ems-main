package generator;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.paasage.upperware.metamodel.cp.VariableType;
import expressions.Expression;
import expressions.VariableExpression;
import lombok.Getter;
import nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import node_candidates.NodeCandidatesPool;
import org.javatuples.Pair;
import sun.rmi.rmic.Names;
import utils.NamesProvider;

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
    private final int MIN_DOMAIN_SIZE = 3;
    private final int maxCardinality = 10;
    private final int minCardinality = 1;
    private int componentsCount;
    @Getter
    private Map<Integer, List<NumericValueInterface>> variableDomains;
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
                .map(value -> new LongValue(value))
                .distinct().collect(Collectors.toList());
    }
    int getVariableCount() {
        return componentsCount* NamesProvider.VARIABLES_PER_COMPONENT;
    }

    private List<NumericValueInterface> getConfigurationElementDomain(int component, VariableType type) {
        switch(type) {
            case CORES:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getCores).map(LongValue::new).distinct().collect(Collectors.toList());
            case RAM:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getRam).map(LongValue::new).distinct().collect(Collectors.toList());
            case STORAGE:
                return componentToConfiguration.get(component).stream().map(VMConfiguration::getDisk).map(LongValue::new).distinct().collect(Collectors.toList());
            default:
                throw new RuntimeException("Invalid variable type");
        }
    }

    <T> List<T> removeRandomElementsFromList(List<T> list, int minSize) {
        if (list.size() <= minSize) return list;
        int newSize = minSize + random.nextInt(list.size() - minSize);
        while (list.size() > newSize) {
            list.remove(random.nextInt(list.size()));
        }
        return list;
    }

    private void sampleConfigurationDomains() {
            List<VMConfiguration> configurations = candidatesPool.getAllConfigurations();
            IntStream.range(0, componentsCount).forEach( component ->
                    componentToConfiguration.put(component, removeRandomElementsFromList(new ArrayList<>(configurations), MIN_DOMAIN_SIZE)));
    }

    private void sampleLocationDomains() {
        List<VMConfiguration> configurations = candidatesPool.getAllConfigurations();
        IntStream.range(0, componentsCount).forEach( component ->
                componentToConfiguration.put(component, removeRandomElementsFromList(new ArrayList<>(configurations), MIN_DOMAIN_SIZE)));
    }

    private int sampleDomainSize(int fullDomainSize) {
        int sampleBound = (int)((1-MIN_DOMAIN_SIZE)*fullDomainSize);
        if (sampleBound == 0) return 1;
        int r  = random.nextInt((int)((1-MIN_DOMAIN_SIZE)*fullDomainSize));
        int size = r + (int) (MIN_DOMAIN_SIZE*fullDomainSize);
        if (size > fullDomainSize) {
            return fullDomainSize;
        } else {
            if (size == 0) return 1;
            return size;
        }
    }

    private List<NumericValueInterface> sampleCardinalityDomain() {
        List<NumericValueInterface> result = new ArrayList<>();
        int maxCard = random.nextInt(maxCardinality - minCardinality) + 1;
        for (int i = 1; i <= maxCard; i++) {
            result.add(new IntegerValue(i));
        }
        assert result.size() > 0 ;
        return result;
    }

    private List<NumericValueInterface> sampleProviderDomain() {
        List<NumericValueInterface> fullDomain = candidatesPool.getDomain(VariableType.PROVIDER);
        int size = sampleDomainSize(fullDomain.size());
        if (size == fullDomain.size()) {
            return fullDomain;
        }
        int startIndex = random.nextInt(fullDomain.size() - size);
        return fullDomain.subList(startIndex, startIndex + size);
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
        variableDomains = new HashMap<>();
        sampleLocationDomains();
        sampleConfigurationDomains();
        range(0, getVariableCount()).filter(variable ->
                        NamesProvider.variableNameToType(NamesProvider.getVariableName(variable)) == VariableType.PROVIDER ||
                        NamesProvider.variableNameToType(NamesProvider.getVariableName(variable)) == VariableType.CARDINALITY
                ).forEach(
                variable -> variableDomains.put(variable, sampleVariableDomain(variable))
        );
    }

    private Pair<Integer, Integer> sampleComponentPair() {
        int component1 = random.nextInt(componentsCount);
        int component2 = random.nextInt(componentsCount);
        return new Pair<>(component1, component2);
    }

    Pair<VariableExpression, VariableExpression> samplePairOfVariables() {
        Pair<Integer, Integer> components = sampleComponentPair();
        int component2 = components.getValue1();
        int component1 = components.getValue0();
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
                component ->
                        new VariableExpression(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + variable))
        ).collect(Collectors.toList());
    }

    Expression sampleVariableExpression() {
        return new VariableExpression(NamesProvider.getVariableName(random.nextInt(getVariableCount())));
    }
}
