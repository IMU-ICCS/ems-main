package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

/**
 * Created by pszkup on 12.12.17.
 */
public interface NewConstraintProblemService {

    ConstraintProblem createConstraintProblem(CamelModel camelModel, String cpName);

}
