package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import com.google.gson.Gson;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.service.CamelEnricherService;
import io.github.cloudiator.rest.model.IdentifierRequirement;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return AdapterRequirement
                .builder()
                .type(IdentifierRequirement.class.getSimpleName())
                .taskName(softwareComponentInstance.getType().getName())
                .nodeName(softwareComponentInstance.getName())
                .nodeCandidate(gson.fromJson(camelEnricherService.fetch("nodeCandidate", softwareComponentInstance), NodeCandidate.class))
                .build();
    }
}
