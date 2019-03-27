package eu.melodic.upperware.adapter.extractor;

import camel.core.Attribute;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import camel.type.StringValue;
import camel.type.Value;
import com.google.gson.Gson;
import io.github.cloudiator.rest.model.NodeCandidate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

class NodeCandidateSupport {

    private static final String NODE_CANDIDATE_PROPERTY_NAME = "nodeCandidate";

    private Gson gson = new Gson();

    Map<String, NodeCandidate> getNodeCandidateForDeployment(DeploymentInstanceModel deploymentInstanceModel){

        Map<String, NodeCandidate> result = new HashMap<>();
        for (SoftwareComponentInstance softwareComponentInstance : deploymentInstanceModel.getSoftwareComponentInstances()) {
            Attribute nodeCandidateAttribute = getNodeCandidateAttribute(softwareComponentInstance)
                    .orElseThrow(() -> new IllegalStateException(format("Could not find attribute %s for softwareComponentInstance: %s", NODE_CANDIDATE_PROPERTY_NAME, softwareComponentInstance.getName())));

            NodeCandidate nodeCandidate = gson.fromJson(getStringValue(nodeCandidateAttribute), NodeCandidate.class);
            result.put(softwareComponentInstance.getName(), nodeCandidate);
        }
        return result;
    }

    private Optional<Attribute> getNodeCandidateAttribute(SoftwareComponentInstance softwareComponentInstance) {
        return softwareComponentInstance.getAttributes().stream().filter(attribute -> NODE_CANDIDATE_PROPERTY_NAME.equals(attribute.getName())).findFirst();
    }

    private String getStringValue(Attribute attribute){
        Value value = attribute.getValue();
        if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        }
        if (value == null) {
            throw new IllegalStateException(format("Value for %s attribute is null", NODE_CANDIDATE_PROPERTY_NAME));
        }
        throw new IllegalStateException(format("Value for %s attribute is in type %s", NODE_CANDIDATE_PROPERTY_NAME, value.getClass().getCanonicalName()));
    }

}
