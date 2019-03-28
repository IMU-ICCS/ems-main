package eu.melodic.upperware.cpsolver.solver.parser.creator;

import eu.melodic.upperware.cpsolver.solver.parser.SolverData;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.eclipse.emf.common.util.EList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.format;

@Slf4j
abstract class NumericValueSupport {

    static final List<BasicTypeEnum> ACCEPTED_INT_TYPES = Arrays.asList(BasicTypeEnum.INTEGER, BasicTypeEnum.LONG);
    static final List<BasicTypeEnum> ACCEPTED_REAL_TYPES = Arrays.asList(BasicTypeEnum.DOUBLE, BasicTypeEnum.FLOAT);

    IntVar apply(SolverData solverData, Map<String, IntVar> collection,
                 String expressionId, Supplier<NumericValueUpperware> supplier) {
        if (collection.containsKey(expressionId)) {
            return collection.get(expressionId);
        }
        log.info("Creating intVar {}", expressionId);
        IntVar intConstant = solverData.getModel().intVar(expressionId, toIntValue(supplier.get()));
        collection.put(expressionId, intConstant);
        return intConstant;
    }

    RealVar applyReal(SolverData solverData, Map<String, RealVar> collection,
                 String expressionId, Supplier<NumericValueUpperware> supplier) {
        if (collection.containsKey(expressionId)) {
            return collection.get(expressionId);
        }
        log.info("Creating realVar {}", expressionId);
        RealVar realConstant = solverData.getModel().realVar(expressionId, toDoubleValue(supplier.get()));
        collection.put(expressionId, realConstant);
        return realConstant;
    }

    BasicTypeEnum getDomainType(Domain domain) {
        if (isRangeDomain(domain)) {
            return ((RangeDomain) domain).getType();
        } else if (isNumericListDomain(domain)) {
            return ((NumericListDomain) domain).getType();
        }
        throw new RuntimeException(format("Unsupported Domain type %s, only RangeDomain and NumericListDomain are supported",
                domain.getClass().getSimpleName()));
    }

    boolean isRangeDomain(Domain domain) {
        return domain instanceof RangeDomain;
    }

    boolean isNumericListDomain(Domain domain) {
        return domain instanceof NumericListDomain;
    }

    Pair<Integer, Integer> parseIntRangeDomain(RangeDomain rangeDomain){
        NumericValueUpperware from = rangeDomain.getFrom();
        NumericValueUpperware to = rangeDomain.getTo();
        return Pair.of(toIntValue(from), toIntValue(to));
    }

    int[] parseIntNumericListDomain(NumericListDomain numericListDomain) {
        EList<NumericValueUpperware> values = numericListDomain.getValues();
        return values
                .stream()
                .mapToInt(this::toIntValue)
                .toArray();
    }

    private int toIntValue(NumericValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return ((IntegerValueUpperware) value).getValue();
        } else if (value instanceof LongValueUpperware) {
            return (int)((LongValueUpperware) value).getValue();
        }
        throw new RuntimeException(format("Unsupported NumericValueUpperware: %s. " +
                "Only IntegerValueUpperware and LongValueUpperware are accepted in IntVarCreator", value.getClass()));
    }

    Pair<Double, Double> parseDoubleRangeDomain(RangeDomain rangeDomain){
        NumericValueUpperware from = rangeDomain.getFrom();
        NumericValueUpperware to = rangeDomain.getTo();
        return Pair.of(toDoubleValue(from), toDoubleValue(to));
    }

    private double toDoubleValue(NumericValueUpperware value) {
        if (value instanceof DoubleValueUpperware) {
            return ((DoubleValueUpperware) value).getValue();
        } else if (value instanceof FloatValueUpperware) {
            return (double)((FloatValueUpperware) value).getValue();
        }
        throw new RuntimeException(format("Unsupported NumericValueUpperware: %s. " +
                "Only DoubleValueUpperware and FloatValueUpperware are accepted in RealVarCreator", value.getClass()));
    }

}
