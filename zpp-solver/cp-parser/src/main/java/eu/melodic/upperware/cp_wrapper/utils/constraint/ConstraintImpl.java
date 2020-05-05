package eu.melodic.upperware.cp_wrapper.utils.constraint;

import eu.melodic.upperware.cp_wrapper.utils.expression_evaluator.ExpressionEvaluator;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ConstraintImpl implements Constraint {
    /*
        Names of variables which are used in the constraint
     */
    private Collection<String> variablesNames;
    private ComparatorEnum comparator;
    private Expression leftExpression;
    private Expression rightExpression;

    public ConstraintImpl(ComparatorEnum comp, Expression exp1, Expression exp2){
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
            ((ComposedExpression) exp).getExpressions().forEach(this::collectVariableNamesFromExpression);
        }
    }

    private boolean checkVariables(Collection<String> vars) {
        return vars.containsAll(variablesNames);
    }

    @Override
    public boolean evaluate(Map<String, NumericValueInterface> variables){
        if (!checkVariables(variables.keySet())) {
            throw new RuntimeException("Can't evaluate - some variables are missing");
        }
        return ExpressionEvaluator.evaluateComparator(comparator, leftExpression, rightExpression, variables);
    }

    @Override
    public Collection<String> getVariableNames() {
        return variablesNames;
    }
}
