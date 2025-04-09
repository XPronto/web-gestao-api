package com.xpronto.webgestao.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionEntity;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, UUID> {
}
