package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.provider.*;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.ValueType;
import eu.paasage.camel.type.impl.EnumerationImpl;
import eu.paasage.camel.type.impl.StringsValueImpl;
import eu.paasage.upperware.profiler.generator.service.camel.model.Flavour;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CamelModelService {

    private static final String FEATURE_NAME_VM = "VM";
    private static final String ATTRIBUTE_NAME_VM_TYPE = "VMType";

    public Map<String, List<AttributeConstraint>> getAttributes(ProviderModel providerModel) {
        List<String> vmTypeNames = getVMProfileInstanceNames(providerModel);

        Map<String, List<AttributeConstraint>> result = new HashMap<>();
        for (String vmTypeName : vmTypeNames) {
            //TODO - moze to wydzielic aby zwracalo juz jedna i ostateczna instancje??
            for (Constraint constraint : providerModel.getConstraints()) {
                List<AttributeConstraint> attributeFromModel = getAttributeFromModel(vmTypeName, constraint.getAttributeConstraints());
                if (CollectionUtils.isNotEmpty(attributeFromModel)) {
                    result.put(vmTypeName, attributeFromModel);
                    break;
                }
            }
        }
        return result;
    }

    private List<String> getVMProfileInstanceNames(ProviderModel providerModel) {
        List<String> result = new ArrayList<>();
        Feature vmFeature = ProviderModelTool.getFeatureByName(providerModel.getRootFeature(), FEATURE_NAME_VM);
        if (vmFeature != null) {
            Attribute vmTypeAttribute = ProviderModelTool.getAttributeByName(vmFeature, ATTRIBUTE_NAME_VM_TYPE);
            if (vmTypeAttribute != null) {
                ValueType valueType = vmTypeAttribute.getValueType();
                if (valueType instanceof EnumerationImpl) {
                    for (EnumerateValue value : ((EnumerationImpl) valueType).getValues()) {
                        result.add(value.getName());
                    }
                }
            } else {
                log.warn("Could not find attribute " + ATTRIBUTE_NAME_VM_TYPE + ". No candidates will be created for provider " + providerModel.getName());
            }
        } else {
            log.warn("Could not find feature " + FEATURE_NAME_VM + ". No candidates will be created for provider " + providerModel.getName());
        }
        return result;
    }

    private List<AttributeConstraint> getAttributeFromModel(String name, EList<AttributeConstraint> attributeConstraints) {
        List<AttributeConstraint> result = new ArrayList<>();
        for (AttributeConstraint attributeConstraint : attributeConstraints) {
            SingleValue fromValue = attributeConstraint.getFromValue();
            if (fromValue instanceof StringsValueImpl) {
                if (name.equals(((StringsValueImpl) fromValue).getValue())) {
                    result.add(attributeConstraint);
                }
            }
        }
        return result;
    }

    public List<Flavour> convertToFlavours(Collection<ProviderModel> providerModels) {
        return providerModels.stream()
                .map(this::convertToFlavours)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Flavour> convertToFlavours(ProviderModel providerModel) {
        Map<String, List<AttributeConstraint>> attributes = getAttributes(providerModel);

        attributes.forEach((key, value) -> log.info("attribute: " + key + " size: " + value.size()));

        return attributes.entrySet().stream()
                .map(entry -> Flavour.createFlavour(providerModel, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
