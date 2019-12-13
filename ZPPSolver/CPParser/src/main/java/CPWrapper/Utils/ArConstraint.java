package CPWrapper.Utils;

import java.util.Collection;
import java.util.Map;

public interface ArConstraint {

    Collection<String> getVariableNames();

    boolean evaluate(Map<String, Double> variables);
}
