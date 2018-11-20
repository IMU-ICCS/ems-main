package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.ServerlessConfiguration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterDockerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class DockerInterfaceConverter implements InterfaceConverter<ServerlessConfiguration, AdapterDockerInterface> {

    @Override
    public AdapterDockerInterface convert(ServerlessConfiguration configuration) {
        return AdapterDockerInterface
                .builder()
                .dockerImage(findDockerImage(configuration))
                .environment(findEnvironment(configuration))
                .build();
    }

    // todo with annotation for Docker
    private String findDockerImage(ServerlessConfiguration configuration) {
        return configuration.getBinaryCodeURL();
    }


    //todo
    private Map<String, String> findEnvironment(ServerlessConfiguration configuration) {
        return Collections.emptyMap();
    }


}
