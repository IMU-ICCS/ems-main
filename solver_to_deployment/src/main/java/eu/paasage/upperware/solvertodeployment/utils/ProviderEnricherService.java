package eu.paasage.upperware.solvertodeployment.utils;

import camel.core.CamelModel;
import camel.deployment.SoftwareComponentInstance;
import io.github.cloudiator.rest.model.NodeCandidate;

public interface ProviderEnricherService {

    void enrichSoftwareComponentInstance(SoftwareComponentInstance softwareComponentInstance, NodeCandidate nodeCandidate,
                                         String constraintProbleId, CamelModel camelModel);

}