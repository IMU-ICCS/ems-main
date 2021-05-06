package eu.melodic.upperware.guibackend;

import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.cloudiator.CloudiatorApi;
import eu.passage.upperware.commons.cloudiator.CloudiatorClientApi;
import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import eu.passage.upperware.commons.cloudiator.QueueInspector;
import eu.passage.upperware.commons.service.provider.ProviderIdCreatorService;
import eu.passage.upperware.commons.service.provider.ProviderService;
import eu.passage.upperware.commons.service.provider.ProviderValidationService;
import eu.passage.upperware.commons.service.store.SecureStoreDBService;
import eu.passage.upperware.commons.service.store.SecureStoreService;
import eu.passage.upperware.commons.service.yaml.YamlDataService;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.*;
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
    public ApiClient apiClient(CloudiatorProperties cloudiatorProperties) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(cloudiatorProperties.getCloudiator().getUrl());
        apiClient.setApiKey(cloudiatorProperties.getCloudiator().getApiKey());
        apiClient.setReadTimeout(cloudiatorProperties.getCloudiator().getHttpReadTimeout());
        return apiClient;
    }

    @Bean
    public CloudApi cloudApi(ApiClient apiClient) {
        return new CloudApi(apiClient);
    }

    @Bean
    public SecurityApi securityApi(ApiClient apiClient) {
        return new SecurityApi(apiClient);
    }

    @Bean
    public NodeApi nodeApi(ApiClient apiClient) {
        return new NodeApi(apiClient);
    }

    @Bean
    public QueueApi queueApi(ApiClient apiClient) {
        return new QueueApi(apiClient);
    }

    @Bean
    public ProcessApi processApi(ApiClient apiClient) {
        return new ProcessApi((apiClient));
    }

    @Bean
    public JobApi jobApi(ApiClient apiClient) {
        return new JobApi(apiClient);
    }

    @Bean
    public MonitoringApi monitoringApi(ApiClient apiClient) {
        return new MonitoringApi(apiClient);
    }

    @Bean
    public QueueInspector queueInspector(
        QueueApi queueApi,
        CloudiatorProperties cloudiatorProperties
    ) {
        return new QueueInspector(queueApi, cloudiatorProperties);
    }

    @Bean
    public CloudiatorApi cloudiatorApi(
        CloudApi cloudApi,
        SecurityApi securityApi,
        NodeApi nodeApi,
        ProcessApi processApi,
        QueueApi queueApi,
        JobApi jobApi,
        MonitoringApi monitoringApi,
        QueueInspector queueInspector
    ) {
        return new CloudiatorClientApi(
            cloudApi,
            securityApi,
            nodeApi,
            processApi,
            queueApi,
            jobApi,
            monitoringApi,
            queueInspector
        );
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
    SecureStoreService secureStoreService(CloudiatorApi cloudiatorApi) {
        return new SecureStoreService(cloudiatorApi);
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
}
