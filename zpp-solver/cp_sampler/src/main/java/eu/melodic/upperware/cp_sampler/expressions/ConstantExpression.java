package eu.melodic.upperware.cp_sampler.expressions;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ConstantExpression implements Expression {
    @Getter
    private NumericValueInterface value;
}
