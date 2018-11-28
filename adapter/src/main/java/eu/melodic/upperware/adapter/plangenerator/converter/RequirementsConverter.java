package eu.melodic.upperware.adapter.plangenerator.converter;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.SoftwareComponentInstance;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import eu.melodic.upperware.adapter.service.ProviderInfoSupplier;
import io.github.cloudiator.rest.model.IdentifierRequirement;
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

    private ProviderInfoSupplier providerInfoSupplier;

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
                .nodeName(softwareComponentInstance.getName())
                .nodeCandidate(providerInfoSupplier.getNodeCandidate(softwareComponentInstance))
                .build();
    }
}
