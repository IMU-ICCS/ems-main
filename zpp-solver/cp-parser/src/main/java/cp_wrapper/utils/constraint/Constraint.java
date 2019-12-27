package cp_wrapper.utils.constraint;

import java.util.Collection;
import java.util.Map;

public interface Constraint {

    Collection<String> getVariableNames();

    boolean evaluate(Map<String, Double> variables);
}
