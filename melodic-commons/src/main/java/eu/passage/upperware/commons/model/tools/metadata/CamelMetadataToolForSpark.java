package eu.passage.upperware.commons.model.tools.metadata;

import camel.core.Attribute;
import camel.core.Feature;
import camel.type.StringValue;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;

import java.util.*;

@Slf4j
public class CamelMetadataToolForSpark {

    //todo spytać Marty czy to co jest dla adnotacji w camelu to jej id, czy name? W CamelMetaDataTool porównują po id
    public static Optional<Attribute> findAttributeByAnnotation(EList<Attribute> attributes, String annotation) {
        return attributes.stream()
                .filter(attribute -> attribute.getAnnotations()
                        .stream()
                        .anyMatch(mmsObject -> mmsObject.getId().equals(annotation)))
                .findFirst();
    }

    public static Optional<Attribute> findAttributeByName(EList<Attribute> attributes, String attributeName) {
        return attributes.stream()
                .filter(attribute -> attribute.getName().equals(attributeName))
                .findFirst();
    }

    public static Optional<Feature> findFeatureByAnnotation(EList<Feature> features, String annotation) {
        return features.stream()
                .filter(featureTmp -> featureTmp.getAnnotations().stream()
                        .anyMatch(mmsObject -> mmsObject.getId().equals(annotation)))
                .findFirst();
    }

    //todo spytać Marty - czy klucz to nazwa z camela i wartość to wartość?
    public static Map<String, String> createStringAttributesMapForFeature(Feature feature) {
        Map<String, String> result = new HashMap<>();
        feature.getAttributes().forEach(attribute ->
                result.put(attribute.getName(), ((StringValue) attribute.getValue()).getValue()));
        return result;
    }

    //to tests
    public static List<String> parseApplicationArguments(String arguments) {
        List<String> result = new ArrayList<>();
        String argumentSign = "--";
        String[] splitArgs = arguments.split(argumentSign);
        for (String arg : splitArgs) {
            result.add(argumentSign + arg.trim());
        }

        return result;
    }
}