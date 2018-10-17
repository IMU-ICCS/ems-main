package eu.paasage.upperware.profiler.generator.service.camel.parser;

import camel.core.CamelModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface ExpressionService {

    void parse(String finalExpressionName, String formula, ConstraintProblem cp, CamelModel camelModel, String cacheKey);

}
