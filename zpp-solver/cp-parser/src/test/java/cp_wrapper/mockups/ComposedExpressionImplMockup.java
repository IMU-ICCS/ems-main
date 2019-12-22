package cp_wrapper.mockups;

import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl;
import lombok.AllArgsConstructor;
import org.eclipse.emf.common.util.EList;

@AllArgsConstructor
public class ComposedExpressionImplMockup extends NumericExpressionImpl implements ComposedExpression {
    private EList<NumericExpression> expressions;
    private OperatorEnum operator;

    @Override
    public EList<NumericExpression> getExpressions() {
        return expressions;
    }

    @Override
    public OperatorEnum getOperator() {
        return operator;
    }

    @Override
    public void setOperator(OperatorEnum value) {
        this.operator = operator;
    }
}
