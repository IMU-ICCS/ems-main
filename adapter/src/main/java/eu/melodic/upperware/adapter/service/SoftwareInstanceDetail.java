package eu.melodic.upperware.adapter.service;

import camel.deployment.SoftwareComponent;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class SoftwareInstanceDetail {

    private int cardinality;
    private NodeCandidate nodeCandidate;
    private SoftwareComponent softwareComponent;
}
