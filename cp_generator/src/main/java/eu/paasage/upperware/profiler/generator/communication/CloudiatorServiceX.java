package eu.paasage.upperware.profiler.generator.communication;

import camel.deployment.RequirementSet;
import camel.location.LocationModel;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.NodeRequirements;

import java.util.List;

public interface CloudiatorServiceX {

    List<NodeCandidate> findNodeCandidates(NodeRequirements nodeRequirements) throws ApiException;

    NodeRequirements createNodeRequirements(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels, String imageId);
}
