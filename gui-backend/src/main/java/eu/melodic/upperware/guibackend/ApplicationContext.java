package eu.melodic.upperware.guibackend;

import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUIImpl;
import eu.melodic.upperware.guibackend.domain.converter.GenericConverter;
import eu.melodic.upperware.guibackend.domain.converter.ProactiveCloudConverter;
import eu.melodic.upperware.guibackend.domain.converter.ProactiveImageConverter;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.model.internal.Cloud;
import eu.passage.upperware.commons.service.provider.ProviderIdCreatorService;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.provider.ProviderValidationService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import org.activeeon.morphemic.model.PACloud;
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
    public ProactiveClientServiceGUI proactiveClientServiceForGUI(final GuiBackendProperties guiBackendProperties) {
        return new ProactiveClientServiceGUIImpl(guiBackendProperties.getPaConfig().getRestUrl(),
                guiBackendProperties.getPaConfig().getLogin(),
                guiBackendProperties.getPaConfig().getPassword(),
                guiBackendProperties.getPaConfig().getEncryptorPw()) {
        };
    }

    @Bean
    @Qualifier("cloudConverter")
    public GenericConverter<?, ?> getCloudConverter() {
        return new ProactiveCloudConverter();
    }

    @Bean
    @Qualifier("imageConverter")
    public GenericConverter<?, ?> getImageConverter() {
        return new ProactiveImageConverter();
    }
}
