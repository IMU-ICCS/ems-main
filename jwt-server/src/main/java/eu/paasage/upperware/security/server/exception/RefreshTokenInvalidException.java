package eu.paasage.upperware.security.server.exception;

public class RefreshTokenInvalidException extends RuntimeException {

    private static final String REFRESH_TOKEN_INVALID_MESSAGE = "Refresh token is invalid";

    public RefreshTokenInvalidException() {
        super(REFRESH_TOKEN_INVALID_MESSAGE);
    }

    public RefreshTokenInvalidException(String message) {
        super(message);
    }
}
