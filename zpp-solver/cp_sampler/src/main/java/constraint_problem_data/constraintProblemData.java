package constraint_problem_data;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import expressions.Constraint;
import expressions.VariableExpression;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class constraintProblemData {
    private Map<String, List<NumericValueInterface>> variableToDomain;
    private Collection<VariableExpression> variables;
    private Collection<Constraint> constraints = new ArrayList<>();

    public constraintProblemData(Map<String, List<NumericValueInterface>> variableToDomain) {
        this.variableToDomain = variableToDomain;
        variables = gatherVariables();
    }

    public List<NumericValueInterface> getVariableDomain(String variable) {
        return variableToDomain.get(variable);
    }

    private Collection<VariableExpression> gatherVariables() {
        return variableToDomain.keySet().stream().map(VariableExpression::new).collect(Collectors.toList());
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
