package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

/**
 * This interface defines the services offered by a database.
 *
 * @author danielromero
 */
public interface IDatabaseProxy {

    void saveModels(PaasageConfiguration pc, ConstraintProblem cp);

    void saveModels(PaasageConfiguration pc, ConstraintProblem cp, CamelModel camelModel);

}
