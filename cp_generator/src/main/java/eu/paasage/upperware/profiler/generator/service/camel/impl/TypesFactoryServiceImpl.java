package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TypesFactoryServiceImpl implements TypesFactoryService {

    private TypesFactory typesFactory;

    @Override
    public IntegerValueUpperware getIntegerValueUpperware(int value) {
        IntegerValueUpperware result= typesFactory.createIntegerValueUpperware();
        result.setValue(value);
        return result;
    }

    @Override
    public DoubleValueUpperware getDoubleValueUpperware(double value) {
        DoubleValueUpperware result= typesFactory.createDoubleValueUpperware();
        result.setValue(value);
        return result;
    }

    @Override
    public FloatValueUpperware getFloatValueUpperware(float value) {
        FloatValueUpperware result= typesFactory.createFloatValueUpperware();
        result.setValue(value);
        return result;
    }

    @Override
    public LongValueUpperware getLongValueUpperware(long value) {
        LongValueUpperware result= typesFactory.createLongValueUpperware();
        result.setValue(value);
        return result;
    }

}
