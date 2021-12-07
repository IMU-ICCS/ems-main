package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.*;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.PACloud;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ProactiveCloudConverter implements GenericConverter<PACloud, Cloud> {
    @Override
    public Cloud createDomain(@NonNull PACloud external) {
        Map<String, String> props = new HashMap<>();
        props.put("CloudID", external.getCloudID());

        return Cloud.builder()
                .endpoint(external.getEndpoint())
                .cloudType(CloudType.valueOf(external.getCloudType().name()))
                .api(Api.builder().providerName(external.getCloudProviderName()).build())
                .credential(CloudCredential.builder()
                        .user(external.getCredentials().getUserName())
                        .secret(external.getCredentials().getPassword())
                        .domain(external.getCredentials().getDomain())
                        .build())
                .cloudConfiguration(CloudConfiguration.builder()
                        .nodeGroup(external.getNodeSourceNamePrefix())
                        .properties(props).identityVersion(external.getIdentityVersion())
                        .scopePrefix(external.getScopePrefix())
                        .scopeValue(external.getScopeValue())
                        .build())
                .id(external.getCloudProviderName())
                .state(CloudState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .diagnostics("NOT_AVAILABLE")
                .build();
    }

    @Override
    public PACloud createExternal(@NonNull Cloud domain) {
        log.warn("ProactiveCloudConverter.createExternal is not implemented yet.");
        return null;
    }
}
