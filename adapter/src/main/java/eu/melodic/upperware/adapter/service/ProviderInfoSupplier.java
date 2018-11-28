package eu.melodic.upperware.adapter.service;

import camel.core.Attribute;
import camel.core.Feature;
import camel.type.StringValue;
import camel.type.Value;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.NonNull;

public interface ProviderInfoSupplier {

    String getCloudName(Feature feature);
    String getProviderName(Feature feature);
    String getLocation(Feature feature);
    String getImage(Feature feature);
    String getMachineType(Feature feature);
    String getDriver(Feature feature);
    String getName(Feature feature);
    String getApiName(Feature feature);
    String getCredentialsName(Feature feature);
    String getPropertyName(Feature feature);
    String getEndpoint(Feature feature);
    NodeCandidate getNodeCandidate(Feature feature);

    default String getAttribute(@NonNull String name, Feature feature) {
        Attribute attribute =
                feature
                .getAttributes()
                .stream()
                .filter(attr -> attr.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find attribute for name " + name + " in " + feature.getName() + " object."));

        Value value = attribute.getValue();
        if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        }
        throw new RuntimeException("Value for attribute " + name + " in " + feature.getName() + " not found or not instance of StringValue");
    }
}
