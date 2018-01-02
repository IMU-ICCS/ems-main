package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.*;

import java.util.List;

/**
 * Created by pszkup on 16.08.17.
 */
public interface VariableService {

    String getVarName(VariableType variableType, String vmName);

    Variable createVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, int min, int max);

    List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp);


    Variable createIntegerVariable(VariableType variableType, String componentId, Domain domain);

    Variable createDoubleVariable(VariableType variableType, String componentId, Domain domain);

    Variable createLongVariable(VariableType variableType, String componentId, Domain domain);

    Variable createFloatVariable(VariableType variableType, String componentId, Domain domain);


    @Deprecated
    Variable createIntegerVariableWithRangeDomain(String varName, int lowerLimit, int upperLimit, VariableType variableType, String componentId);

    @Deprecated
    Variable createDoubleVariableWithRangeDomain(String name, double lowerLimit, double upperLimit, VariableType variableType, String componentId);

    @Deprecated
    Variable createLongVariableWithRangeDomain(String name, long lowerLimit, long upperLimit, VariableType variableType, String componentId);

    @Deprecated
    Variable createFloatVariableWithRangeDomain(String name, float lowerLimit, float upperLimit, VariableType variableType, String componentId);


    RangeDomain createIntegerRangeDomain(int from, int to);

    RangeDomain createDoubleRangeDomain(double from, double to);

    RangeDomain createLongRangeDomain(long from, long to);

    RangeDomain createFloatRangeDomain(float from, float to);


    NumericListDomain createIntegerListDomain(List<Integer> values);

    NumericListDomain createDoubleListDomain(List<Double> values);

    NumericListDomain createLongListDomain(List<Long> values);

    NumericListDomain createFloatListDomain(List<Float> values);
}
