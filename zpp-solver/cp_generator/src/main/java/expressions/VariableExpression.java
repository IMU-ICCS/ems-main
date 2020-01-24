package expressions;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public class VariableExpression implements Expression {
    @Getter
    private String variableName;

    @Override
    public String toString() {
        return variableName;
    }
}
