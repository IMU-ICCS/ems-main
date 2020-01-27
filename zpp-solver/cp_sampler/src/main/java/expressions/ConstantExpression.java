package expressions;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ConstantExpression implements Expression {
    @Getter
    private NumericValueInterface value;

    @Override
    public String toString() {
        return value.toString();
    }
}
