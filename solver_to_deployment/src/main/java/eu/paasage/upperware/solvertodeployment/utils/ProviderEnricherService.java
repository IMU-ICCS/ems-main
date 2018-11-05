package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.CamelModel;
import camel.deployment.SoftwareComponentInstance;
import camel.deployment.VMInstance;
import io.github.cloudiator.rest.model.NodeCandidate;

public interface ProviderEnricherService {

    @Deprecated
    //TODO - to remove in the future
    void enrichVMInstance(VMInstance vmInstance, NodeCandidate nodeCandidate, String constraintProbleId, CamelModel camelModel);

    void enrichSoftwareComponentInstance(SoftwareComponentInstance softwareComponentInstance, NodeCandidate nodeCandidate,
                                         String constraintProbleId, CamelModel camelModel);

}