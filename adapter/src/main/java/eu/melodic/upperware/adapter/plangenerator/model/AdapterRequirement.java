package eu.melodic.upperware.adapter.plangenerator.model;

import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.Builder;
import lombok.Getter;

import java.util.function.BiPredicate;

@Getter
@Builder
public class AdapterRequirement implements Data {

    public static final BiPredicate<AdapterRequirement, AdapterRequirement> NODE_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getNodeName().equals(oldReq.getNodeName());

    private String taskName;
    private String type;
    private String nodeName;

    private NodeCandidate nodeCandidate;

    @Override
    public String getName() {
        return "AdapterRequirement_" + nodeName;
    }
}
