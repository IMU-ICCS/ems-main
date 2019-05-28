package eu.melodic.upperware.guibackend;

import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.CloudApi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationContext {

    private GuiBackendProperties guiBackendProperties;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(guiBackendProperties.getCloudiator().getUrl());
        apiClient.setApiKey(guiBackendProperties.getCloudiator().getApiKey());
        apiClient.setReadTimeout(guiBackendProperties.getCloudiator().getHttpReadTimeout());
        return apiClient;
    }

    @Bean
    public CloudApi cloudApi(ApiClient apiClient) {
        return new CloudApi(apiClient);
    }
}
