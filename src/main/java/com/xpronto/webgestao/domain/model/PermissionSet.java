package com.xpronto.webgestao.domain.model;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class PermissionSet {
    private UUID id;
    private String name;
    private Tenant tenant;
    private Set<Permission> permissions;
    private OffsetDateTime createdAt;

    public PermissionSet() {
    }

    public PermissionSet(UUID id, String name, Tenant tenant, Set<Permission> permissions, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.tenant = tenant;
        this.permissions = permissions;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
