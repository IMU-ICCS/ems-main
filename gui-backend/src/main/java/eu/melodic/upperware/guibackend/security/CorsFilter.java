package eu.melodic.upperware.guibackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");

        if (httpServletRequest.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(httpServletRequest.getMethod())) {
            httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "Authorization, accept");
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, AUTH-TOKEN");
            httpServletResponse.addHeader("Access-Control-Max-Age", "1500");
        }
        if (!"OPTIONS".equals(httpServletRequest.getMethod())) {
            try {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ExpiredJwtException e) {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Expired token");
            } catch (MalformedJwtException | IllegalArgumentException e) {
                httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            }
        }
    }
}
