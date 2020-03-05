package eu.melodic.upperware.cp_sampler.expressions;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Constraint {
    private Expression expression;
    private Expression constant;
    private ComparatorEnum comparator;

    private String comparatorToString(ComparatorEnum comp) {
        switch(comp) {
            case LESS_OR_EQUAL_TO:
                return "<=";
            case LESS_THAN:
                return "<";
            case GREATER_OR_EQUAL_TO:
                return ">=";
            case GREATER_THAN:
                return ">";
            case DIFFERENT:
                return "!=";
            default:
                return "==";
        }
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(expression.toString());
        builder.append(comparatorToString(comparator));
        builder.append(((ConstantExpression)constant).getValue());
        builder.append('\n');
        return builder.toString();
    }
}
