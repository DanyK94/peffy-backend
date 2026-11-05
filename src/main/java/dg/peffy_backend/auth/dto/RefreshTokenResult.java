package dg.peffy_backend.auth.dto;

import dg.peffy_backend.user.User;

public record RefreshTokenResult(
    String refreshToken,
    User user
) {}
