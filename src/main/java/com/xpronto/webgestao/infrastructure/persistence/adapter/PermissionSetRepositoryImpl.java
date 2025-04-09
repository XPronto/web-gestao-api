package com.xpronto.webgestao.infrastructure.persistence.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.domain.repositories.PermissionSetRepository;
import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionSetEntity;
import com.xpronto.webgestao.infrastructure.persistence.mapper.PermissionSetMapper;
import com.xpronto.webgestao.infrastructure.persistence.repository.JpaPermissionSetRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PermissionSetRepositoryImpl implements PermissionSetRepository {
    private final JpaPermissionSetRepository jpaPermissionSetRepository;

    @Override
    @Transactional
    public PermissionSet save(PermissionSet permissionSet) {
        PermissionSetEntity permissionSetEntity = PermissionSetMapper.toEntity(permissionSet);
        PermissionSetEntity savedPermissionSetEntity = jpaPermissionSetRepository.save(permissionSetEntity);
        return PermissionSetMapper.toDomain(savedPermissionSetEntity);
    }

    @Override
    @Transactional
    public void assignUser(UUID permissionSetId, UUID userId) {
        jpaPermissionSetRepository.assignUser(userId, permissionSetId);
    }

}
