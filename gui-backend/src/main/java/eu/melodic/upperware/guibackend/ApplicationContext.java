package eu.melodic.upperware.guibackend;

import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
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
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }
}
