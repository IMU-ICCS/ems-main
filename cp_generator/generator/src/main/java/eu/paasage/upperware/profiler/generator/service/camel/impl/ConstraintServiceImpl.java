package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConstraintServiceImpl implements ConstraintService {

    private CpFactory cpFactory;
    private IdGenerator constraintIdGenerator;
    private IdGenerator auxExpressionIdGenerator;

    @Override
    public ComparisonExpression createComparisonExpression(Expression exp1, ComparatorEnum comparatorEnum, Expression exp2){
        return createComparisonExpression(exp1, comparatorEnum, exp2, constraintIdGenerator.generate());
    }

    @Override
    public ComparisonExpression createComparisonExpression(Expression exp1, ComparatorEnum comparatorEnum, Expression exp2, String id) {
        ComparisonExpression ce= cpFactory.createComparisonExpression();
        ce.setId(id);
        ce.setExp1(exp1);
        ce.setExp2(exp2);
        ce.setComparator(comparatorEnum);
        return ce;
    }

    @Override
    public ComposedExpression createComposedExpression(OperatorEnum op, NumericExpression... numericExpressions) {
        return createComposedExpression(op, auxExpressionIdGenerator.generate(), numericExpressions);
    }

    @Override
    public ComposedExpression createComposedExpression(OperatorEnum op, String id, NumericExpression... numericExpressions) {
        ComposedExpression exp = cpFactory.createComposedExpression();
        exp.setId(id);
        exp.setOperator(op);

        if (ArrayUtils.isNotEmpty(numericExpressions)){
            exp.getExpressions().addAll(Arrays.asList(numericExpressions));
        }
        return exp;
    }

    @Override
    public void reset() {
        this.constraintIdGenerator.reset();
        this.auxExpressionIdGenerator.reset();
    }
}
