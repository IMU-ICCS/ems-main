package eu.melodic.upperware.cp_sampler.generator;

import eu.melodic.upperware.cp_wrapper.utils.expression_evaluator.ExpressionEvaluator;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.cp_sampler.expressions.ComposedExpression;
import eu.melodic.upperware.cp_sampler.expressions.ConstantExpression;
import eu.melodic.upperware.cp_sampler.expressions.Expression;
import eu.melodic.upperware.cp_sampler.expressions.VariableExpression;
import eu.melodic.upperware.cp_sampler.utils.NamesProvider;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.VariableTypeOrderer;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.VariableType;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static eu.melodic.upperware.cp_wrapper.utils.expression_evaluator.ExpressionEvaluator.evaluateComparator;

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
    private Random random = new Random();
    private VariableGenerator variableGenerator;
    private int numberOfConstraints;
    private List<Map<Integer, Pair<VMConfiguration, GeographicCoordinate>>> samples;
    private final int SAMPLE_SIZE = 1000;
    private final int MIN_SAMPLE_SIZE = 10;

    public ConstraintEvaluator(VariableGenerator variableGenerator, int numberOfConstraints) {
        this.variableGenerator = variableGenerator;
        this.numberOfConstraints = numberOfConstraints;
        this.samples = getSample(SAMPLE_SIZE);
    }

    private List<Map<Integer, Pair<VMConfiguration, GeographicCoordinate>>> getSample(int sampleSize) {
        return IntStream.range(0, sampleSize).mapToObj(sampleNumber -> sample()).collect(Collectors.toList());
    }

    ConstantExpression getConstant(Expression expression, ComparatorEnum comparatorEnum) {
        if (samples.size() <= MIN_SAMPLE_SIZE) samples = getSample(SAMPLE_SIZE);
        List<Double> evaluatedResults = samples.stream().map(sample -> evaluateExpression(expression, mapSampleToList(sample))).sorted().collect(Collectors.toList());
        /*
            At this point evaluated results have been sorted, hence SAMPLE_SIZE/2 -th element
            will be the median. We choose samples median as a constraint constant because this guarantees us
            approximately half of state space elements will not violate the constraint.
         */
        double constant = getConstant(evaluatedResults, comparatorEnum);
        samples = samples.stream().filter(sample -> evaluateComparator(comparatorEnum, evaluateExpression(expression, mapSampleToList(sample)), constant)).collect(Collectors.toList());
        return new ConstantExpression(new DoubleValue(constant));
    }

    private double getConstant(List<Double> evaluatedSamples, ComparatorEnum comparatorEnum) {
        int middle = evaluatedSamples.size() /2;
        int constantIndex = middle;
        if (comparatorEnum == ComparatorEnum.GREATER_OR_EQUAL_TO || comparatorEnum == ComparatorEnum.GREATER_THAN) {
            constantIndex -= random.nextInt(middle);
        } else {
            constantIndex += random.nextInt(middle);
        }
        return evaluatedSamples.get(constantIndex);
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
