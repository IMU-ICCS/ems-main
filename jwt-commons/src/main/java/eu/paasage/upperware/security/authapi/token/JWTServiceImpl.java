package eu.paasage.upperware.security.authapi.token;

import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
                .setAudience(SecurityConstants.AUDIENCE_UPPERWARE)
                .setExpiration(new Date(System.currentTimeMillis() + melodicSecurityProperties.getJwt().getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, melodicSecurityProperties.getJwt().getSecret().getBytes())
                .compact();
    }

    @Override
    public String createRefreshToken(String userName) {

        Map<String, Object> header = new HashMap<>();
        header.put(Header.CONTENT_TYPE, SecurityConstants.REFRESH_HEADER_STRING);


        return Jwts.builder()
                .setSubject(userName)
                .setHeader(header)
                .setAudience(SecurityConstants.AUDIENCE_JWT)
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + melodicSecurityProperties.getJwt().getRefreshTokenExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, melodicSecurityProperties.getJwt().getSecret().getBytes())
                .compact();
    }

}
