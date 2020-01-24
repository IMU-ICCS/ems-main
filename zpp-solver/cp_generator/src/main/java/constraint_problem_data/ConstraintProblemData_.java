package constraint_problem_data;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import expressions.Constraint;
import expressions.VariableExpression;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Getter
public class ConstraintProblemData_ {
    private Map<String, List<NumericValueInterface>> variableToDomain;
    private Collection<VariableExpression> variables;
    private Collection<Constraint> constraints;

    public ConstraintProblemData_(Map<String, List<NumericValueInterface>> variableToDomain) {
        this.variableToDomain = variableToDomain;
        gatherVariables();
        constraints = new ArrayList<>();
    }

    public List<NumericValueInterface> getVariableDomain(String variable) {
        return variableToDomain.get(variable);
    }

    private void gatherVariables() {
        variables = new ArrayList<>();
        for (String name : variableToDomain.keySet()) {
            variables.add(new VariableExpression(name));
        }
    }

    public void postConstraint(Constraint constraint) {
        constraints.add(constraint);
    }
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Variables:\n");
        for (VariableExpression var : variables) {
            build.append(var.getVariableName());
            build.append('\n');
        }
        build.append("Constraints:\n");
        for (Constraint c : constraints) {
            build.append(c.toString());
        }
        build.append("Domains:\n");
        for (String var : variableToDomain.keySet()) {
            build.append(var + " domain:\n");
            build.append('\n');
        }
        return build.toString();
    }
}
