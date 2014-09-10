package eu.paasage.camel.examples.model.submodels;

import eu.paasage.camel.provider.*;
import eu.paasage.camel.type.*;
import org.javatuples.Quintet;

/**
 * Created by orzech on 27/07/14.
 */
public class ProviderModelClass {

    public static Quintet<ProviderModel, Feature, Constraint, Constraint, Constraint> createProviderModel() {
        ProviderModel providerModel = ProviderFactory.eINSTANCE.createProviderModel();

        Feature vmFeature = ProviderFactory.eINSTANCE.createFeature();
        vmFeature.setName("VM");
        FeatCardinality vmCardinality = ProviderFactory.eINSTANCE.createFeatCardinality();
        vmCardinality.setValue(1);
        vmCardinality.setCardinalityMin(1);
        vmCardinality.setCardinalityMax(8);
        vmFeature.setFeatureCardinality(vmCardinality);

        providerModel.setRootFeature(vmFeature);

        Attribute vmType = ProviderFactory.eINSTANCE.createAttribute();
        vmType.setName("vmType");

        Enumeration vmTypes = TypeFactory.eINSTANCE.createEnumeration();

        EnumerateValue smallVm = TypeFactory.eINSTANCE.createEnumerateValue();
        smallVm.setName("SMALL");
        smallVm.setValue(0);
        vmTypes.getValues().add(smallVm);

        EnumerateValue mediumVm = TypeFactory.eINSTANCE.createEnumerateValue();
        mediumVm.setName("MEDIUM");
        mediumVm.setValue(1);
        vmTypes.getValues().add(mediumVm);

        EnumerateValue largeVm = TypeFactory.eINSTANCE.createEnumerateValue();
        largeVm.setName("LARGE");
        largeVm.setValue(2);
        vmTypes.getValues().add(largeVm);

        vmType.setValueType(vmTypes);

        vmFeature.getAttributes().add(vmType);

        Attribute vmCPU = ProviderFactory.eINSTANCE.createAttribute();
        vmCPU.setName("vmCPU");
        Range vmCPURange = TypeFactory.eINSTANCE.createRange();

        vmCPURange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

        Limit minCPU = TypeFactory.eINSTANCE.createLimit();
        minCPU.setIncluded(true);
        FloatValue minCPUValue = TypeFactory.eINSTANCE.createFloatValue();
        minCPUValue.setValue(1);
        minCPU.setValue(minCPUValue);

        Limit maxCPU = TypeFactory.eINSTANCE.createLimit();
        maxCPU.setIncluded(true);
        FloatValue maxCPUValue = TypeFactory.eINSTANCE.createFloatValue();
        maxCPUValue.setValue(5);
        maxCPU.setValue(maxCPUValue);

        vmCPURange.setLowerLimit(minCPU);
        vmCPURange.setUpperLimit(maxCPU);

        vmCPU.setValueType(vmCPURange);

        vmFeature.getAttributes().add(vmCPU);

        Attribute vmMemory = ProviderFactory.eINSTANCE.createAttribute();
        vmMemory.setName("vmMemory");

        Range vmMemoryRange = TypeFactory.eINSTANCE.createRange();

        vmMemoryRange.setPrimitiveType(TypeEnum.INT_TYPE);

        Limit minMemory = TypeFactory.eINSTANCE.createLimit();
        minMemory.setIncluded(true);
        IntValue minMemoryValue = TypeFactory.eINSTANCE.createIntValue();
        minMemoryValue.setValue(2048);
        minMemory.setValue(minMemoryValue);

        Limit maxMemory = TypeFactory.eINSTANCE.createLimit();
        maxMemory.setIncluded(true);
        IntValue maxMemoryValue = TypeFactory.eINSTANCE.createIntValue();
        maxMemoryValue.setValue(16384);
        maxMemory.setValue(maxMemoryValue);

        vmMemoryRange.setLowerLimit(minMemory);
        vmMemoryRange.setUpperLimit(maxMemory);

        vmMemory.setValueType(vmMemoryRange);

        vmFeature.getAttributes().add(vmMemory);

        Attribute vmStorage = ProviderFactory.eINSTANCE.createAttribute();
        vmStorage.setName("vmStorage");

        Range vmStorageRange = TypeFactory.eINSTANCE.createRange();

        vmStorageRange.setPrimitiveType(TypeEnum.INT_TYPE);

        Limit minStorage = TypeFactory.eINSTANCE.createLimit();
        minStorage.setIncluded(true);
        IntValue minStorageValue = TypeFactory.eINSTANCE.createIntValue();
        minStorageValue.setValue(200);
        minStorage.setValue(minStorageValue);

        Limit maxStorage = TypeFactory.eINSTANCE.createLimit();
        maxStorage.setIncluded(true);
        IntValue maxStorageValue = TypeFactory.eINSTANCE.createIntValue();
        maxStorageValue.setValue(2048);
        maxStorage.setValue(maxStorageValue);

        vmStorageRange.setLowerLimit(minStorage);
        vmStorageRange.setUpperLimit(maxStorage);

        vmStorage.setValueType(vmStorageRange);

        vmFeature.getAttributes().add(vmStorage);

        Attribute vmCores = ProviderFactory.eINSTANCE.createAttribute();
        vmCores.setName("vmCores");

        Range vmCoresRange = TypeFactory.eINSTANCE.createRange();

        vmCoresRange.setPrimitiveType(TypeEnum.INT_TYPE);

        Limit minCores = TypeFactory.eINSTANCE.createLimit();
        minCores.setIncluded(true);
        IntValue minCoresValue = TypeFactory.eINSTANCE.createIntValue();
        minCoresValue.setValue(1);
        minCores.setValue(minCoresValue);

        Limit maxCores = TypeFactory.eINSTANCE.createLimit();
        maxCores.setIncluded(true);
        IntValue maxCoresValue = TypeFactory.eINSTANCE.createIntValue();
        maxCoresValue.setValue(128);
        maxCores.setValue(maxCoresValue);

        vmCoresRange.setLowerLimit(minCores);
        vmCoresRange.setUpperLimit(maxCores);

        vmCores.setValueType(vmCoresRange);

        vmFeature.getAttributes().add(vmCores);

        Implies smallVmConstraint = ProviderFactory.eINSTANCE.createImplies();

        smallVmConstraint.setFrom(vmFeature);
        smallVmConstraint.setTo(vmFeature);

        AttributeConstraint smallVmCPUConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        smallVmCPUConstraint.setFrom(vmType);
        StringValue smallVmCPUConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        smallVmCPUConstraintFrom.setValue("SMALL");
        smallVmCPUConstraint.setFromValue(smallVmCPUConstraintFrom);

        smallVmCPUConstraint.setTo(vmCPU);
        FloatValue smallCPUConstraintTo = TypeFactory.eINSTANCE.createFloatValue();
        smallCPUConstraintTo.setValue(1);
        smallVmCPUConstraint.setToValue(smallCPUConstraintTo);

        smallVmConstraint.getAttributeConstraints().add(smallVmCPUConstraint);

        AttributeConstraint smallVmMemoryConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        smallVmMemoryConstraint.setFrom(vmType);
        StringValue smallVmMemoryConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        smallVmMemoryConstraintFrom.setValue("SMALL");
        smallVmMemoryConstraint.setFromValue(smallVmMemoryConstraintFrom);

        smallVmMemoryConstraint.setTo(vmMemory);
        IntValue smallMemoryConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        smallMemoryConstraintTo.setValue(2048);
        smallVmMemoryConstraint.setToValue(smallMemoryConstraintTo);

        smallVmConstraint.getAttributeConstraints().add(smallVmMemoryConstraint);

        AttributeConstraint smallVmStorageConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        smallVmStorageConstraint.setFrom(vmType);
        StringValue smallVmStorageConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        smallVmStorageConstraintFrom.setValue("SMALL");
        smallVmStorageConstraint.setFromValue(smallVmStorageConstraintFrom);

        smallVmStorageConstraint.setTo(vmStorage);
        IntValue smallVmStorageConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        smallVmStorageConstraintTo.setValue(200);
        smallVmStorageConstraint.setToValue(smallVmStorageConstraintTo);

        smallVmConstraint.getAttributeConstraints().add(smallVmStorageConstraint);

        AttributeConstraint smallVmCoresConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        smallVmCoresConstraint.setFrom(vmType);
        StringValue smallVmCoresConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        smallVmCoresConstraintFrom.setValue("SMALL");
        smallVmCoresConstraint.setFromValue(smallVmCoresConstraintFrom);

        smallVmCoresConstraint.setTo(vmCores);
        IntValue smallVmCoresConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        smallVmCoresConstraintTo.setValue(1);
        smallVmCoresConstraint.setToValue(smallVmCoresConstraintTo);

        smallVmConstraint.getAttributeConstraints().add(smallVmCoresConstraint);

        providerModel.getConstraints().add(smallVmConstraint);

        Implies mediumVmConstraint = ProviderFactory.eINSTANCE.createImplies();

        mediumVmConstraint.setFrom(vmFeature);
        mediumVmConstraint.setTo(vmFeature);

        AttributeConstraint mediumVmCPUConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        mediumVmCPUConstraint.setFrom(vmType);
        StringValue mediumVmCPUConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        mediumVmCPUConstraintFrom.setValue("MEDIUM");
        mediumVmCPUConstraint.setFromValue(mediumVmCPUConstraintFrom);

        mediumVmCPUConstraint.setTo(vmCPU);
        FloatValue mediumCPUConstraintTo = TypeFactory.eINSTANCE.createFloatValue();
        mediumCPUConstraintTo.setValue(2);
        mediumVmCPUConstraint.setToValue(mediumCPUConstraintTo);

        mediumVmConstraint.getAttributeConstraints().add(mediumVmCPUConstraint);

        AttributeConstraint mediumVmMemoryConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        mediumVmMemoryConstraint.setFrom(vmType);
        StringValue mediumVmMemoryConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        mediumVmMemoryConstraintFrom.setValue("MEDIUM");
        mediumVmMemoryConstraint.setFromValue(mediumVmMemoryConstraintFrom);

        mediumVmMemoryConstraint.setTo(vmMemory);
        IntValue mediumMemoryConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        mediumMemoryConstraintTo.setValue(4096);
        mediumVmMemoryConstraint.setToValue(mediumMemoryConstraintTo);

        mediumVmConstraint.getAttributeConstraints().add(mediumVmMemoryConstraint);

        AttributeConstraint mediumVmStorageConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        mediumVmStorageConstraint.setFrom(vmType);
        StringValue mediumVmStorageConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        mediumVmStorageConstraintFrom.setValue("MEDIUM");
        mediumVmStorageConstraint.setFromValue(mediumVmStorageConstraintFrom);

        mediumVmStorageConstraint.setTo(vmStorage);
        IntValue mediumVmStorageConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        mediumVmStorageConstraintTo.setValue(512);
        mediumVmStorageConstraint.setToValue(mediumVmStorageConstraintTo);

        mediumVmConstraint.getAttributeConstraints().add(mediumVmStorageConstraint);

        AttributeConstraint mediumVmCoresConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        mediumVmCoresConstraint.setFrom(vmType);
        StringValue mediumVmCoresConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        mediumVmCoresConstraintFrom.setValue("MEDIUM");
        mediumVmCoresConstraint.setFromValue(mediumVmCoresConstraintFrom);

        mediumVmCoresConstraint.setTo(vmCores);
        IntValue mediumVmCoresConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        mediumVmCoresConstraintTo.setValue(6);
        mediumVmCoresConstraint.setToValue(mediumVmCoresConstraintTo);

        mediumVmConstraint.getAttributeConstraints().add(mediumVmCoresConstraint);

        providerModel.getConstraints().add(mediumVmConstraint);

        Implies largeVmConstraint = ProviderFactory.eINSTANCE.createImplies();

        largeVmConstraint.setFrom(vmFeature);
        largeVmConstraint.setTo(vmFeature);

        AttributeConstraint largeVmCPUConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        largeVmCPUConstraint.setFrom(vmType);
        StringValue largeVmCPUConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        largeVmCPUConstraintFrom.setValue("LARGE");
        largeVmCPUConstraint.setFromValue(largeVmCPUConstraintFrom);

        largeVmCPUConstraint.setTo(vmCPU);
        FloatValue largeCPUConstraintTo = TypeFactory.eINSTANCE.createFloatValue();
        largeCPUConstraintTo.setValue((float) 3.2);
        largeVmCPUConstraint.setToValue(largeCPUConstraintTo);

        largeVmConstraint.getAttributeConstraints().add(largeVmCPUConstraint);

        AttributeConstraint largeVmMemoryConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        largeVmMemoryConstraint.setFrom(vmType);
        StringValue largeVmMemoryConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        largeVmMemoryConstraintFrom.setValue("LARGE");
        largeVmMemoryConstraint.setFromValue(largeVmMemoryConstraintFrom);

        largeVmMemoryConstraint.setTo(vmMemory);
        IntValue largeMemoryConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        largeMemoryConstraintTo.setValue(8192);
        largeVmMemoryConstraint.setToValue(largeMemoryConstraintTo);

        largeVmConstraint.getAttributeConstraints().add(largeVmMemoryConstraint);

        AttributeConstraint largeVmStorageConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        largeVmStorageConstraint.setFrom(vmType);
        StringValue largeVmStorageConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        largeVmStorageConstraintFrom.setValue("LARGE");
        largeVmStorageConstraint.setFromValue(largeVmStorageConstraintFrom);

        largeVmStorageConstraint.setTo(vmStorage);
        IntValue largeVmStorageConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        largeVmStorageConstraintTo.setValue(2048);
        largeVmStorageConstraint.setToValue(largeVmStorageConstraintTo);

        largeVmConstraint.getAttributeConstraints().add(largeVmStorageConstraint);

        AttributeConstraint largeVmCoresConstraint = ProviderFactory.eINSTANCE.createAttributeConstraint();
        largeVmCoresConstraint.setFrom(vmType);
        StringValue largeVmCoresConstraintFrom = TypeFactory.eINSTANCE.createStringValue();
        largeVmCoresConstraintFrom.setValue("LARGE");
        largeVmCoresConstraint.setFromValue(largeVmCoresConstraintFrom);

        largeVmCoresConstraint.setTo(vmCores);
        IntValue largeVmCoresConstraintTo = TypeFactory.eINSTANCE.createIntValue();
        largeVmCoresConstraintTo.setValue(12);
        largeVmCoresConstraint.setToValue(largeVmCoresConstraintTo);

        largeVmConstraint.getAttributeConstraints().add(largeVmCoresConstraint);

        providerModel.getConstraints().add(largeVmConstraint);

        ////// END definition of Provider model

        return new Quintet<ProviderModel, Feature, Constraint, Constraint, Constraint>(providerModel, vmFeature,
                smallVmConstraint, mediumVmConstraint, largeVmConstraint);

    }

}
