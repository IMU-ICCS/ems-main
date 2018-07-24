package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface SloService {

    void update(CamelModel cModel, ConstraintProblem cpModel);
}
