package eu.paasage.upperware.security.server.data.service;


import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.data.repository.RefreshToken;
import eu.paasage.upperware.security.server.data.repository.RefreshTokenRepository;
import eu.paasage.upperware.security.server.exception.RefreshTokenInvalidException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshTokenService {

    private JWTService jwtService;
    private RefreshTokenRepository repository;


    public String createRefreshToken(String username) {
        String refreshToken = jwtService.createRefreshToken(username);
        repository.save(new RefreshToken(getId(refreshToken)));
        log.debug("Refresh token has been saved.");
        return refreshToken;
    }

    public void validateToken(String authorization) throws RefreshTokenInvalidException {
        Claims claims = jwtService.parse(authorization);

        String audience = claims.getAudience();
        String tokenId = claims.getId();
        log.debug("Claims: {}", claims.toString());

        if (SecurityConstants.AUDIENCE_JWT.equals(audience)) {
            Optional<RefreshToken> tokenById = repository.findById(tokenId);
            if (tokenById.isPresent()) {
                if (RefreshToken.RefreshTokenType.NEW.equals(tokenById.get().getState())) {
                    log.info("Token with id: {} can be used", tokenId);
                } else {
                    throw new RefreshTokenInvalidException(String.format("Token with id: %s cannot be used, its state is %s.", tokenId, tokenById.get().getState()));
                }
            } else {
                throw new RefreshTokenInvalidException(String.format("Token with id: %s does not exist in the refresh token repository", tokenId));
            }
        } else {
            throw new RefreshTokenInvalidException();
        }
    }

    public void invalidateToken(String token) {
        String id = jwtService.parse(token).getId();
        RefreshToken refreshToken = repository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("Token with id: %s does not exist", id)));

        if (RefreshToken.RefreshTokenType.NEW.equals(refreshToken.getState())) {
            repository.save(new RefreshToken(id, RefreshToken.RefreshTokenType.INVALIDATED));
        } else {
            log.info("Token cannot be used, it was used before");
        }
        log.debug("found: {}", repository.findById(id).get().getState());

    }

    public void useToken(String token) {

        String tokenId = getId(token);
        repository.findById(tokenId)
                .orElseThrow(() -> new IllegalStateException(String.format("Token with id: %s does not exist", tokenId)));
        repository.save(new RefreshToken(tokenId, RefreshToken.RefreshTokenType.USED));

    }

    public String getUsername(String authorization) {
        return jwtService.parse(authorization).getSubject();

    }

    private String getId(String token) {
        return jwtService.parse(token).getId();
    }

}
