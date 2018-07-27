package eu.paasage.upperware.profiler.generator.communication;

import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.NodeRequirements;

import java.util.List;

public interface CloudiatorService {

    List<NodeCandidate> findNodeCandidates(NodeRequirements nodeRequirements) throws ApiException;

    NodeRequirements createNodeRequirements();
}
