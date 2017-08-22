package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;

public interface TypesFactoryService {

    IntegerValueUpperware getIntegerValueUpperware(int value);

    DoubleValueUpperware getDoubleValueUpperware(double value);

    FloatValueUpperware getFloatValueUpperware(float value);

}
