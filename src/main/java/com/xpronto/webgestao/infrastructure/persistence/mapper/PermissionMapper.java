package com.xpronto.webgestao.infrastructure.persistence.mapper;

import com.xpronto.webgestao.domain.model.Permission;
import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionEntity;

public class PermissionMapper {
    public static Permission toDomain(PermissionEntity entity) {
        return new Permission(
                entity.getId(),
                entity.getCode(),
                entity.getDescription());
    }

    public static PermissionEntity toEntity(Permission domain) {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        return entity;
    }
}
