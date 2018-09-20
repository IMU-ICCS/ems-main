package eu.paasage.upperware.profiler.generator.service.camel.parser.elements;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberElement implements ExpressionElement {

    private static final String LOOKS_LIKE = "aux_expression";
    private static final int TYPE_ID = 1;

    @Override
    public String getLooksLike() {
        return LOOKS_LIKE;
    }

    @Override
    public int getTypeId() {
        return TYPE_ID;
    }
}
