package cp_wrapper.utils.numeric_value;

public interface NumericValueInterface {
    double getDoubleValue();
    int getIntValue();
    boolean isInteger();
    @Override
    boolean equals(Object object);

    boolean representsSameNumber(NumericValueInterface value);

    @Override
    int hashCode();
}
