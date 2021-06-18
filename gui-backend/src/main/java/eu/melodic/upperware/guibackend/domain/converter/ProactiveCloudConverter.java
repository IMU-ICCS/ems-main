package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.*;
import org.activeeon.morphemic.model.PACloud;
import org.springframework.lang.NonNull;

public class ProactiveCloudConverter implements GenericConverter<PACloud, Cloud> {
    @Override
    public Cloud createDomain(@NonNull PACloud external) {
        return Cloud.builder()
                .endpoint(external.getEndpoint())
                .cloudType(CloudType.valueOf(external.getCloudType().name()))
                .api(Api.builder().providerName(external.getCloudProviderName()).build())
                .credential(CloudCredential.builder().user(external.getCredentials().getUserName()).secret(external.getCredentials().getPassword()).build())
                .cloudConfiguration(CloudConfiguration.builder().nodeGroup(external.getNodeSourceNamePrefix()).build())
                .id(external.getCloudID())
                .state(CloudState.NOT_AVAILABLE)
                .owner("NOT_AVAILABLE")
                .diagnostics("NOT_AVAILABLE")
                .build();
    }

    @Override
    public PACloud createExternal(@NonNull Cloud domain) {
        return null;
    }
}
