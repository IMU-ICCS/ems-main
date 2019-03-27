package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.types.*;

public interface TypesFactoryService {

    IntegerValueUpperware getIntegerValueUpperware(int value);

    DoubleValueUpperware getDoubleValueUpperware(double value);

    FloatValueUpperware getFloatValueUpperware(float value);

    LongValueUpperware getLongValueUpperware(long value);

    NumericValueUpperware copy(NumericValueUpperware from);

}
