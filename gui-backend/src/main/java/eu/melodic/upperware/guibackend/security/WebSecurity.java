package eu.melodic.upperware.guibackend.security;

import eu.paasage.upperware.security.authapi.JWTAuthorizationFilter;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
            http.csrf().disable().antMatcher("/application/**")
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtService), BasicAuthenticationFilter.class);
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        } else {
            log.info("Running WITHOUT security");
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();
        }
    }
}
