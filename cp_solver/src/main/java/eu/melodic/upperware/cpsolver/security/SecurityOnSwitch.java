package eu.melodic.upperware.cpsolver.security;

import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.authapi.token.JWTServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SpringBootApplication
@ConditionalOnProperty(value = SecurityConstants.MELODIC_SECURITY_ENABLED_PROPERTY, havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"eu.paasage.upperware.security.authapi.properties"})
public class SecurityOnSwitch {

    public SecurityOnSwitch() {
        log.info("Running WITH security");
    }

    @Bean
    @ConfigurationProperties
    public MelodicSecurityProperties melodicSecurityProperties() {
        return new MelodicSecurityProperties();
    }

    @Bean
    public JWTService getJWTService() {
        return new JWTServiceImpl(melodicSecurityProperties());
    }

}