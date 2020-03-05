package eu.melodic.upperware.cp_sampler.expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class VariableExpression implements Expression {
    @Getter
    private String variableName;
}
