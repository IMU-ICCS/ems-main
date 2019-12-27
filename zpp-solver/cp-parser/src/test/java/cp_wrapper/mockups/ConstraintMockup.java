package cp_wrapper.mockups;

import cp_wrapper.utils.constraint.Constraint;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class ConstraintMockup implements Constraint {
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
