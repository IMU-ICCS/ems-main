package eu.paasage.upperware.security.authapi;

import eu.paasage.upperware.security.authapi.token.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authManager, JWTService jwtService) {
		super(authManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws IOException, ServletException {
		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied - missing or wrong header");
			return;
		}

		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(req, res);
				return;
			}
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}

	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
		if (token != null) {
			String user = jwtService.parse(token).getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
