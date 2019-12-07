package CPWrapper.Utils;

import eu.paasage.upperware.metamodel.cp.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ArConstraintImpl implements ArConstraint {
    /*
        Names of variables which are used in the constraint
     */
    private Collection<String> variablesNames;
    private ComparatorEnum comparator;
    private Expression leftExpression;
    private Expression rightExpression;

    public ArConstraintImpl(ComparatorEnum comp, Expression exp1, Expression exp2){
        this.comparator = comp;
        this.leftExpression = exp1;
        this.rightExpression = exp2;
        variablesNames = new ArrayList<>();
        collectVariableNames();
    }

    private void collectVariableNames() {
        collectVariableNamesFromExpression(leftExpression);
        collectVariableNamesFromExpression(rightExpression);
    }

    private void collectVariableNamesFromExpression(Expression exp) {
        if (ExpressionEvaluator.isCpVariable(exp)) {
            String name = exp.getId();
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

    private boolean checkVariables(Collection<String> vars) {
        return vars.containsAll(variablesNames);
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
