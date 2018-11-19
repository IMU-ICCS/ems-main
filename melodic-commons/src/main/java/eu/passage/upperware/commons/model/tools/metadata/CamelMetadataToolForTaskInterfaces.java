package eu.passage.upperware.commons.model.tools.metadata;

import camel.core.Attribute;
import camel.core.Feature;
import camel.type.StringValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class CamelMetadataToolForTaskInterfaces {

    private static final String SPLIT_ATTRIBUTE_PATTERN = "\\s+";
    private static final String ATTRIBUTE_PREFIX = "--";

    public static Optional<Attribute> findAttributeByAnnotation(EList<Attribute> attributes, String annotation) {
        return attributes.stream()
                .filter(attribute -> attribute.getAnnotations()
                        .stream()
                        .anyMatch(mmsObject -> mmsObject.getId().equals(annotation)))
                .findFirst();
    }

    public static Optional<Feature> findFeatureByAnnotation(EList<Feature> features, String annotation) {
        return features.stream()
                .filter(featureTmp -> featureTmp.getAnnotations().stream()
                        .anyMatch(mmsObject -> mmsObject.getId().equals(annotation)))
                .findFirst();
    }

    public static Map<String, String> createStringAttributesMapForFeature(Feature feature) {
        return feature.getAttributes().stream()
                .map(attribute -> parseAttributeToPair(((StringValue) attribute.getValue()).getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    // e.g. --executor-memory 6G => key:executor-memory, value:6G
    private static Pair<String, String> parseAttributeToPair(String attribute) {
        String[] split = attribute.trim().split(SPLIT_ATTRIBUTE_PATTERN);
        String key = split[0].trim().replace(ATTRIBUTE_PREFIX, ""); //cut prefix
        return Pair.of(key, split[1].trim());
    }
}