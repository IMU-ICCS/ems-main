package cp_wrapper.utils.constraint;

import cp_wrapper.utils.numeric_value.NumericValueInterface;

import java.util.Collection;
import java.util.Map;

public interface Constraint {

    Collection<String> getVariableNames();

    boolean evaluate(Map<String, NumericValueInterface> variables);
}
