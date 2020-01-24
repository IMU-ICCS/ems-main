package expressions;

import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComposedExpression implements Expression {
    private Expression leftExpr;
    private Expression rightExpr;
    private OperatorEnum oper;

    private String operatorToString(OperatorEnum oper) {
        switch(oper) {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case TIMES:
                return "*";
            default:
                return "{}";
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append(leftExpr.toString());
        builder.append(") ");
        builder.append(operatorToString(oper));
        builder.append(" (");
        builder.append(rightExpr.toString());
        builder.append(')');
        return builder.toString();
    }
}
