package eu.melodic.upperware.utilitygenerator.model.DTO;

import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;

public class MetricDTOFactory {

    public static MetricDTO createMetricDTO(Constant metric){

        if (metric instanceof IntegerValueUpperware){
            return new IntMetricDTO(metric.getId(), ((IntegerValueUpperware) metric).getValue());
        }
        else if (metric instanceof DoubleValueUpperware){
            return new DoubleMetricDTO(metric.getId(), ((DoubleValueUpperware) metric).getValue());
        }
        else if (metric instanceof LongValueUpperware){
            return new IntMetricDTO(metric.getId(), (int)((LongValueUpperware) metric).getValue());
        }
        else {
            return new FloatMetricDTO(metric.getId(), ((FloatValueUpperware) metric).getValue());
        }
    }
}
