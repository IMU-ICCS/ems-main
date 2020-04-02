package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
public class IntegerValue implements NumericValueInterface {
    @Setter
    private int value;

    public int getIntValue() { return value;}

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public boolean representsSameNumber(NumericValueInterface value) {
        if (value instanceof LongValue) {
            return value.getIntValue() == this.value;
        } else {
            return equals(value);
        }
    }


    @Override
    public double getDoubleValue() {
        return value;
    }
}
