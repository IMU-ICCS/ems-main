package eu.paasage.upperware.profiler.generator.communication;

import eu.melodic.cloudiator.client.ApiException;
import eu.melodic.cloudiator.client.model.NodeCandidate;
import eu.melodic.cloudiator.client.model.NodeRequirements;
import eu.paasage.camel.deployment.VM;

import java.util.List;

public interface CloudiatorService {

    List<NodeCandidate> findNodeCandidates(NodeRequirements nodeRequirements) throws ApiException;

    NodeRequirements createNodeRequirements(VM vm);
}
