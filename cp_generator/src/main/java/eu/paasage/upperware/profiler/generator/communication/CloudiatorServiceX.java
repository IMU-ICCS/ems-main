package eu.paasage.upperware.profiler.generator.communication;

import camel.deployment.RequirementSet;
import camel.location.LocationModel;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;

import java.util.List;

public interface CloudiatorServiceX {

    List<NodeCandidate> findNodeCandidates(List<Requirement> requirements);

    List<Requirement> createRequirements(RequirementSet globalRequirementSet, RequirementSet localRequirementSet, List<LocationModel> locationModels);

}
