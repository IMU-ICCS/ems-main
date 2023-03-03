package eu.melodic.upperware.guibackend;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientService;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUIImpl;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceImpl;
import eu.melodic.upperware.guibackend.domain.converter.*;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.service.provider.ProviderIdCreatorService;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.provider.ProviderValidationService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

    @Bean
    public RestTemplate getRestTemplate() {

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(180000);
        simpleClientHttpRequestFactory.setReadTimeout(120000);

        return new RestTemplate(simpleClientHttpRequestFactory);
    }


    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }

    @Bean
    public ProviderIdCreatorService providerIdCreatorService() {
        return new ProviderIdCreatorService();
    }

    @Bean
    ProviderValidationService providerValidationService() {
        return new ProviderValidationService();
    }

    @Bean
    public YamlDataService yamlDataService() {
        return new YamlDataService();
    }

    @Bean
    public SecureStoreDBService secureStoreDBService(final GuiBackendProperties guiBackendProperties) {
        return new SecureStoreDBService(
                guiBackendProperties.getSecureStore().getDbUrl(),
                guiBackendProperties.getSecureStore().getDbUsername(),
                guiBackendProperties.getSecureStore().getDbPassword(),
                guiBackendProperties.getSecureStore().getPw()
        );
    }

    @Bean
    public ProviderService providerService(
        ProviderIdCreatorService providerIdCreatorService,
        ProviderValidationService providerValidationService,
        YamlDataService yamlDataService,
        SecureStoreDBService secureStoreDBService
    ) {
        return new ProviderService(
            providerIdCreatorService,
            providerValidationService,
            yamlDataService,
            secureStoreDBService
        );
    }

    @Bean
    public ProactiveClientServiceGUI proactiveClientServiceForGUI(ProactiveClientConnectorService proactiveClientConnectorService) {
        return new ProactiveClientServiceGUIImpl(proactiveClientConnectorService);
    }

    @Bean
    public ProactiveClientService proactiveClientService(ProactiveClientConnectorService proactiveClientConnectorService) {
        return new ProactiveClientServiceImpl(proactiveClientConnectorService);
    }

    @Bean(name = "locationConverter")
    public GenericConverter<?, ?> getLocationConverter() {
        return new ProactiveLocationConverter();
    }

    @Bean(name = "hardwareConverter")
    public GenericConverter<?, ?> getHardwareConverter(@Qualifier("locationConverter") GenericConverter<?, ?> locationConverter) {
        return new ProactiveHardwareConverter((ProactiveLocationConverter) locationConverter);
    }

    @Bean(name = "imageConverter")
    public GenericConverter<?, ?> getImageConverter(@Qualifier("locationConverter") GenericConverter<?, ?> locationConverter) {
        return new ProactiveImageConverter((ProactiveLocationConverter) locationConverter);
    }

    @Bean(name = "cloudConverter")
    public GenericConverter<?, ?> getCloudConverter() {
        return new ProactiveCloudConverter();
    }

    @Bean(name = "nodeConverter")
    public GenericConverter<?, ?> getNodeConverter() {
        return new ProactiveNodeConverter();
    }

    @Bean(name = "monitorConverter")
    public GenericConverter<?, ?> getMonitorConverter() {
        return new ProactiveMonitorConverter();
    }

    @Bean(name = "jobConverter")
    public GenericConverter<?, ?> getJobConverter(@Qualifier("monitorConverter") GenericConverter<?, ?> monitorConverter) {
        return new ProactiveJobConverter((ProactiveMonitorConverter) monitorConverter);
    }
}
