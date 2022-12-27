package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.EmsDeploymentTargetProvider;
import eu.passage.upperware.commons.model.internal.EmsDeploymentTargetType;
import eu.passage.upperware.commons.model.internal.OperatingSystemFamily;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.EmsDeploymentRequest;

import java.util.Objects;

@Slf4j
public class ProactiveMonitorConverter implements GenericConverter<EmsDeploymentRequest, eu.passage.upperware.commons.model.internal.EmsDeploymentRequest> {

    @Override
    public eu.passage.upperware.commons.model.internal.EmsDeploymentRequest createDomain(EmsDeploymentRequest external) {
        if(Objects.isNull(external)){
            return null;
        }

        return eu.passage.upperware.commons.model.internal.EmsDeploymentRequest.builder()
                .id(external.getId())
                .authorizationBearer(external.getAuthorizationBearer())
                .baguetteIp(external.getBaguetteIp())
                .baguettePort(external.getBaguette_port())
                .isUsingHttps(external.isUsingHttps())
                .location(external.getTargetNodeCandidate().getLocation().getName())
                .nodeId(external.getNodeId())
                .targetName(external.getTargetName())
                .targetOs(OperatingSystemFamily.valueOf(external.getTargetNodeCandidate().getImage().getOperatingSystem().getOperatingSystemFamily().name()))
                .targetProvider(EmsDeploymentTargetProvider.valueOf(external.getTargetProvider().name()))
                .targetType(EmsDeploymentTargetType.valueOf(external.getTargetType().name()))
                .build();
    }

    @Override
    public EmsDeploymentRequest createExternal(eu.passage.upperware.commons.model.internal.EmsDeploymentRequest domain) {
        log.warn("ProactiveMonitorConverter.createExternal is not implemented yet.");
        return null;
    }
}
