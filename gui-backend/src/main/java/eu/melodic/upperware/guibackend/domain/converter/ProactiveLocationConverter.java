package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.DiscoveryItemState;
import eu.passage.upperware.commons.model.internal.GeoLocation;
import eu.passage.upperware.commons.model.internal.LocationScope;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.Location;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Slf4j
public class ProactiveLocationConverter implements GenericConverter<Location, eu.passage.upperware.commons.model.internal.Location> {

    @Override
    public eu.passage.upperware.commons.model.internal.Location createDomain(@NonNull Location external) {
        return createDomainRecursive(external);
    }


    private eu.passage.upperware.commons.model.internal.Location createDomainRecursive(Location location) {
        if (Objects.isNull(location)) {
            return null;
        }

        eu.passage.upperware.commons.model.internal.Location locationResult = eu.passage.upperware.commons.model.internal.Location.builder()
                .id(location.getId())
                .name(location.getName())
                .providerId(location.getProviderId())
                .locationScope(LocationScope.valueOf(location.getLocationScope().name()))
                .isAssignable(location.isIsAssignable())
                .state(DiscoveryItemState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .build();

        if (location.getGeoLocation() != null) {
            locationResult.setGeoLocation((GeoLocation.builder()
                    .city(location.getGeoLocation().getCity())
                    .country(location.getGeoLocation().getCountry())
                    .latitude(location.getGeoLocation().getLatitude())
                    .longitude(location.getGeoLocation().getLongitude()))
                    .build());
        }
        if (location.getParent() != null) {
            locationResult.setParent(createDomainRecursive(location.getParent()));
        }
        return locationResult;
    }
    
    @Override
    public Location createExternal(@NonNull eu.passage.upperware.commons.model.internal.Location domain) {
        log.warn("ProactiveLocationConverter.createExternal is not implemented yet.");
        return null;
    }
}
