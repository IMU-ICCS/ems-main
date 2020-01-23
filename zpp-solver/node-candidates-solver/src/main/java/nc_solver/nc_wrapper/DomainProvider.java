package nc_solver.nc_wrapper;

import cp_wrapper.utils.numeric_value.NumericValueInterface;

public interface DomainProvider {
    NumericValueInterface getMaxValue(int variable);

    NumericValueInterface getMinValue(int variable);

    boolean isInDomain(NumericValueInterface value, int index);
}
