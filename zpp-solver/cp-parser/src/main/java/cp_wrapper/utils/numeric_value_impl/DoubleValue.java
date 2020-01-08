package cp_wrapper.utils.numeric_value_impl;

import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DoubleValue implements NumericValue {
    double value;

    public double getValue() { return value;}

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(NumericValue value) {
        if (value instanceof DoubleValue) {
            return this.value == ((DoubleValue) value).getValue();
        }
        return false;
    }
}
