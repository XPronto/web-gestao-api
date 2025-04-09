package com.xpronto.webgestao.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;

import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.infrastructure.persistence.entity.TenantEntity;
import com.xpronto.webgestao.infrastructure.persistence.mapper.TenantMapper;
import com.xpronto.webgestao.infrastructure.persistence.repository.JpaTenantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {
    private final JpaTenantRepository jpaTenantRepository;

    @Override
    @Transactional
    public Tenant save(Tenant tenant) {
        TenantEntity tenantEntity = TenantMapper.toEntity(tenant);
        TenantEntity savedTenantEntity = jpaTenantRepository.save(tenantEntity);
        return TenantMapper.toDomain(savedTenantEntity);
    }

}
