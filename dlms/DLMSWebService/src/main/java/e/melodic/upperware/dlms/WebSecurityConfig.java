package e.melodic.upperware.dlms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security configuration for Spring.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // request configuration
        http
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and().
                httpBasic()
                .and().
                csrf()
                .disable();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // TODO password encoder not suited for live use --> change before go-live
        // user configuration
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("9687831d-a0bd-4d7a-993d-5d31e740749b")
                        .roles("USER")
                        .authorities("USER", "write")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

}
