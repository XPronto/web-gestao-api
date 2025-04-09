package com.xpronto.webgestao.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Tenant {
    private UUID id;
    private String name;
    private Address address;
    private List<User> users;
    private List<PermissionSet> permissionSets;
    private OffsetDateTime createdAt;

    public Tenant() {
    }

    public Tenant(UUID id, String name, Address address, List<User> users, List<PermissionSet> permissionSets,
            OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.users = users;
        this.permissionSets = permissionSets;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

}
