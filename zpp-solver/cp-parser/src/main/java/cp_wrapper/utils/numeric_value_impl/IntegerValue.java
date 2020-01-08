package cp_wrapper.utils.numeric_value_impl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerValue implements NumericValue, IntValueInterface {
    private int value;

    public int getValue() {
        return value;
    }

    public int getIntValue() { return getValue();}

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(NumericValue value) {
        if (value instanceof IntValueInterface) {
            return this.value == ((IntValueInterface) value).getIntValue();
        }
        return false;
    }
}
