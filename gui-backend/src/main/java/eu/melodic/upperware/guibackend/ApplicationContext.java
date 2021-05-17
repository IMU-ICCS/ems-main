package eu.melodic.upperware.guibackend;

import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceForGUI;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceForGUIImpl;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.service.provider.ProviderIdCreatorService;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.provider.ProviderValidationService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
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
    public ProactiveClientServiceForGUI proactiveClientServiceForGUI(final GuiBackendProperties guiBackendProperties) {
        return new ProactiveClientServiceForGUIImpl(guiBackendProperties.getPaConfig().getRestUrl(),
                guiBackendProperties.getPaConfig().getLogin(),
                guiBackendProperties.getPaConfig().getPassword(),
                guiBackendProperties.getPaConfig().getEncryptorPw()) {
        };
    }
}
