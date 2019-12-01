import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ArConstraintImpl implements ArConstraint {

    private Collection<String> variablesNames;

    private ComparatorEnum comparator;

    Expression leftExpression;

    Expression rightExpression;

    private void collectVariableNamesFromExpression(Expression exp) {
        if (ExpressionEvaluator.isCpVariable(exp)) {
            String name = ((CpVariable) exp).getId();
            if(!variablesNames.contains(name)) {
                variablesNames.add(name);
            }
        } else if (ExpressionEvaluator.isComposedExpression(exp)) {
            ((ComposedExpression) exp).getExpressions()
                    .stream()
                    .forEach( e -> {
                        this.collectVariableNamesFromExpression(e);
                    });
        }
    }

    private void collectVariableNames() {
        collectVariableNamesFromExpression(leftExpression);
        collectVariableNamesFromExpression(rightExpression);
    }

    private boolean checkVariables(Collection<String> vars) {
        return vars.containsAll(variablesNames);
    }

    public ArConstraintImpl(ComparatorEnum comp, Expression exp1, Expression exp2){
        this.comparator = comp;
        this.leftExpression = exp1;
        this.rightExpression = exp2;
        variablesNames = new ArrayList<>();
        collectVariableNames();
    }

    @Override
    public boolean evaluate(Map<String, Double> variables){
        if (!checkVariables(variables.keySet())) {
            throw new RuntimeException("Can't evaluate - some variables are missing");
        }
        double leftExpValue = ExpressionEvaluator.evaluateExpression(leftExpression, variables);
        double rightExpValue = ExpressionEvaluator.evaluateExpression(rightExpression, variables);
        return ExpressionEvaluator.evaluateComparator(comparator, leftExpValue, rightExpValue);
    }

    @Override
    public Collection<String> getVariableNames() {
        return variablesNames;
    }
}
