package eu.melodic.upperware.dlms;

import io.github.cloudiator.rest.api.CloudApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import alluxio.conf.InstancedConfiguration;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.NodeApi;
import io.github.cloudiator.rest.api.ProcessApi;

@Configuration
@ComponentScan({"eu.passage.upperware.commons"})
public class DLMSWebServiceContext {
	@Bean
	public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
		return new JWTServiceImpl(melodicSecurityProperties);
	}
	
	@Bean
	public InstancedConfiguration instancedConfiguration(){
		return InstancedConfiguration.defaults();	
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
	public NodeApi nodeApi(ApiClient apiClient) {
		return new NodeApi(apiClient);
	}

	@Bean
	public ProcessApi processApi(ApiClient apiClient) {
		return new ProcessApi(apiClient);
	}

	@Bean
	public CloudApi cloudApi(ApiClient apiClient) {
		return new CloudApi(apiClient);
	}
	
}
