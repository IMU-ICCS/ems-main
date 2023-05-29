package eu.melodic.upperware.adapter.plangenerator.converter.job;

import camel.deployment.Configuration;
import camel.deployment.ScriptConfiguration;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterLanceInterface;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystem;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystemArchitecture;
import eu.melodic.upperware.adapter.plangenerator.model.AdapterOperatingSystemFamily;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class LanceInterfaceConverter implements InterfaceConverter<ScriptConfiguration, AdapterLanceInterface> {

    private final SecureStoreDBService secureStoreDBService;

    private final AdapterProperties adapterProperties;

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
                .preInstall(fillSecureVariablesFromSecureStore(configuration.getDownloadCommand()))
                .install(fillSecureVariablesFromSecureStore(configuration.getInstallCommand()))
                .postInstall(fillSecureVariablesFromSecureStore(configuration.getConfigureCommand()))
                .start(fillSecureVariablesFromSecureStore(configuration.getStartCommand()))
                .startDetection(fillSecureVariablesFromSecureStore(configuration.getUploadCommand()))
                .stop(fillSecureVariablesFromSecureStore(configuration.getStopCommand()))
                .update(fillSecureVariablesFromSecureStore(configuration.getUpdateCommand()))
                .build();
    }

    private AdapterOperatingSystem createDefaultAdapterOperatingSystem() {
        return AdapterOperatingSystem.builder()
                .operatingSystemFamily(AdapterOperatingSystemFamily.UBUNTU)
                .operatingSystemArchitecture(AdapterOperatingSystemArchitecture.AMD64)
                .operatingSystemVersion(new BigDecimal(1604))
                .build();
    }

    private String fillSecureVariablesFromSecureStore(String text) {
        if (adapterProperties.isFillSecureVariables()) {
            return secureStoreDBService.fillSecureVariablesInText(text);
        } else {
            return text;
        }
    }
}
