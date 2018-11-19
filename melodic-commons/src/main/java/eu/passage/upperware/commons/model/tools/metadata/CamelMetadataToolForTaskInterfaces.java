package eu.passage.upperware.commons.model.tools.metadata;

import camel.core.Attribute;
import camel.core.Feature;
import camel.type.StringValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.emf.common.util.EList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CamelMetadataToolForTaskInterfaces {

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

    public static Map<String, String> createStringAttributesMapForFeature(Feature feature) {
        Map<String, String> result = new HashMap<>();
        feature.getAttributes().forEach(attribute -> {
            ImmutablePair<String, String> pairAttribute = parseAttributeToPair(((StringValue) attribute.getValue()).getValue());
            result.put(pairAttribute.getKey(), pairAttribute.getValue());
        });
        return result;
    }

    // e.g. --executor-memory 6G => key:executor-memory, value:6G
    private static ImmutablePair<String, String> parseAttributeToPair(String attribute) {
        String splitAttributePattern = "\\s+";
        String attributePrefix = "--";
        String[] split = attribute.trim().split(splitAttributePattern);
        String key = split[0].trim().replace(attributePrefix, ""); //cut prefix
        return new ImmutablePair<>(key, split[1].trim());
    }
}