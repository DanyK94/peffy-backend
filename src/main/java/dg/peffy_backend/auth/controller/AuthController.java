package dg.peffy_backend.auth.controller;

import dg.peffy_backend.auth.config.RefreshTokenCookieConfig;
import dg.peffy_backend.auth.config.RefreshTokenCookieProperties;
import dg.peffy_backend.auth.dto.RefreshTokenResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.auth.service.RefreshTokenService;
import dg.peffy_backend.exception.InvalidRefreshTokenException;
import dg.peffy_backend.security.JwtService;
import dg.peffy_backend.user.UserService;
import dg.peffy_backend.user.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController 
@RequestMapping ("/api/auth")
public class AuthController {
    
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenCookieProperties cookieProperties;

    public AuthController(RefreshTokenService refreshTokenService, JwtService jwtService, UserService userService, RefreshTokenCookieProperties cookieProperties) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.cookieProperties = cookieProperties;
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        
        String rawToken = null;

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()){
                if (RefreshTokenCookieConfig.COOKIE_NAME.equals(cookie.getName())){
                    rawToken = cookie.getValue();
                    break;
                }
            }
        }
        
        if (rawToken == null || rawToken.isBlank()) {
            return ResponseEntity.status(401).build();
        }
        try {
            RefreshTokenResult result = refreshTokenService.rotateRefreshToken(rawToken);
            String newAccessToken = jwtService.generateAccessToken(result.user());

            ResponseCookie refreshCookie = ResponseCookie
                .from(RefreshTokenCookieConfig.COOKIE_NAME, result.refreshToken())
                .httpOnly(true)
                .secure(cookieProperties.secure())
                .sameSite("Lax")
                .path(RefreshTokenCookieConfig.COOKIE_PATH)
                .maxAge(RefreshTokenCookieConfig.REFRESH_TOKEN_EXPIRATION_SECONDS)
                .build();
            
            LoginResponse response = new LoginResponse(newAccessToken, "Bearer", jwtService.getAccessTokenExpirationSeconds(), userService.mapToResponse(result.user()));    
            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
        } catch (InvalidRefreshTokenException ex) {
            ResponseCookie clearCookie = ResponseCookie
                .from(RefreshTokenCookieConfig.COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path(RefreshTokenCookieConfig.COOKIE_PATH)
                .maxAge(0)
                .build();
            
                return ResponseEntity.status(401)
                    .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                    .build();
        }
    }
    
    @GetMapping("/csrf")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
        csrfToken.getToken();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        if (request.getCookies() != null){
            for (var cookie : request.getCookies()){
                if (RefreshTokenCookieConfig.COOKIE_NAME.equals(cookie.getName())){
                    String rawToken = cookie.getValue();

                    if (rawToken != null && !rawToken.isBlank()){
                        refreshTokenService.revokeRefeshToken(rawToken);
                    }
                    break;
                }
            }
        }

        ResponseCookie clearCookie = ResponseCookie
            .from(RefreshTokenCookieConfig.COOKIE_NAME, "")
            .httpOnly(true)
            .secure(cookieProperties.secure())
            .sameSite("Lax")
            .path(RefreshTokenCookieConfig.COOKIE_PATH)
            .maxAge(0)
            .build();
        
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, clearCookie.toString()).build();
    }
    
    
}
