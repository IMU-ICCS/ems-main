package CPWrapper.Mockups;

import CPWrapper.Utils.ArConstraint;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class ConstraintMockup implements ArConstraint {
        private Collection<String> variables;

        @Override
        public Collection<String> getVariableNames() {
            return variables;
        }

        @Override
        public boolean evaluate(Map<String, Double> variables) {
            return false;
        }
}
