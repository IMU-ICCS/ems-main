package eu.melodic.upperware.adapter.service;

import camel.core.Feature;
import com.google.gson.Gson;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProviderInfoSupplierImpl implements ProviderInfoSupplier {

    private Gson gson = new Gson();

    @Override
    public String getCloudName(Feature feature) {
        return getAttribute("cloudName", feature);
    }

    @Override
    public String getProviderName(Feature feature) {
        return getAttribute("providerName", feature);
    }

    @Override
    public String getLocation(Feature feature) {
        return getAttribute("location", feature);
    }

    @Override
    public String getImage(Feature feature) {
        return getAttribute("image", feature);
    }

    @Override
    public String getMachineType(Feature feature) {
        return getAttribute("machineType", feature);
    }

    @Override
    public String getDriver(Feature feature) {
        return getAttribute("driver", feature);
    }

    @Override
    public String getName(Feature feature) {
        return getAttribute("name", feature);
    }

    @Override
    public String getApiName(Feature feature) {
        return getAttribute("apiName", feature);
    }

    @Override
    public String getCredentialsName(Feature feature) {
        return getAttribute("credentialsName", feature);
    }

    @Override
    public String getPropertyName(Feature feature) {
        return getAttribute("propertyName", feature);
    }

    @Override
    public String getEndpoint(Feature feature) {
        return getAttribute("endpoint", feature);
    }

    @Override
    public NodeCandidate getNodeCandidate(Feature feature) {
        String nodeCandidateStr = getAttribute("nodeCandidate", feature);
        return gson.fromJson(nodeCandidateStr, NodeCandidate.class);
    }
}
