package cp_wrapper.utils.test_utils.mockups;

import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantImplMockup extends NumericExpressionImpl implements Constant {
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
