package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.IntValueInterface;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class IntegerValue implements NumericValueInterface, IntValueInterface {
    @Getter
    @Setter
    private int value;

    public int getIntValue() { return getValue();}

    @Override
    public boolean equals(NumericValueInterface value) {
        if (value instanceof IntValueInterface) {
            return this.value == ((IntValueInterface) value).getIntValue();
        }
        return false;
    }
}
