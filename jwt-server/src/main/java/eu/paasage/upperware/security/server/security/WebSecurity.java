package eu.paasage.upperware.security.server.security;

import eu.paasage.upperware.security.authapi.JWTAuthenticationFilter;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
//    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;
    private JWTService jwtService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/sign-up").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login-in").permitAll()
                .antMatchers(HttpMethod.GET, "/users").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .ldapAuthentication()
//                .userDnPatterns("dc=example,dc=org")
//                .contextSource()
//                .url("ldap://52.30.133.171:389/cn=admin,dc=example,dc=org")
//                .managerDn("cn=admin,dc=example,dc=org")
//                .managerPassword("melodic")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(passwordEncoder)
//                .passwordAttribute("userPassword");
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
