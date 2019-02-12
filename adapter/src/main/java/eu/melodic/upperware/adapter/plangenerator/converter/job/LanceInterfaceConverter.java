package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import camel.deployment.ScriptConfiguration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterLanceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LanceInterfaceConverter implements InterfaceConverter<ScriptConfiguration, AdapterLanceInterface> {

    @Override
    public boolean isInstance(Configuration configuration) {
        if (configuration instanceof ScriptConfiguration) {
            ScriptConfiguration scriptConfiguration = (ScriptConfiguration) configuration;
            return !DOCKER_TAG.equals(scriptConfiguration.getDevopsTool());
        }
        return false;
    }

    public AdapterLanceInterface convert(ScriptConfiguration configuration) {
        return AdapterLanceInterface
                .builder()
                .containterType("NATIVE")
                .preInstall(configuration.getDownloadCommand())
                .install(configuration.getInstallCommand())
                .postInstall(configuration.getConfigureCommand())
                .start(configuration.getStartCommand())
                .startDetection(configuration.getUploadCommand())
                .stop(configuration.getStopCommand())
                .build();
    }
}
