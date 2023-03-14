package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import com.google.gson.Gson;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.service.CamelEnricherService;
import io.github.cloudiator.rest.model.IdentifierRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.NodeCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RequirementsConverter implements ModelConverter<DeploymentInstanceModel, Collection<AdapterRequirement>> {

    private CamelEnricherService camelEnricherService;
    private Gson gson;

    @Override
    public Collection<AdapterRequirement> toComparableModel(DeploymentInstanceModel model) {
        return model
                .getSoftwareComponentInstances()
                .stream()
                .map(this::createAdapterRequirement)
                .collect(toSet());
    }

    private AdapterRequirement createAdapterRequirement(SoftwareComponentInstance softwareComponentInstance) {
        final NodeCandidate nodeCandidate = gson.fromJson(camelEnricherService.fetch("nodeCandidate", softwareComponentInstance), NodeCandidate.class);
        clearNodeCandidate(nodeCandidate);

        return AdapterRequirement
                .builder()
                .type(IdentifierRequirement.class.getSimpleName())
                .taskName(softwareComponentInstance.getType().getName())
                .nodeName(softwareComponentInstance.getName())
                .nodeCandidate(nodeCandidate)
                .build();
    }

    private void clearNodeCandidate(NodeCandidate nodeCandidate) {
        //clear NodeCandidate from unnecessary Cloud in case of BYON
        if (nodeCandidate != null && NodeCandidate.NodeCandidateTypeEnum.BYON.equals(nodeCandidate.getNodeCandidateType()) ||
                (nodeCandidate != null && NodeCandidate.NodeCandidateTypeEnum.EDGE.equals(nodeCandidate.getNodeCandidateType()))) {
            nodeCandidate.setCloud(null);
        }
    }
}
