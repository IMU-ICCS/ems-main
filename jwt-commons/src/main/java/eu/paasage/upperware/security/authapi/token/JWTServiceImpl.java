package eu.paasage.upperware.security.authapi.token;

import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService {

    private MelodicSecurityProperties melodicSecurityProperties;

    @Override
    public Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(melodicSecurityProperties.getJwt().getSecret().getBytes())
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();
    }

    @Override
    public String create(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + melodicSecurityProperties.getJwt().getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, melodicSecurityProperties.getJwt().getSecret().getBytes())
                .compact();
    }

}
