/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter;

import camel.deployment.DeploymentPackage;
import camel.organisation.OrganisationPackage;
import camel.type.TypePackage;
import cloud.morphemic.connectors.ProactiveClientConnectorService;
import com.google.gson.Gson;
import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.security.authorization.client.AuthorizationServiceClient;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapter;
import eu.melodic.upperware.adapter.communication.proactive.ProactiveClientServiceForAdapterImpl;
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.melodic.upperware.adapter.service.Instance_no_provider.BusyInstancesRegistry;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.AllArgsConstructor;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

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
    return new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE, TypesPackage.eINSTANCE,
            TypePackage.eINSTANCE, OrganisationPackage.eINSTANCE, DeploymentPackage.eINSTANCE));
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
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
    String host = cacheProperties.getCache().getHost();
    Integer port = cacheProperties.getCache().getPort();
    return new MemcachedClient(new BinaryConnectionFactory(), Collections.singletonList(new InetSocketAddress(host, port)));
  }

  @Bean
  public ProactiveClientServiceForAdapter proactiveClientServiceForAdapter(ProactiveClientConnectorService proactiveClientConnectorService) {
    return new ProactiveClientServiceForAdapterImpl(proactiveClientConnectorService);
  }

  //Busy Instance Number provider
  @Bean
  public BusyInstancesRegistry busyInstancesRegistry(ProactiveClientServiceForAdapter proactiveClientServiceForAdapter) {
    return new BusyInstancesRegistry(
            new ConcurrentHashMap<>(),
            proactiveClientServiceForAdapter
    );
  }
}
