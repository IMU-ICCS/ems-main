package eu.paasage.upperware.profiler.generator.service.camel.creator;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

public interface VariableCreator<E extends Number> {

    CpVariable createCpVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain);

    CpVariable createCpVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain, String variableName);

    default Pair<NumericValueUpperware, NumericValueUpperware> unpackDomain(NumericDomain numericDomain) {
        if (numericDomain instanceof RangeDomain) {
            RangeDomain rangeDomain = (RangeDomain) numericDomain;
            NumericValueUpperware from = rangeDomain.getFrom();
            NumericValueUpperware to = rangeDomain.getTo();
            return ImmutablePair.of(from, to);
        } else if (numericDomain instanceof NumericListDomain) {
            NumericListDomain numericListDomain = (NumericListDomain) numericDomain;
            EList<NumericValueUpperware> values = numericListDomain.getValues();
            NumericValueUpperware from = values.get(0);
            NumericValueUpperware to = values.get(values.size() - 1);
            return ImmutablePair.of(from, to);
        }

        throw new GeneratorException("Could not unpack Domain: " + numericDomain.getClass());
    }

}
