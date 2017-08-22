package eu.paasage.upperware.profiler.generator.filter;

import eu.paasage.camel.provider.AttributeConstraint;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.impl.IntegerValueImpl;
import eu.paasage.upperware.profiler.generator.service.camel.model.Flavour;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
@AllArgsConstructor
public class QuantitativeHardwareRequirementFilter implements Predicate<Flavour> {
    
    private QuantitativeHardwareRequirement quantitativeHardwareRequirement;

    @Override
    public boolean test(Flavour flavour) {

        if (quantitativeHardwareRequirement == null) {
            log.info("** Testing flavour: quantitativeHardwareRequirement is null" );
            return true;
        }

        if (quantitativeHardwareRequirement.getMinCores() > 0 && quantitativeHardwareRequirement.getMaxCores() > 0){
            int cores = getIntValue(flavour.getCores());
            if (!(quantitativeHardwareRequirement.getMinCores() <= cores && cores <= quantitativeHardwareRequirement.getMaxCores())) {
                log.info("** Rejecting flavour: quantitativeHardwareRequirement cores min: " + quantitativeHardwareRequirement.getMinCores()
                        + " max: " + quantitativeHardwareRequirement.getMaxCores() + ". Current value: " + cores );
                return false;
            }
        }

        if (quantitativeHardwareRequirement.getMinRAM() > 0 && quantitativeHardwareRequirement.getMaxRAM() > 0){
            int memory = getIntValue(flavour.getMemory());
            if (!(quantitativeHardwareRequirement.getMinRAM() <= memory && memory <= quantitativeHardwareRequirement.getMaxRAM())) {
                log.info("** Rejecting flavour: quantitativeHardwareRequirement RAM min: " + quantitativeHardwareRequirement.getMinRAM()
                        + " max: " + quantitativeHardwareRequirement.getMaxRAM() + ". Current value: " + memory );
                return false;
            }
        }

        if (quantitativeHardwareRequirement.getMinStorage() > 0 && quantitativeHardwareRequirement.getMaxStorage() > 0){
            int storage = getIntValue(flavour.getStorage());
            if (!(quantitativeHardwareRequirement.getMinStorage() <= storage && storage <= quantitativeHardwareRequirement.getMaxStorage())) {
                log.info("** Rejecting flavour: quantitativeHardwareRequirement storage min: " + quantitativeHardwareRequirement.getMinStorage()
                        + " max: " + quantitativeHardwareRequirement.getMaxStorage() + ". Current value: " + storage );
                return false;
            }
        }

        if (quantitativeHardwareRequirement.getMinCPU() > 0 && quantitativeHardwareRequirement.getMaxCPU() > 0){
            int cpu = getIntValue(flavour.getCpu());
            if (!(quantitativeHardwareRequirement.getMinCPU() <= cpu && cpu <= quantitativeHardwareRequirement.getMaxCPU())) {
                log.info("** Rejecting flavour: quantitativeHardwareRequirement CPU min: " + quantitativeHardwareRequirement.getMinCPU()
                        + " max: " + quantitativeHardwareRequirement.getMaxCPU() + ". Current value: " + cpu );
                return false;
            }
        }

        return true;
    }

    private int getIntValue(AttributeConstraint attributeConstraint){
        int result = 0;
        if (attributeConstraint != null){
            SingleValue toValue = attributeConstraint.getToValue();
            if (toValue != null) {
                result = ((IntegerValueImpl) toValue).getValue();
            }
        }
        return result;
    }

}
