package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import camel.deployment.ScriptConfiguration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterLanceInterface;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystem;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystemArchitecture;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystemFamily;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
                .operatingSystem(createDefaultAdapterOperatingSystem())
                .preInstall(configuration.getDownloadCommand())
                .install(configuration.getInstallCommand())
                .postInstall(configuration.getConfigureCommand())
                .start(configuration.getStartCommand())
                .startDetection(configuration.getUploadCommand())
                .stop(configuration.getStopCommand())
                .update(configuration.getUpdateCommand())
                .build();
    }

    private AdapterOperatingSystem createDefaultAdapterOperatingSystem() {
        return AdapterOperatingSystem.builder()
                .operatingSystemFamily(AdapterOperatingSystemFamily.UBUNTU)
                .operatingSystemArchitecture(AdapterOperatingSystemArchitecture.AMD64)
                .operatingSystemVersion(new BigDecimal(1604))
                .build();
    }
}
