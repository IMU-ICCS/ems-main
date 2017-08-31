package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Variable;

import java.util.List;

/**
 * Created by pszkup on 16.08.17.
 */
public interface VariableService {

    Variable createVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, int min, int max);

    List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp);

    Variable createIntegerVariableWithRangeDomain(String varName, int lowerLimit, int upperLimit);

    Variable createDoubleVariableWithRangeDomain(String name, double lowerLimit, double upperLimit);

}
