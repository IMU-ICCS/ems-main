package eu.paasage.upperware.profiler.generator.communication.impl;

import eu.paasage.upperware.profiler.generator.communication.CloudiatorService;
import eu.paasage.upperware.profiler.generator.properties.GeneratorProperties;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.MatchmakingApi;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.NodeRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CloudiatorServiceImpl implements CloudiatorService {

    private static final String HARDWARE_CLASS = "hardware";
    private static final String IMAGE_CLASS = "image";
    private static final String LOCATION_CLASS = "location";

    private MatchmakingApi matchmakingApi;

    public CloudiatorServiceImpl(GeneratorProperties generatorProperties) {
        ApiClient apiClient = new ApiClient();
        apiClient.getHttpClient().setReadTimeout(generatorProperties.getCloudiatorV2().getHttpReadTimeout(), TimeUnit.SECONDS);
        apiClient.setBasePath(generatorProperties.getCloudiatorV2().getUrl());
        apiClient.setApiKey(generatorProperties.getCloudiatorV2().getApiKey());
        this.matchmakingApi = new MatchmakingApi(apiClient);
    }

    @Override
    public List<NodeCandidate> findNodeCandidates(NodeRequirements nodeRequirements) throws ApiException {
        return matchmakingApi.findNodeCandidates(nodeRequirements);
    }

    @Override
    public NodeRequirements createNodeRequirements(){
        return null;
    }


}
