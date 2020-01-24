package generator;

import cp_wrapper.utils.ExpressionEvaluator;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.paasage.upperware.metamodel.cp.VariableType;
import expressions.ComposedExpression;
import expressions.ConstantExpression;
import expressions.Expression;
import expressions.VariableExpression;
import lombok.Getter;
import nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import nc_solver.variable_orderer.VariableTypeOrderer;
import node_candidates.NodeCandidatesPool;
import org.javatuples.Pair;
import utils.NamesProvider;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
    Each constraint is of a form Expression operator constant;
    Process of constraint sampling proceeds as follows:
        1) sample Expression (task of dasasdasdasd)
        2) sample operator
        3) Choose constant value
    Point 3) is this class responsibility;
    A sample of size @sampleSize is taken from the state space -
    median of Expression value on this sample is taken to be equal to the constant
    (we try to avoid equality constraints);
 */
public class ConstraintEvaluator {
    private final int SAMPLE_SIZE = 20;
    private Random random = new Random();
    private NodeCandidatesPool nodeCandidatesPool;
    private int componentCount;
    private VariableGenerator variableGenerator;
    public ConstraintEvaluator(VariableGenerator variableGenerator, NodeCandidatesPool nodeCandidatesPool, int componentCount) {
        this.variableGenerator = variableGenerator;
        this.nodeCandidatesPool = nodeCandidatesPool;
        this.componentCount = componentCount;
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
        IntStream.range(0, componentCount).forEach( component -> sample.put(component, sampleComponentData(component)));
        return sample;
    }

    private Map<String, NumericValueInterface> mapSampleToList(Map<Integer, Pair<VMConfiguration, GeographicCoordinate>> sample) {
        Map<String, NumericValueInterface> result = new HashMap<>();
        Map<Integer, List<NumericValueInterface>> domains = variableGenerator.getVariableDomains();
        sample.keySet().stream().forEach(
                component -> {
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES)),
                            new LongValue(sample.get(component).getValue0().getCores()));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE)),
                            new LongValue(sample.get(component).getValue0().getDisk()));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM)),
                            new LongValue(sample.get(component).getValue0().getRam()));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE)),
                            new LongValue(sample.get(component).getValue1().getLatitude()));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE)),
                            new LongValue(sample.get(component).getValue1().getLongitude()));
                    List<NumericValueInterface> cardinalityDomain = domains
                            .get(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.CARDINALITY)),
                            cardinalityDomain.get(random.nextInt(cardinalityDomain.size())));
                    List<NumericValueInterface> providerDomain = domains
                            .get(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.PROVIDER));
                    result.put(NamesProvider.getVariableName(component*NamesProvider.VARIABLES_PER_COMPONENT + VariableTypeOrderer.mapTypeToIndex(VariableType.PROVIDER)),
                            providerDomain.get(random.nextInt(providerDomain.size())));
                }
        );
        return result;
    }

    ConstantExpression getConstant(Expression expression) {
        List<Double> evaluateResults = new ArrayList<>();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            evaluateResults.add(evaluateExpression(expression, mapSampleToList(sample())));
        }
        Collections.sort(evaluateResults);
        return new ConstantExpression(new DoubleValue(evaluateResults.get(SAMPLE_SIZE /2)));
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
                    ((ComposedExpression) expression).getOper(),
                    Arrays.asList(
                            evaluateExpression(exp1, vars),
                            evaluateExpression(exp2, vars)
                    )
            );
        }
        throw new RuntimeException("Unrecognized expression type!");
    }
}
