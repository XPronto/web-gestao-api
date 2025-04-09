package com.xpronto.webgestao.infrastructure.persistence.mapper;

import java.util.HashSet;
import java.util.List;

import com.xpronto.webgestao.domain.model.Permission;
import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionSetEntity;

public class PermissionSetMapper {
    public static PermissionSet toDomain(PermissionSetEntity entity) {
        List<Permission> permissions = entity.getPermissions().stream()
                .map(PermissionMapper::toDomain)
                .toList();

        return new PermissionSet(
                entity.getId(),
                entity.getName(),
                TenantMapper.toDomain(entity.getTenant()),
                new HashSet<>(permissions),
                entity.getCreatedAt());
    }

    public static PermissionSetEntity toEntity(PermissionSet domain) {
        PermissionSetEntity entity = new PermissionSetEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setTenant(TenantMapper.toEntity(domain.getTenant()));
        entity.setPermissions(new HashSet<>(
                domain.getPermissions().stream()
                        .map(PermissionMapper::toEntity)
                        .toList()));
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
