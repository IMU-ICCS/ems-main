package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.CamelModel;
import camel.deployment.VMInstance;
import io.github.cloudiator.rest.model.NodeCandidate;

public interface ProviderEnricherService {

    void enrichVMInstance(VMInstance vmInstance, NodeCandidate nodeCandidate, String constraintProbleId, CamelModel camelModel);

}