package eu.paasage.upperware.profiler.generator.service.camel.parser.elements;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetricElement implements ExpressionElement {
    private static final String LOOKS_LIKE = "metric";
    private static final int TYPE_ID = 1001;

    public static final MetricElement INSTANCE = new MetricElement();

    @Override
    public String getLooksLike() {
        return LOOKS_LIKE;
    }

    @Override
    public int getTypeId() {
        return TYPE_ID;
    }
}
