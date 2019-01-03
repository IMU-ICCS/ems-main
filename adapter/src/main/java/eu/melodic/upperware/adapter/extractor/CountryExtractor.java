package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.*;
import io.github.cloudiator.rest.model.GeoLocation;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CountryExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Set<String>> {
    @Override
    public String getKey() {
        return "all-vm-countries";
    }

    @Override
    public Set<String> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .map(value -> {
					Location vmLoc = value.getHardware().getLocation();
					if (vmLoc==null) vmLoc = value.getLocation();
					if (vmLoc==null) return null;
					GeoLocation geoLoc = vmLoc.getGeoLocation();
					if (geoLoc==null) return null;
					return geoLoc.getCountry();
				})
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<String>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
