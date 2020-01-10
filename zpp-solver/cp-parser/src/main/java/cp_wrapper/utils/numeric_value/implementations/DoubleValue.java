package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DoubleValue implements NumericValueInterface {
    @Getter
    @Setter
    private double value;

    @Override
    public boolean equals(NumericValueInterface value) {
        if (value instanceof DoubleValue) {
            return this.value == ((DoubleValue) value).getValue();
        }
        return false;
    }
}
