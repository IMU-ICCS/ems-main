/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import eu.melodic.security.authorization.client.AuthorizationServiceClient;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationContext {

  private AdapterProperties adapterProperties;

  @Bean
  public Filter loggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeClientInfo(true);
    filter.setIncludeHeaders(true);
    filter.setIncludePayload(true);
    filter.setIncludeQueryString(true);
    filter.setMaxPayloadLength(10000);
    return filter;
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CDOClientX getCdoClient() {
    return new CDOClientXImpl();
  }

  @Bean
  public Client getClient() {
    AdapterProperties.Colosseum colosseum = adapterProperties.getColosseum();
    AdapterProperties.Colosseum.Auth colosseumAuth = colosseum.getAuth();
    return ClientBuilder.getNew()
      .url(colosseum.getUrl())
      .credentials(colosseumAuth.getEmail(), colosseumAuth.getTenant(), colosseumAuth.getPassword())
      .build();
  }

  @Bean
  public ThreadPoolTaskExecutor getTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    AdapterProperties.TaskExecutor taskExecutor = adapterProperties.getTaskExecutor();
    if (taskExecutor != null) {
      Integer corePoolSize = taskExecutor.getCorePoolSize();
      Integer maxPoolSize = taskExecutor.getMaxPoolSize();
      Integer queueCapacity = taskExecutor.getQueueCapacity();
      if (corePoolSize != null) {
        executor.setCorePoolSize(corePoolSize);
      }
      if (maxPoolSize != null) {
        executor.setMaxPoolSize(maxPoolSize);
      }
      if (queueCapacity != null) {
        executor.setQueueCapacity(queueCapacity);
      }
    }
    return executor;
  }

  @Bean
  public AuthorizationServiceClient getAuthorizationServiceClient(AuthorizationServiceClientProperties authorizationServiceClientProperties) {
    return new AuthorizationServiceClient(authorizationServiceClientProperties);
  }

  @Bean
  @ConfigurationProperties
  public AuthorizationServiceClientProperties authorizationServiceClientProperties(){
    return new AuthorizationServiceClientProperties();
  }
}
