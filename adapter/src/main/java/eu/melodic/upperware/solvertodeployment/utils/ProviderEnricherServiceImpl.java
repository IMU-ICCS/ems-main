package eu.melodic.upperware.solvertodeployment.utils;

import camel.core.Attribute;
import camel.core.CoreFactory;
import camel.core.Feature;
import camel.type.StringValue;
import camel.type.TypeFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProviderEnricherServiceImpl implements ProviderEnricherService {

    @Override
    public void enrich(Feature feature, String key, String value) {
        log.info("Start enriching {}: {}", feature.getClass().getSimpleName(), feature.getName());
        feature.getAttributes().add(createAttribute(key, value));
        log.info("Finish enriching {}: {}", feature.getClass().getSimpleName(), feature.getName());
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