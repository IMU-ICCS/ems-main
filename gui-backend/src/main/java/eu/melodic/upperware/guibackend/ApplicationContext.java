package eu.melodic.upperware.guibackend;

import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.CloudApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
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
}
