package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.DiscoveryItemState;
import eu.passage.upperware.commons.model.internal.OperatingSystem;
import eu.passage.upperware.commons.model.internal.OperatingSystemArchitecture;
import eu.passage.upperware.commons.model.internal.OperatingSystemFamily;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.ow2.proactive.sal.model.Image;
import org.springframework.lang.NonNull;

@Slf4j
@AllArgsConstructor
public class ProactiveImageConverter implements GenericConverter<Image, eu.passage.upperware.commons.model.internal.Image> {

    private ProactiveLocationConverter proactiveLocationConverter;

    @Override
    public eu.passage.upperware.commons.model.internal.Image createDomain(@NonNull Image external) {
        return eu.passage.upperware.commons.model.internal.Image.builder()
                .id(external.getId())
                .name(external.getName())
                .providerId(external.getProviderId())
                .operatingSystem(OperatingSystem.builder()
                        .operatingSystemArchitecture(ObjectUtils.defaultIfNull(OperatingSystemArchitecture
                                .valueOf(external.getOperatingSystem().getOperatingSystemArchitecture().name()), OperatingSystemArchitecture.UNKNOWN))
                        .operatingSystemFamily(ObjectUtils.defaultIfNull(OperatingSystemFamily
                                .valueOf(external.getOperatingSystem().getOperatingSystemFamily().name()),OperatingSystemFamily.UNKNOWN_OS_FAMILY))
                        .operatingSystemVersion(external.getOperatingSystem().getOperatingSystemVersion())
                        .build())
                .location(proactiveLocationConverter.createDomain(external.getLocation()))
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();
    }


    @Override
    public Image createExternal(@NonNull eu.passage.upperware.commons.model.internal.Image domain) {
        log.warn("ProactiveImageConverter.createExternal is not implemented yet.");
        return null;
    }

}
