package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.ServerlessConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class JobDockerConverter {

    // todo with annotation for Docker
    public String findDockerImage(ServerlessConfiguration configuration) {
        return configuration.getBinaryCodeURL();
    }


    //todo
    public Map<String, String> findEnvironment(ServerlessConfiguration configuration) {
        return Collections.emptyMap();
    }
}
