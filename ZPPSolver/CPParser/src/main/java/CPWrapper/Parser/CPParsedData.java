package CPWrapper.Parser;
/*
    This class contains all data representing a specific constraint problem.
 */

import CPWrapper.Utils.ArConstraint;
import CPWrapper.Utils.ConstraintGraph;
import CPWrapper.Utils.VariableNumericType;
import eu.melodic.upperware.cpsolver.solver.parser.creator.IntVarCreator;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CPParsedData {
    private List<Constant> constants;
    private List<CpMetric> metrics;
    private List<ArConstraint> constraints;
    private List<CpVariable> variables;
    private IntVarCreator intVarCreator;
    @Getter
    private ConstraintGraph constraintGraph;
    private Map<String, CpVariable> nameToVariable;

    public Collection<String> getVariableNames() {
        return nameToVariable.keySet();
    }

    protected void init() {
        initializeConstraintGraph();
        initializeNameToVariable();
    }

    public boolean isFeasible(Map<String, Double> variables) {
        for (ArConstraint c : constraints) {
            if (!c.evaluate(variables)) {
                return false;
            }
        }
        return true;
    }

    public int countViolatedConstraints(Map<String, Double> variables) {
        return constraints.stream()
                .map(c -> c.evaluate(variables) ?  0 : 1)
                .reduce( 0, Integer::sum);
    }

    public int getHeuristicEvaluation(String variable, Map<String, Double> variables) {
        return constraintGraph.getVariableHeuristicEvaluation(variable, variables);
    }

    public void updateMetrics(Collection<CpMetric> metrics) {
        //TODO
    }

    public Domain getVariableDomain(String variable) {
        return nameToVariable.get(variable).getDomain();
    }

    public VariableNumericType getVariableType(String name) {
        if (intVarCreator.is(nameToVariable.get(name))) {
            return VariableNumericType.INT;
        } else {
            return VariableNumericType.DOUBLE;
        }
    }
    private void initializeNameToVariable() {
        nameToVariable = new HashMap<String, CpVariable>();
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

    void postConstants(List<Constant> constants) {
        this.constants = constants;
    }
    void postMetrics(List<CpMetric> metrics) {
        this.metrics = metrics;
    }
    void postConstraints(List<ArConstraint> constraints) {
        this.constraints = constraints;
    }
    void postVariables(List<CpVariable> variables) {
        this.variables = variables;
    }
}
