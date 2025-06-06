package com.xpronto.webgestao.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String firtstName;
    private String lastName;
    private String email;
    private boolean verified;
    private String passwordHash;
    private Tenant tenant;
    private List<PermissionSet> permissionSets = new ArrayList<>();
    private OffsetDateTime createdAt;

    public User(UUID id, String firtstName, String lastName, String email, boolean verified, String passwordHash,
            Tenant tenant, List<PermissionSet> permissionSets, OffsetDateTime createdAt) {
        this.id = id;
        this.firtstName = firtstName;
        this.lastName = lastName;
        this.email = email;
        this.verified = verified;
        this.passwordHash = passwordHash;
        this.tenant = tenant;
        this.permissionSets = permissionSets;
        this.createdAt = createdAt;
    }

    public User(UUID id, String firtstName, String lastName, String email, String passwordHash, Tenant tenant,
            List<PermissionSet> permissionSets, OffsetDateTime createdAt) {
        this.id = id;
        this.firtstName = firtstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.tenant = tenant;
        this.permissionSets = permissionSets;
        this.createdAt = createdAt;
        this.verified = false;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(List<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirtstName() {
        return firtstName;
    }

    public void setFirtstName(String firtstName) {
        this.firtstName = firtstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public static List<String> mapPermissionsCodes(List<PermissionSet> permissionSets) {
        return permissionSets.stream()
                .flatMap((permissionSet -> permissionSet.getPermissions().stream()
                        .map(permission -> permission.getCode())))
                .toList();
    }
}
