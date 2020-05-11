package eu.melodic.upperware.cp_wrapper.utils.test_utils.mockups;

import eu.melodic.upperware.cp_wrapper.utils.constraint.Constraint;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
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
        public boolean evaluate(Map<String, NumericValueInterface> variables) {
            return false;
        }
}
