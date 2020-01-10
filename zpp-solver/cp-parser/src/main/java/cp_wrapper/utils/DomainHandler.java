package cp_wrapper.utils;
/*
    All variable domains are abstracted as finite sequences of subsequent
    natural numbers - this class is responsible for turning those "domain indices"
    into corresponding values. Currently only RangeDomain and NumericListDomain
    domains are supported.
 */
import cp_wrapper.utils.numeric_value.IntValueInterface;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.NumericListDomain;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import java.util.List;

public class DomainHandler {
    public static boolean isRangeDomain(Domain domain){
        return domain instanceof RangeDomain;
    }

    public static boolean isNumericListDomain(Domain domain){
        return domain instanceof NumericListDomain;
    }

    public static NumericValueInterface getRangeValue(int valueIndex, RangeDomain domain) {
        NumericValueUpperware min = domain.getFrom();
        if (min instanceof IntegerValueUpperware) {
            return new IntegerValue(((IntegerValueUpperware) min).getValue() + valueIndex);
        } else {
            throw new RuntimeException("Only integer RangeDomains are supported!");
        }
    }

    public static NumericValueInterface getNumericListValue(int valueIndex, NumericListDomain domain) {
        List<NumericValueUpperware> values = domain.getValues();
        if (values.size() <= valueIndex) {
            throw new RuntimeException();
        }
        return ExpressionEvaluator.convertNumericInterfaceToNumericValue(values.get(valueIndex));
    }

    public static int getMaxDomainValue(Domain domain) {
        if (domain instanceof NumericListDomain) {
            return ((NumericListDomain) domain).getValues().size() - 1;
        } else if (domain instanceof RangeDomain) {
            return ExpressionEvaluator
                    .getValueOfIntegerNumericInterface(
                            (IntegerValueUpperware) ((RangeDomain) domain).getTo()
                    ) -
                    ExpressionEvaluator
                    .getValueOfIntegerNumericInterface(
                            (IntegerValueUpperware) ((RangeDomain) domain).getFrom()
                    );
        }

        throw new RuntimeException("Only integer RangeDomains are supported!");
    }

    public static int getMinDomainValue(Domain domain) {
        if (domain instanceof NumericListDomain || domain instanceof  RangeDomain) {
            return 0;
        }

        throw new RuntimeException("Only integer RangeDomains are supported!");
    }

    public static NumericValueInterface getMaxValue(Domain domain) {
        if (isRangeDomain(domain)) {
            return ExpressionEvaluator.convertNumericInterfaceToNumericValue(((RangeDomain) domain).getTo());
        } else if (isNumericListDomain(domain)) {
            List<NumericValueUpperware> values = ((NumericListDomain) domain).getValues();
            return ExpressionEvaluator.convertNumericInterfaceToNumericValue(values.get(values.size() - 1));
        }
        throw new RuntimeException("Unsupported domain type");
    }

    public static NumericValueInterface getMinValue(Domain domain) {
        if (isRangeDomain(domain)) {
            return ExpressionEvaluator.convertNumericInterfaceToNumericValue(((RangeDomain) domain).getFrom());
        } else if (isNumericListDomain(domain)) {
            List<NumericValueUpperware> values = ((NumericListDomain) domain).getValues();
            return ExpressionEvaluator.convertNumericInterfaceToNumericValue(values.get(0));
        }
        throw new RuntimeException("Unsupported domain type");
    }

    private static boolean isInList(NumericValueInterface value, List<NumericValueUpperware> list) {
        for (NumericValueUpperware v : list) {
            if (value.equals(ExpressionEvaluator.convertNumericInterfaceToNumericValue(v))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInDomain(NumericValueInterface value, Domain domain) {
        if (isRangeDomain(domain)) {
            if (!(value instanceof IntValueInterface)) {
                return false;
            }
            int val = ((IntValueInterface) value).getIntValue();
            return val >= ExpressionEvaluator.getValueOfNumericInterface(((RangeDomain) domain).getFrom())
                    &&
                    val <= ExpressionEvaluator.getValueOfNumericInterface(((RangeDomain) domain).getTo());
        } else if (isNumericListDomain(domain)) {
               return isInList(value, ((NumericListDomain) domain).getValues());
        }
        throw new RuntimeException("Unsupported domain type");
    }
}
