package eu.melodic.upperware.cp_sampler.generator;

import cp_wrapper.utils.ExpressionEvaluator;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.cp_sampler.expressions.ComposedExpression;
import eu.melodic.upperware.cp_sampler.expressions.ConstantExpression;
import eu.melodic.upperware.cp_sampler.expressions.Expression;
import eu.melodic.upperware.cp_sampler.expressions.VariableExpression;
import eu.melodic.upperware.cp_sampler.utils.NamesProvider;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.VariableTypeOrderer;
import eu.paasage.upperware.metamodel.cp.VariableType;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
    Each constraint is of a form Expression operator constant;
    Process of constraint sampling proceeds as follows:
        1) sample Expression (task of ConstraintGenerator)
        2) sample operator
        3) Choose constant value
    Point 3) is this class responsibility;
    A sample of size @sampleSize is taken from the state space -
    median of Expression value on this sample is taken to be equal to the constant
    (we try to avoid equality constraints);
 */
public class ConstraintEvaluator {
    private final int SAMPLE_SIZE = 80;
    private Random random = new Random();
    private VariableGenerator variableGenerator;

    public ConstraintEvaluator(VariableGenerator variableGenerator) {
        this.variableGenerator = variableGenerator;
    }

    ConstantExpression getConstant(Expression expression) {
        List<Double> evaluatedResults = IntStream.range(0, SAMPLE_SIZE).mapToObj(
                sample -> evaluateExpression(expression, mapSampleToList(sample()))).sorted().collect(Collectors.toList());
        /*
            At this point evaluated results have been sorted, hence SAMPLE_SIZE/2 -th element
            will be the median. We choose samples median as a constraint constant because this guarantees us
            approximately half of state space elements will not violate the constraint.
         */
        return new ConstantExpression(new DoubleValue(evaluatedResults.get(SAMPLE_SIZE /2)));
    }

    private Pair<VMConfiguration, GeographicCoordinate> sampleComponentData(int component) {
        List<VMConfiguration> configurations = variableGenerator.getComponentToConfiguration().get(component);
        List<GeographicCoordinate> locations = variableGenerator.getComponentToLocation().get(component);
        return new Pair<>(
                configurations.get(random.nextInt(configurations.size())),
                locations.get(random.nextInt(locations.size()))
        );
    }

    private Map<Integer, Pair<VMConfiguration, GeographicCoordinate>> sample() {
        Map<Integer, Pair<VMConfiguration, GeographicCoordinate>> sample = new HashMap<>();
        IntStream.range(0, variableGenerator.getComponentsCount())
                .forEach( component -> sample.put(component, sampleComponentData(component)));
        return sample;
    }

    private Map<String, NumericValueInterface> mapSampleToList(Map<Integer, Pair<VMConfiguration, GeographicCoordinate>> sample) {
        Map<String, NumericValueInterface> result = new HashMap<>();
        Map<Integer, List<NumericValueInterface>> domains = variableGenerator.getVariableDomains();
        sample.keySet().forEach(
                component -> {
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES)),
                            new LongValue(sample.get(component).getValue0().getCores()));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE)),
                            new LongValue(sample.get(component).getValue0().getDisk()));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM)),
                            new LongValue(sample.get(component).getValue0().getRam()));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE)),
                            new LongValue(sample.get(component).getValue1().getLatitude()));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE)),
                            new LongValue(sample.get(component).getValue1().getLongitude()));
                    List<NumericValueInterface> cardinalityDomain = domains
                            .get(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY)),
                            cardinalityDomain.get(random.nextInt(cardinalityDomain.size())));
                    List<NumericValueInterface> providerDomain = domains
                            .get(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.PROVIDER));
                    result.put(NamesProvider.getVariableName(component* NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.PROVIDER)),
                            providerDomain.get(random.nextInt(providerDomain.size())));
                }
        );
        return result;
    }

    private double evaluateExpression(Expression expression, Map<String, NumericValueInterface> vars) {
        if (expression instanceof ConstantExpression) {
            return ((ConstantExpression) expression).getValue().getDoubleValue();
        } else if (expression instanceof VariableExpression) {
            return vars.get(((VariableExpression) expression).getVariableName()).getDoubleValue();
        } else if (expression instanceof ComposedExpression) {
            Expression exp1 = ((ComposedExpression) expression).getLeftExpr();
            Expression exp2 = ((ComposedExpression) expression).getRightExpr();
            return ExpressionEvaluator.evaluateOnOperator(
                    ((ComposedExpression) expression).getOperator(),
                    Arrays.asList(
                            evaluateExpression(exp1, vars),
                            evaluateExpression(exp2, vars)
                    )
            );
        }
        throw new RuntimeException("Unrecognized expression type!");
    }
}
