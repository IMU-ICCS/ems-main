package eu.melodic.upperware.activemqtorest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.passage.upperware.commons.cloudiator.CloudiatorProperties;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.NodeApi;

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * 2019 CAS Software AG
*/

@Configuration
public class MqHttpAdapterContext {
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
}
