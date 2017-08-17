package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface ConstraintProblemService {

    ConstraintProblem derivateConstraintProblem(CamelModel camel, PaasageConfiguration configuration);

}
