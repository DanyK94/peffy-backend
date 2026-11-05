package dg.peffy_backend.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "app.auth.refresh-cookie")
public record RefreshTokenCookieProperties(
    Boolean secure
) {
        
}
