package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.*;

import java.util.List;

/**
 * Created by pszkup on 16.08.17.
 */
public interface VariableService {

    String getVarName(VariableType variableType, String vmName);


    Variable createIntegerVariable(VariableType variableType, String componentId, Domain domain);

    Variable createDoubleVariable(VariableType variableType, String componentId, Domain domain);

    Variable createFloatVariable(VariableType variableType, String componentId, Domain domain);


    Variable createIntegerVariable(String name, VariableType variableType, String componentId, Domain domain);

    Variable createDoubleVariable(String name, VariableType variableType, String componentId, Domain domain);

    Variable createFloatVariable(String name, VariableType variableType, String componentId, Domain domain);


    RangeDomain createIntegerRangeDomain(int from, int to);

    RangeDomain createDoubleRangeDomain(double from, double to);

    RangeDomain createLongRangeDomain(long from, long to);

    RangeDomain createFloatRangeDomain(float from, float to);


    NumericListDomain createIntegerListDomain(List<Integer> values);

    NumericListDomain createDoubleListDomain(List<Double> values);

    NumericListDomain createLongListDomain(List<Long> values);

    NumericListDomain createFloatListDomain(List<Float> values);
}
