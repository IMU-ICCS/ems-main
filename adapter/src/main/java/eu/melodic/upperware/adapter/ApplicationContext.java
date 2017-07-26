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
import eu.melodic.upperware.adapter.properties.AdapterProperties;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationContext {

  private AdapterProperties adapterProperties;

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CDOClient getCdoClient() {
    return new CDOClient();
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
}
