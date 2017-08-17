package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

import java.util.List;

public interface DimensionDerivatorService {

    void createDimensions(CamelModel camel, ConstraintProblem cp, List<OptimisationRequirement> complexOptRequirements);
}
