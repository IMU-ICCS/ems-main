package CPWrapper.Parser;

import CPWrapper.Utils.ArConstraint;
import CPWrapper.Utils.ConstraintGraph;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.CpVariable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CPParsedData {
    private Collection<Constant> constants;
    private Collection<CpMetric> metrics;
    private Collection<ArConstraint> constraints;
    private Collection<CpVariable> variables;
    private ConstraintGraph constraintGraph;


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

    public int getHeuristicEvaluation(String variable) {
        return 0;
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
