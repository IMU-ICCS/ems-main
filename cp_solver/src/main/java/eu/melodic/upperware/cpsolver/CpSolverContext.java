/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.cpsolver;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.passage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.passage.upperware.security.authapi.token.JWTService;
import eu.passage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.AllArgsConstructor;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

@Configuration
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CpSolverContext {

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
  @ConfigurationProperties
  public UtilityGeneratorProperties utilityGeneratorProperties() {return new UtilityGeneratorProperties();}


  @Bean
  public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
    String host = cacheProperties.getCache().getHost();
    Integer port = cacheProperties.getCache().getPort();
    return new MemcachedClient(new BinaryConnectionFactory(), Collections.singletonList(new InetSocketAddress(host, port)));
  }

  @Bean
  @ConfigurationProperties
  public MelodicSecurityProperties melodicSecurityProperties(){
    return new MelodicSecurityProperties();
  }

  @Bean
  public JWTService getJWTService(){
    return new JWTServiceImpl(melodicSecurityProperties());
  }

}
