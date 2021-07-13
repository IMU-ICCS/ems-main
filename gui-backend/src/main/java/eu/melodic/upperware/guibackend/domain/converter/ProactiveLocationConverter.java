package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.DiscoveryItemState;
import eu.passage.upperware.commons.model.internal.GeoLocation;
import eu.passage.upperware.commons.model.internal.LocationScope;
import org.activeeon.morphemic.model.Location;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class ProactiveLocationConverter implements GenericConverter<Location, eu.passage.upperware.commons.model.internal.Location> {
    @Override
    public eu.passage.upperware.commons.model.internal.Location createDomain(@NonNull Location external) {
        return createDomainRecursive(external);
    }

    private eu.passage.upperware.commons.model.internal.Location createDomainRecursive(Location location) {
        if(Objects.isNull(location)) {
            return null;
        }

        return eu.passage.upperware.commons.model.internal.Location.builder()
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
                .parent(createDomainRecursive(location.getParent()))
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();
    }
    
    @Override
    public Location createExternal(@NonNull eu.passage.upperware.commons.model.internal.Location domain) {
        return null;
    }
}
