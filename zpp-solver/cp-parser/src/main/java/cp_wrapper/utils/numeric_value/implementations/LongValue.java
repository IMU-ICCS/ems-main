package cp_wrapper.utils.numeric_value.implementations;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
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
    public double getDoubleValue() {
        return value;
    }
}
