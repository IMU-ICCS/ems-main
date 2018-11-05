package eu.melodic.upperware.adapter.plangenerator.model;

import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdapterRequirement implements Data {

    private String type;
    private String nodeName;

    private NodeCandidate nodeCandidate;

    @Override
    public String getName() {
        return "AdapterRequirement_" + nodeName;
    }
}
