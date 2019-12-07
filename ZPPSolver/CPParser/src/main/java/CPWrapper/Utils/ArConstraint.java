package CPWrapper.Utils;

import java.util.Collection;
import java.util.Map;

public interface ArConstraint {

    public Collection<String> getVariableNames();

    public boolean evaluate(Map<String, Double> variables);
}
