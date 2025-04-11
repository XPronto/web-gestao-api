package com.xpronto.webgestao.infrastructure.persistence.mapper;

import java.util.HashSet;
import java.util.List;

import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity) {
        List<PermissionSet> permissionSets = entity.getPermissionSets().stream()
                .map(PermissionSetMapper::toDomain)
                .toList();

        return new User(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                TenantMapper.toDomain(entity.getTenant()),
                permissionSets,
                entity.getCreatedAt());
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirtstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setTenant(TenantMapper.toEntity(user.getTenant()));
        entity.setPermissionSets(new HashSet<>(user.getPermissionSets().stream()
                .map(PermissionSetMapper::toEntity)
                .toList()));
        entity.setCreatedAt(user.getCreatedAt());
        return entity;
    }
}
