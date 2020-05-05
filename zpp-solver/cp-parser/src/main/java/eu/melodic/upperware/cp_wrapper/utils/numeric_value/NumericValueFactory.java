package eu.melodic.upperware.cp_wrapper.utils.numeric_value;

import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.paasage.upperware.metamodel.types.*;

public class NumericValueFactory {
    public static NumericValueInterface fromNumericValueInterface(NumericValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return new IntegerValue(((IntegerValueUpperware) value).getValue());
        } else if (value instanceof LongValueUpperware) {
            return new LongValue(((LongValueUpperware) value).getValue());
        } else if (value instanceof DoubleValueUpperware) {
            return new DoubleValue(((DoubleValueUpperware) value).getValue());
        } else if (value instanceof FloatValueUpperware) {
            return new DoubleValue(((FloatValueUpperware) value).getValue());
        }
        throw new RuntimeException("Unsupported NumericValueUpperware implementation");
    }
}
