package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class IntegerValue implements NumericValueInterface {
    @Setter
    private int value;

    public int getIntValue() { return value;}

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public boolean equals(NumericValueInterface value) {
        if (value instanceof IntegerValue || value instanceof LongValue) {
            return this.value == value.getIntValue();
        }
        return false;
    }

    @Override
    public double getDoubleValue() {
        return value;
    }
}
