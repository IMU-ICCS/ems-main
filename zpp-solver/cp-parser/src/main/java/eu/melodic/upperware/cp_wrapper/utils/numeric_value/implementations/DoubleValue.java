package eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations;

import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
public class DoubleValue implements NumericValueInterface {
    @Setter
    private double value;

    @Override
    public double getDoubleValue() {
        return value;
    }

    @Override
    public int getIntValue() {
        return (int) value;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public boolean representsSameNumber(NumericValueInterface value) {
        return equals(value);
    }
}
