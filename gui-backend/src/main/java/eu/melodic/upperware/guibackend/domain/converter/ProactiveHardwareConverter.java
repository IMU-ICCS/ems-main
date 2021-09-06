package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.DiscoveryItemState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.Hardware;
import org.springframework.lang.NonNull;

@Slf4j
@AllArgsConstructor
public class ProactiveHardwareConverter implements GenericConverter<Hardware, eu.passage.upperware.commons.model.internal.Hardware> {

    private ProactiveLocationConverter proactiveLocationConverter;

    @Override
    public eu.passage.upperware.commons.model.internal.Hardware createDomain(@NonNull Hardware external) {
        return eu.passage.upperware.commons.model.internal.Hardware.builder()
                .id(external.getId())
                .name(external.getName())
                .providerId(external.getProviderId())
                .cores(external.getCores())
                .ram(external.getRam())
                .disk(external.getDisk())
                .location(proactiveLocationConverter.createDomain(external.getLocation()))
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();
    }

    @Override
    public Hardware createExternal(@NonNull eu.passage.upperware.commons.model.internal.Hardware domain) {
        log.warn("ProactiveHardwareConverter.createExternal is not implemented yet.");
        return null;
    }
}
