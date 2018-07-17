package eu.paasage.upperware.security.server.security;

import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
    //   private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;
    private JWTService jwtService;
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/login-in").permitAll()
                .antMatchers(HttpMethod.GET, "/users").authenticated()
                .antMatchers(HttpMethod.POST, "/users/sign-up").authenticated()
                //.anyRequest().authenticated()
                //.and()
                //.formLogin()
                .and()
                //.logout().permitAll().logoutSuccessUrl("/users/login-in")
                //.and()
                //.httpBasic()
                //.and()
                //.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                //.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//    }


//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/users/sign-up").permitAll()
//                //.antMatchers(HttpMethod.POST, "/users/login-in").permitAll()
//                .antMatchers(HttpMethod.GET, "/users").permitAll()
////                .and()
////                .formLogin().loginPage("/users/login-in").permitAll()
//                .and()
//                //.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
//                //.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
//
//                //.anyRequest().authenticated()
//                //.and()
//                //.formLogin()
//                //.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
//                //.anyRequest().authenticated()
//                //.and()
//                // this disables session creation on Spring Security
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


//        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/users").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                //.formLogin()
//                //.and()
//                //.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
//                // this disables session creation on Spring Security
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);







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
