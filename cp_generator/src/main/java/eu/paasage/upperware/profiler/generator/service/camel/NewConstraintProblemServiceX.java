package eu.paasage.upperware.profiler.generator.service.camel;

import camel.core.CamelModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface NewConstraintProblemServiceX {

    ConstraintProblem createConstraintProblem(CamelModel camelModel, String cpName);

}
