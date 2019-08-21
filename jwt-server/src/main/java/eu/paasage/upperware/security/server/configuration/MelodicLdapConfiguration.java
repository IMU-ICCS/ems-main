package eu.paasage.upperware.security.server.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.security.properties")
@ComponentScan(basePackages = {"eu.paasage.upperware.security.*"})
@Profile("default")
@AllArgsConstructor
@EnableLdapRepositories(basePackages = "eu.paasage.upperware.security.**")
public class MelodicLdapConfiguration {

    private Environment env;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(env.getRequiredProperty("ldap.partitionSuffix"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.principal"));
        contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        contextSource.setPooled(false);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

}
