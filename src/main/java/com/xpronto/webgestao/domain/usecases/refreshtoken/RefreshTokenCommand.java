package com.xpronto.webgestao.domain.usecases.refreshtoken;

import java.util.UUID;

public class RefreshTokenCommand {
    private final UUID userId;

    public RefreshTokenCommand(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
