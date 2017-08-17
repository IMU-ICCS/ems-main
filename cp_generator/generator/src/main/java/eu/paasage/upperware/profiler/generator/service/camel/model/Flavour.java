package eu.paasage.upperware.profiler.generator.service.camel.model;

import eu.paasage.camel.provider.AttributeConstraint;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.impl.IntegerValueImpl;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;

import java.util.List;

public class Flavour {

    private static final String VM_STORAGE = "VMStorage";
    private static final String VM_CORES = "VMCores";
    private static final String VM_MEMORY = "VMMemory";

    private static final String VM_CPU = "VMCpu";   //TODO - PSZKUP - is it correct value?

    private ProviderModel provider;
    private String vmTypeName;

    private AttributeConstraint storage;
    private AttributeConstraint cores;
    private AttributeConstraint memory;
    private AttributeConstraint cpu;

    public static Flavour createFlavour(ProviderModel provider, String vmTypeName, List<AttributeConstraint> attributes){
        Flavour instance = new Flavour();
        instance.setProvider(provider);
        instance.setVmTypeName(vmTypeName);

        ProviderModelTool.getAttributeConstraintByName(attributes, VM_STORAGE).ifPresent(attributeConstraint -> instance.storage = attributeConstraint);
        ProviderModelTool.getAttributeConstraintByName(attributes, VM_CORES).ifPresent(attributeConstraint -> instance.cores = attributeConstraint);
        ProviderModelTool.getAttributeConstraintByName(attributes, VM_MEMORY).ifPresent(attributeConstraint -> instance.memory = attributeConstraint);
        ProviderModelTool.getAttributeConstraintByName(attributes, VM_CPU).ifPresent(attributeConstraint -> instance.cpu = attributeConstraint);

        return instance;
    }

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

}
