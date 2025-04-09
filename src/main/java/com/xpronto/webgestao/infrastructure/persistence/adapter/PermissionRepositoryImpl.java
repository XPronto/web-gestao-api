package com.xpronto.webgestao.infrastructure.persistence.adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xpronto.webgestao.domain.model.Permission;
import com.xpronto.webgestao.domain.repositories.PermissionRepository;
import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionEntity;
import com.xpronto.webgestao.infrastructure.persistence.mapper.PermissionMapper;
import com.xpronto.webgestao.infrastructure.persistence.repository.JpaPermissionRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final JpaPermissionRepository jpaPermissionRepository;

    @Override
    public List<Permission> findAll() {
        List<PermissionEntity> permissionEntities = jpaPermissionRepository.findAll();
        return permissionEntities.stream()
                .map(PermissionMapper::toDomain)
                .toList();
    }

}
