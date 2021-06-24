package eu.melodic.upperware.guibackend.domain.converter;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericConverter<E, D> {

    D createDomain(@NonNull E external);

    E createExternal(@NonNull D domain);

    default List<D> createDomains(@NonNull final Collection<E> externals) {
        return externals.stream()
                .map(this::createDomain)
                .collect(Collectors.toList());
    }

    default List<E> createExternals(@NonNull final Collection<D> domains) {
        return domains.stream()
                .map(this::createExternal)
                .collect(Collectors.toList());
    }
}
