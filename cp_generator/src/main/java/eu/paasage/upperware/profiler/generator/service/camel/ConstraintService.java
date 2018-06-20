package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.*;

public interface ConstraintService extends GeneratorService {

    ComparisonExpression createComparisonExpression(Expression exp1, ComparatorEnum comparatorEnum, Expression exp2);

    ComparisonExpression createComparisonExpression(Expression exp1, ComparatorEnum comparatorEnum, Expression exp2, String id);

    ComposedExpression createComposedExpression(OperatorEnum op, NumericExpression... numericExpressions);

    ComposedExpression createComposedExpression(OperatorEnum op, String id, NumericExpression... numericExpressions);

}
