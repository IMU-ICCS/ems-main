/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import eu.melodic.security.authorization.client.AuthorizationServiceClient;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import io.github.cloudiator.rest.ApiClient;
import io.github.cloudiator.rest.api.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationContext {

  public static final String INNER_THREAD_POOL_TASK_EXECUTOR_NAME = "innerThreadPoolTaskExecutor";

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
  @Primary
  public ThreadPoolTaskExecutor getTaskExecutor() {
    return createThreadPoolTaskExecutor("main-task-executor-");
  }

  @Bean
  @Qualifier(INNER_THREAD_POOL_TASK_EXECUTOR_NAME)
  public ThreadPoolTaskExecutor getInnerTaskExecutor() {
    return createThreadPoolTaskExecutor("inner-task-executor-");
  }

  private ThreadPoolTaskExecutor createThreadPoolTaskExecutor(String prefix) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix(prefix);
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
  public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
    return new JWTServiceImpl(melodicSecurityProperties);
  }

  @Bean
  public JobApi jobApi(ApiClient apiClient) {
    return new JobApi(apiClient);
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
    return new ProcessApi(apiClient);
  }

  @Bean
  public MonitoringApi monitoringApi(ApiClient apiClient) {
    return new MonitoringApi(apiClient);
  }

  @Bean
  public ApiClient apiClient() {
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(adapterProperties.getCloudiatorV2().getUrl());
    apiClient.setApiKey(adapterProperties.getCloudiatorV2().getApiKey());
    apiClient.setReadTimeout(adapterProperties.getCloudiatorV2().getHttpReadTimeout());
    return apiClient;
  }

}
