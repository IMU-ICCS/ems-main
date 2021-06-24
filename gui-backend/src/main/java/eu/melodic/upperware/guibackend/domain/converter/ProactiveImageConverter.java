package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.*;
import org.activeeon.morphemic.model.Image;

import java.util.Objects;

public class ProactiveImageConverter implements GenericConverter<Image, eu.passage.upperware.commons.model.internal.Image> {
    @Override
    public eu.passage.upperware.commons.model.internal.Image createDomain(Image external) {
        return eu.passage.upperware.commons.model.internal.Image.builder()
                .id(external.getId())
                .name(external.getName())
                .providerId(external.getProviderId())
                .operatingSystem(OperatingSystem.builder()
                        .operatingSystemArchitecture(OperatingSystemArchitecture.valueOf(external.getOperatingSystem().getOperatingSystemArchitecture().name()))
                        .operatingSystemFamily(OperatingSystemFamily.valueOf(external.getOperatingSystem().getOperatingSystemFamily().name()))
                        .operatingSystemVersion(external.getOperatingSystem().getOperatingSystemVersion())
                        .build())
                .location(convertToLocation(external.getLocation()))
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();
    }

    //TODO: potencjalnie do przeniesienia do konwertera Location
    private Location convertToLocation(org.activeeon.morphemic.model.Location location) {
        if(Objects.isNull(location)) {
            return null;
        }

        return Location.builder()
                .id(location.getId())
                .name(location.getName())
                .providerId(location.getProviderId())
                .locationScope(LocationScope.valueOf(location.getLocationScope().name()))
                .isAssignable(location.isIsAssignable())
                .geoLocation(GeoLocation.builder()
                        .city(location.getGeoLocation().getCity())
                        .country(location.getGeoLocation().getCountry())
                        .latitude(location.getGeoLocation().getLatitude())
                        .longitude(location.getGeoLocation().getLongitude())
                        .build())
                .parent(convertToLocation(location.getParent()))
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();
    }

    @Override
    public Image createExternal(eu.passage.upperware.commons.model.internal.Image domain) {
        return null;
    }
}
