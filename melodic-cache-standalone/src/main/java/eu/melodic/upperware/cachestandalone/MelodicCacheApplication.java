package eu.melodic.upperware.cachestandalone;

import eu.melodic.cache.properties.CacheProperties;
import eu.melodic.security.authorization.util.properties.AuthorizationServiceClientProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan(basePackages = {
        "eu.melodic.upperware.cachestandalone",
        "eu.melodic.security.authorization.util.properties",
        "eu.melodic.cache"})
@SpringBootApplication
@EnableConfigurationProperties({MelodicSecurityProperties.class, AuthorizationServiceClientProperties.class, CacheProperties.class})
public class MelodicCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MelodicCacheApplication.class, args);
    }
}