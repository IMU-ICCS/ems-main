package cp_wrapper.utils.test_utils.mockups;

import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CpMetricImplMockup extends NumericExpressionImpl implements CpMetric {
    private BasicTypeEnum type;
    private NumericValueUpperware value;
    @Override
    public BasicTypeEnum getType() {
        return type;
    }

    @Override
    public void setType(BasicTypeEnum value) {
        this.type = value;
    }

    @Override
    public NumericValueUpperware getValue() {
        return value;
    }

    @Override
    public void setValue(NumericValueUpperware value) {
        this.value = value;
    }
}
