package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.ScriptConfiguration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterLanceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LanceInterfaceConverter implements InterfaceConverter<ScriptConfiguration, AdapterLanceInterface> {

    public AdapterLanceInterface convert(ScriptConfiguration configuration) {
        return AdapterLanceInterface
                .builder()
                .containterType("NATIVE") //TODO - do it in better way
                .preInstall(configuration.getDownloadCommand())
                .install(configuration.getInstallCommand())
                .postInstall(configuration.getConfigureCommand())
                .start(configuration.getStartCommand())
                .startDetection(configuration.getUploadCommand())
                .stop(configuration.getStopCommand())
                .build();
    }
}
