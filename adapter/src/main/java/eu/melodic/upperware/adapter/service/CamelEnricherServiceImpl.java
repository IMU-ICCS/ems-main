package eu.melodic.upperware.adapter.service;

import camel.core.Attribute;
import camel.core.CoreFactory;
import camel.core.Feature;
import camel.type.StringValue;
import camel.type.TypeFactory;
import camel.type.Value;
import eu.melodic.upperware.adapter.exception.AdapterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@AllArgsConstructor
public class CamelEnricherServiceImpl implements CamelEnricherService {

    @Override
    public void enrich(Feature feature, String key, String value) {
        log.info("Start enriching {}: {}", feature.getClass().getSimpleName(), feature.getName());
        feature.getAttributes().add(createAttribute(key, value));
        log.info("Finish enriching {}: {}", feature.getClass().getSimpleName(), feature.getName());
    }

    @Override
    public <T extends Feature> String fetch(String key, T feature) {
        Attribute attribute = findAttribute(key, feature);

        Value value = attribute.getValue();
        if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        }
        throw new AdapterException(format("Value for attribute %s in %s is not found or is not instance of StringValue", key, feature.getName()));
    }

    private <T extends Feature> Attribute findAttribute(String key, T feature) {
        return feature
                .getAttributes()
                .stream()
                .filter(attr -> attr.getName().equals(key))
                .findFirst()
                .orElseThrow(() -> new AdapterException("Could not find attribute for key " + key + " in " + feature.getName() + " object."));
    }

    private Attribute createAttribute(String attributeName, String attributeValue) {
        log.info("Adding attribute {} with value: {}", attributeName, attributeValue);
        Attribute strAttribute = CoreFactory.eINSTANCE.createAttribute();
        strAttribute.setName(attributeName);
        strAttribute.setValue(createStringValue(attributeValue));
        return strAttribute;
    }

    private StringValue createStringValue(String attributeValue) {
        StringValue stringValue = TypeFactory.eINSTANCE.createStringValue();
        stringValue.setValue(attributeValue);
        return stringValue;
    }

}