package com.xpronto.webgestao.domain.services.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.xpronto.webgestao.infrastructure.persistence.entity.UserEntity;

public class UserPayload {
    private final UUID userId;
    private final UUID tenantId;
    private final List<String> permissions;

    public UserPayload(String userId, String tenantId, List<String> permissions) {
        this.userId = userId == null ? null : UUID.fromString(userId);
        this.tenantId = tenantId == null ? null : UUID.fromString(tenantId);
        this.permissions = permissions == null ? new ArrayList<>() : permissions;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UserEntity.mapAuthorities(permissions);
    }
}
