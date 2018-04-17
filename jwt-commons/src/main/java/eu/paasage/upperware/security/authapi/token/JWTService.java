package eu.paasage.upperware.security.authapi.token;

import io.jsonwebtoken.Claims;

public interface JWTService {
    Claims parse(String token);

    String create(String userName);
}
