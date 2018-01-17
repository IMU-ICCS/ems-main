/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.solvertodeployment;

import eu.melodic.cache.properties.CacheProperties;
import lombok.AllArgsConstructor;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SolverToDeploymentContext {

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  @ConfigurationProperties
  public CacheProperties cacheProperties(){
    return new CacheProperties();
  }

  @Bean
  public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
    String host = cacheProperties.getCache().getHost();
    Integer port = cacheProperties.getCache().getPort();
    return new MemcachedClient(new InetSocketAddress(host, port));
  }

}
