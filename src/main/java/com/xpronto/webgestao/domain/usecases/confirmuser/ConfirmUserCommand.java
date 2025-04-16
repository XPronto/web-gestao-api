package com.xpronto.webgestao.domain.usecases.confirmuser;

import java.util.UUID;

public class ConfirmUserCommand {
    private final UUID userId;
    private final UUID tenantId;
    private final String password;

    public ConfirmUserCommand(UUID userId, UUID tenantId, String password) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public String getPassword() {
        return password;
    }
}
