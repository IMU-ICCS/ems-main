package eu.melodic.upperware.adapter.service;

import camel.deployment.SoftwareComponent;
import lombok.Builder;
import lombok.Getter;
import org.ow2.proactive.sal.model.NodeCandidate;

@Getter
@Builder
class SoftwareInstanceDetail {

    private int cardinality;
    private NodeCandidate nodeCandidate;
    private SoftwareComponent softwareComponent;
}
