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


    public String createToken(String username) {
        String encodedRefreshToken = jwtService.createRefreshToken(username);
        RefreshToken decodedRefreshToken = decodeRefreshToken(encodedRefreshToken);
        repository.save(new RefreshToken(decodedRefreshToken.getId(), username));
        log.debug("Refresh token has been saved.");
        return encodedRefreshToken;
    }

    private RefreshToken decodeRefreshToken(String encodedToken) {
        Claims claims = jwtService.parse(encodedToken);
        return new RefreshToken(claims.getId(), claims.getSubject());
    }

    public RefreshToken validateToken(String encodedToken) throws RefreshTokenInvalidException {
        Claims claims = jwtService.parse(encodedToken);
        String audience = claims.getAudience();
        String tokenId = claims.getId();
        log.debug("Claims: {}", claims.toString());

        if (SecurityConstants.AUDIENCE_JWT.equals(audience)) {
            Optional<RefreshToken> tokenById = repository.findById(tokenId);
            if (tokenById.isPresent()) {
                if (RefreshToken.RefreshTokenState.NEW.equals(tokenById.get().getState())) {
                    log.debug("Token with id: {} can be used", tokenId);
                    return tokenById.get();
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

    public void invalidateToken(String encodedToken) {
        String id = jwtService.parse(encodedToken).getId();
        RefreshToken refreshToken = repository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("Token with id: %s does not exist", id)));

        if (RefreshToken.RefreshTokenState.NEW.equals(refreshToken.getState())) {
            refreshToken.setState(RefreshToken.RefreshTokenState.INVALIDATED);
            repository.save(refreshToken);
            log.info("Token with id: {} has been invalidated.", id);
        } else {
            throw new IllegalStateException(String.format("Token cannot be invalidated, its state is %s", refreshToken.getState()));
        }
        log.debug("found: {}", repository.findById(id).get().getState());
    }

    public void useToken(RefreshToken refreshToken) {
        String tokenId = refreshToken.getId();
        repository.findById(tokenId)
                .orElseThrow(() -> new IllegalStateException(String.format("Token with id: %s does not exist", tokenId)));
        refreshToken.setState(RefreshToken.RefreshTokenState.USED);
        repository.save(refreshToken);

    }

}
