package cp_wrapper.parser;
/*
    This class contains all data representing a specific constraint problem.
 */

import cp_wrapper.utils.constraint.Constraint;
import cp_wrapper.utils.constraint_graph.ConstraintGraph;
import cp_wrapper.utils.cp_variable.VariableNumericType;
import cp_wrapper.utils.numeric_value.NumericValueInterface;

//import eu.melodic.upperware.cpsolver.solver.parser.creator.*;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CPParsedData {
    private Collection<Constant> constants;
    private Collection<CpMetric> metrics;
    private Collection<Constraint> constraints;
    @Getter
    private Collection<CpVariable> variables;
    static final List<BasicTypeEnum> ACCEPTED_INT_TYPES = Arrays.asList(BasicTypeEnum.INTEGER, BasicTypeEnum.LONG);
    @Getter
    private ConstraintGraph constraintGraph;
    private Map<String, CpVariable> nameToVariable;

    public Collection<String> getVariableNames() {
        return nameToVariable.keySet();
    }

    public boolean checkIfFeasible(Map<String, NumericValueInterface> variables) {
        for (Constraint c : constraints) {
            if (!c.evaluate(variables)) {
                return false;
            }
        }
        return true;
    }

    public int countViolatedConstraints(Map<String, NumericValueInterface> variables) {
        return constraints.stream()
                .map(c -> c.evaluate(variables) ?  0 : 1)
                .reduce( 0, Integer::sum);
    }

    public int getHeuristicEvaluation(String variable, Map<String, NumericValueInterface> variables) {
        return constraintGraph.getVariableHeuristicEvaluation(variable, variables);
    }

    public Domain getVariableDomain(String variable) {
        return nameToVariable.get(variable).getDomain();
    }

    public VariableNumericType getVariableType(String name) {
        if (ACCEPTED_INT_TYPES.contains(getDomainType((nameToVariable.get(name)).getDomain()))) {
            return VariableNumericType.INT;
        } else {
            return VariableNumericType.DOUBLE;
        }
    }

    void init() {
        initializeConstraintGraph();
        initializeNameToVariable();
    }

    void postConstants(Collection<Constant> constants) {
        this.constants = constants;
    }
    void postMetrics(Collection<CpMetric> metrics) {
        this.metrics = metrics;
    }
    void postConstraints(Collection<Constraint> constraints) {
        this.constraints = constraints;
    }
    void postVariables(Collection<CpVariable> variables) {
        this.variables = variables;
    }

    private BasicTypeEnum getDomainType(Domain domain) {
        if (domain instanceof RangeDomain) {
            return ((RangeDomain) domain).getType();
        } else if (domain instanceof NumericListDomain) {
            return ((NumericListDomain) domain).getType();
        }
        throw new RuntimeException(format("Unsupported Domain type %s, only RangeDomain and NumericListDomain are supported",
                domain.getClass().getSimpleName()));
    }

    private void initializeNameToVariable() {
        nameToVariable = new HashMap<>();
        for (CpVariable var : variables) {
            nameToVariable.put(var.getId(), var);
        }
    }

    private void initializeConstraintGraph() {
        List<String> variableNames =
                variables.stream()
                        .map(CPElement::getId)
                        .collect(Collectors.toList());
        constraintGraph = new ConstraintGraph(constraints, variableNames);
    }
}
