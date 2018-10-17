package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.*;
import org.eclipse.emf.common.util.EList;

import java.util.List;
import java.util.Optional;

/**
 * Created by pszkup on 16.08.17.
 */
public interface VariableService {

    String getVarName(VariableType variableType, String vmName);


    CpVariable createIntegerCpVariable(VariableType variableType, String componentId, Domain domain);

    CpVariable createDoubleCpVariable(VariableType variableType, String componentId, Domain domain);

    CpVariable createFloatCpVariable(VariableType variableType, String componentId, Domain domain);


    CpVariable createIntegerCpVariable(String name, VariableType variableType, String componentId, Domain domain);

    CpVariable createDoubleCpVariable(String name, VariableType variableType, String componentId, Domain domain);

    CpVariable createFloatCpVariable(String name, VariableType variableType, String componentId, Domain domain);


    RangeDomain createIntegerRangeDomain(int from, int to);

    RangeDomain createDoubleRangeDomain(double from, double to);

    RangeDomain createLongRangeDomain(long from, long to);

    RangeDomain createFloatRangeDomain(float from, float to);


    NumericListDomain createIntegerListDomain(List<Integer> values);

    NumericListDomain createDoubleListDomain(List<Double> values);

    NumericListDomain createLongListDomain(List<Long> values);

    NumericListDomain createFloatListDomain(List<Float> values);

    Optional<CpVariable> getByName(EList<CpVariable> expressions, String name);
}
