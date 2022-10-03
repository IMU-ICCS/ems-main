package eu.melodic.upperware.cachestandalone;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.security.authorization.client.AuthorizationServiceClient;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

@Configuration
public class ApplicationContext {

    @Bean
    public MemcachedClient memcachedClient(CacheProperties cacheProperties) throws IOException {
        String host = cacheProperties.getCache().getHost();
        Integer port = cacheProperties.getCache().getPort();
        return new MemcachedClient(new BinaryConnectionFactory(), Collections.singletonList(new InetSocketAddress(host, port)));
    }

    @Bean
    public AuthorizationServiceClient getAuthorizationServiceClient(AuthorizationServiceClientProperties authorizationServiceClientProperties) {
        return new AuthorizationServiceClient(authorizationServiceClientProperties);
    }

    @Bean
    public JWTService jWTService(MelodicSecurityProperties melodicSecurityProperties) {
        return new JWTServiceImpl(melodicSecurityProperties);
    }
}
