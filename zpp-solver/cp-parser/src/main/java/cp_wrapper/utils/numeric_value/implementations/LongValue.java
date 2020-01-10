package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.IntValueInterface;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LongValue implements NumericValueInterface, IntValueInterface {
    @Getter
    @Setter
    private long value;

    public int getIntValue() { return (int) value;}

    @Override
    public boolean equals(NumericValueInterface value) {
        if (value instanceof LongValue) {
            return this.value == ((LongValue) value).getValue();
        } else if (value instanceof IntegerValue) {
            return this.value == (long) ((IntegerValue) value).getValue();
        }
        return false;
    }
}
