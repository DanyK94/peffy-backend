package dg.peffy_backend.user.dto;

public record LoginResponse(
    String accessToken,
    String tokenType,
    long expiresIn,
    UserResponse user
) {}
