package eu.paasage.upperware.security.server.security;

import eu.paasage.upperware.security.authapi.JWTAuthorizationFilter;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final JWTService jwtService;

    @Value("${melodic.security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (securityEnabled) {
            log.info("Running WITH security");
            http.cors().and().csrf().disable()
                    .antMatcher("/user/**")
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtService), BasicAuthenticationFilter.class);

            // this disables session creation on Spring Security
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } else {
            log.info("Running WITHOUT security");
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        if (securityEnabled) {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
            return source;
        }
        return null;
    }

}
