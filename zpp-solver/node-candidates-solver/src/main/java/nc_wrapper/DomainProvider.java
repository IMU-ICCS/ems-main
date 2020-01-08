package nc_wrapper;

import cp_wrapper.utils.numeric_value_impl.NumericValue;

public interface DomainProvider {
    NumericValue getMaxValue(int variable);

    NumericValue getMinValue(int variable);

    boolean isInDomain(NumericValue value, int index);
}
