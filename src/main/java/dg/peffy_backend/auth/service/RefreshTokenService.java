package dg.peffy_backend.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.stereotype.Service;

import dg.peffy_backend.auth.RefreshTokenRepository;
import dg.peffy_backend.auth.dto.RefreshTokenResult;
import dg.peffy_backend.auth.entity.RefreshToken;
import dg.peffy_backend.exception.InvalidRefreshTokenException;
import dg.peffy_backend.user.User;
import jakarta.transaction.Transactional;

@Service 
public class RefreshTokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;
    private static final int TOKEN_BYTES = 32;
    private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 7;

    private final SecureRandom secureRandom = new SecureRandom();

    RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional 
    public String createRefreshToken(User user){

        //Generazione di 256 bit casuali
        byte[] randomBytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(randomBytes);

        //Token originale da inviare a browser
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        //Entity
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashToken(rawToken));
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION_DAYS));

        refreshTokenRepository.save(refreshToken);

        // Restituiamo il token originale (non Hash)
        return rawToken;

    }

    public String hashToken(String token){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 algorithm not avaiable");
        }
    }

    @Transactional 
    public RefreshToken validateRefreshToken(String rawToken){
        String tokenHash = hashToken(rawToken);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash).orElseThrow(InvalidRefreshTokenException::new);

        if (refreshToken.isExpired()) {
            throw new InvalidRefreshTokenException();
        }
        if (refreshToken.isRevoked()) {
            throw new IllegalArgumentException(
                    "Refresh token revoked"
            );
        }

        return refreshToken;
    }

    @Transactional 
    public RefreshTokenResult rotateRefreshToken(String rawToken){
        String tokenHash = hashToken(rawToken);

        RefreshToken oldToken = refreshTokenRepository.findByTokenHashForUpdate(tokenHash).orElseThrow(InvalidRefreshTokenException::new);

        if (oldToken.isExpired() || oldToken.isRevoked()) {
            throw new InvalidRefreshTokenException();
        }

        User user = oldToken.getUser();
        oldToken.revoke();
        String newRefreshToken = createRefreshToken(user);
        return new RefreshTokenResult(newRefreshToken,user);
    }
}
