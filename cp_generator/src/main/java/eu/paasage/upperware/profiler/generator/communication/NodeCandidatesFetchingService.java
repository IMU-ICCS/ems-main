package eu.paasage.upperware.profiler.generator.communication;

import camel.deployment.RequirementSet;
import camel.location.LocationModel;
import org.ow2.proactive.sal.model.NodeCandidate;
import org.ow2.proactive.sal.model.Requirement;

import java.util.List;

public interface NodeCandidatesFetchingService {

    List<NodeCandidate> findNodeCandidates(List<Requirement> requirements);

    List<Requirement> createRequirements(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels, String resourceName);

}
