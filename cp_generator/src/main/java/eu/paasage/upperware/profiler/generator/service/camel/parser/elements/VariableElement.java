package eu.paasage.upperware.profiler.generator.service.camel.parser.elements;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VariableElement implements ExpressionElement {
    private static final String LOOKS_LIKE = "variable";
    private static final int TYPE_ID = 1000;

    public static final VariableElement INSTANCE = new VariableElement();

    @Override
    public String getLooksLike() {
        return LOOKS_LIKE;
    }

    @Override
    public int getTypeId() {
        return TYPE_ID;
    }
}
