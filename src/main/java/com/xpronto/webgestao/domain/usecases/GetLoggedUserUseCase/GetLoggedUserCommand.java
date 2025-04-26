package com.xpronto.webgestao.domain.usecases.GetLoggedUserUseCase;

import java.util.UUID;

public class GetLoggedUserCommand {
    private final UUID userId;
    private final UUID tenantId;

    public GetLoggedUserCommand(UUID userId, UUID tenantId) {
        this.userId = userId;
        this.tenantId = tenantId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}