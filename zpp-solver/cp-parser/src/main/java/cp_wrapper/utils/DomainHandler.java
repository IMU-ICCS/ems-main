package cp_wrapper.utils;
/*
    All variable domains are abstracted as finite sequences of subsequent
    natural numbers - this class is responsible for turning those "domain indices"
    into corresponding values. Currently only RangeDomain and NumericListDomain
    domains are supported.
 */
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

    public static double getRangeValue(int valueIndex, RangeDomain domain) {
        NumericValueUpperware min = domain.getFrom();
        if (min instanceof IntegerValueUpperware) {
            return (((IntegerValueUpperware) min).getValue() + valueIndex);
        } else {
            throw new RuntimeException("Only integer RangeDomains are supported!");
        }
    }

    public static double getNumericListValue(int valueIndex, NumericListDomain domain) {
        List<NumericValueUpperware> values = domain.getValues();
        if (values.size() <= valueIndex) {
            throw new RuntimeException();
        }
        return ExpressionEvaluator.getValueOfNumericInterface(values.get(valueIndex));
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
}
