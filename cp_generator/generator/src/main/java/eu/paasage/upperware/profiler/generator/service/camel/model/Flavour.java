package eu.paasage.upperware.profiler.generator.service.camel.model;

import eu.paasage.camel.provider.AttributeConstraint;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.impl.IntegerValueImpl;

public class Flavour {

    private ProviderModel provider;
    private String vmTypeName;

    private AttributeConstraint storage;
    private AttributeConstraint cores;
    private AttributeConstraint memory;
    private AttributeConstraint cpu;


    public ProviderModel getProvider() {
        return provider;
    }

    public void setProvider(ProviderModel provider) {
        this.provider = provider;
    }

    public String getVmTypeName() {
        return vmTypeName;
    }

    public void setVmTypeName(String vmTypeName) {
        this.vmTypeName = vmTypeName;
    }

    public AttributeConstraint getStorage() {
        return storage;
    }

    public AttributeConstraint getCores() {
        return cores;
    }

    public AttributeConstraint getCpu() {
        return cpu;
    }

    public AttributeConstraint getMemory() {
        return memory;
    }

    public Integer getStorageInt() {
        return getStorage() != null ? getIntValue(storage) : null;
    }

    public Integer getCoresInt() {
        return getCores() != null ? getIntValue(cores) : null;
    }

    public Integer getCpuInt() {
        return getCpu() != null ? getIntValue(cpu) : null;
    }

    public Integer getMemoryInt() {
        return getMemory() != null ? getIntValue(memory) : null;
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

    public void setStorage(AttributeConstraint storage) {
        this.storage = storage;
    }

    public void setCores(AttributeConstraint cores) {
        this.cores = cores;
    }

    public void setMemory(AttributeConstraint memory) {
        this.memory = memory;
    }

    public void setCpu(AttributeConstraint cpu) {
        this.cpu = cpu;
    }
}
