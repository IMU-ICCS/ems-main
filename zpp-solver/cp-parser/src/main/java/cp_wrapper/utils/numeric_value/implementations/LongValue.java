package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LongValue implements NumericValueInterface {
    @Getter
    @Setter
    private long value;

    public int getIntValue() { return (int) value;}

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
