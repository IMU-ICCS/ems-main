import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

public class ArConstraint {

    @Getter
    private Collection<String> variables;

    private ComparatorEnum comparator;

    Expression leftExpression;

    Expression rightExpression;

    private boolean checkVariables(Collection<String> vars) {
        return vars.containsAll(variables);
    }

    public ArConstraint(){

    }

    public boolean evaluate(Map<String, Double> variables){
        if (!checkVariables(variables.keySet())) {
            throw new RuntimeException("Can't evaluate - some variables are missing");
        }
        double leftExpValue = ExpressionEvaluator.evaluateExpression(leftExpression, variables);
        double rightExpValue = ExpressionEvaluator.evaluateExpression(leftExpression, variables);

        return ExpressionEvaluator.evaluateComparator(comparator, leftExpValue, rightExpValue);
    }
}
