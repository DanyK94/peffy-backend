package dg.peffy_backend.auth.config;

public class RefreshTokenCookieConfig {
    public static final String COOKIE_NAME = "refreshToken";
    public static final long REFRESH_TOKEN_EXPIRATION_SECONDS = 7 * 24 * 60 * 60;
    public static final String COOKIE_PATH = "/api/auth";
}
