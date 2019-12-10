package CPWrapper.Parser;
/*
    This class contains all data representing a specific constraint problem.
 */

import CPWrapper.Utils.ArConstraint;
import CPWrapper.Utils.ConstraintGraph;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Domain;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CPParsedData {
    private Collection<Constant> constants;
    private Collection<CpMetric> metrics;
    private Collection<ArConstraint> constraints;
    private Collection<CpVariable> variables;
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
        int count =
                constraints.stream()
                        .map(c -> c.evaluate(variables) ?  0 : 1)
                        .reduce( (int) 0, (subtotal, el) -> subtotal + el);
        return count;
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

    private void initializeNameToVariable() {
        nameToVariable = new HashMap<String, CpVariable>();
        for (CpVariable var : variables) {
            nameToVariable.put(var.getId(), var);
        }
    }

    private void initializeConstraintGraph() {
        List<String> variableNames =
                variables.stream()
                        .map(var -> var.getId())
                        .collect(Collectors.toList());
        constraintGraph = new ConstraintGraph(constraints, variableNames);
    }

    protected void postConstants(Collection<Constant> constants) {
        this.constants = constants;
    }
    protected void postMetrics(Collection<CpMetric> metrics) {
        this.metrics = metrics;
    }
    protected void postConstraints(Collection<ArConstraint> constraints) {
        this.constraints = constraints;
    }
    protected void postVariables(Collection<CpVariable> variables) {
        this.variables = variables;
    }
}
