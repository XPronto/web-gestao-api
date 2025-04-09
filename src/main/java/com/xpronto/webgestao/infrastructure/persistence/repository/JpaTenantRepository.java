package com.xpronto.webgestao.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpronto.webgestao.infrastructure.persistence.entity.TenantEntity;

public interface JpaTenantRepository extends JpaRepository<TenantEntity, UUID> {
}
