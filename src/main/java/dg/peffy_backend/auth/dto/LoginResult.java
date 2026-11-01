package dg.peffy_backend.auth.dto;

import dg.peffy_backend.user.dto.UserResponse;

public record LoginResult(
    String accessToken,
    String tokenType,
    long expiresIn,
    UserResponse user,
    String refreshToken
) {}