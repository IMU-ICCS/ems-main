package eu.functionizer.functionizertestingtool.model;

import io.github.cloudiator.rest.model.Function;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class CloudiatorData {
    private Map<String, Function> deployedFunctions;
    private Map<String, String> functionDeployNames;
    private Map<String, NodeCandidate> nodeCandidates;
    private Map<String, String> userSecrets;

}
