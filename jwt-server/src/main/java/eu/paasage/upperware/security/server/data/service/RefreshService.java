package eu.paasage.upperware.security.server.data.service;


import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.exception.RefreshTokenInvalidException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshService {

    private JWTService jwtService;


    public void validateToken(String authorization) throws RefreshTokenInvalidException{
        Claims claims = jwtService.parse(authorization);

        String audience = claims.getAudience();
        log.info("claims: {}", claims.toString());

        if (SecurityConstants.AUDIENCE_JWT.equals(audience) ) { //todo: check database and validation

        }
        //
        else {
            throw new RefreshTokenInvalidException();
        }
    }

    public String getUsername(String authorization){
        return jwtService.parse(authorization).getSubject();

    }

}
