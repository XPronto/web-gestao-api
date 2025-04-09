package com.xpronto.webgestao.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xpronto.webgestao.infrastructure.persistence.entity.PermissionSetEntity;

public interface JpaPermissionSetRepository extends JpaRepository<PermissionSetEntity, UUID> {
    @Modifying
    @Query(value = "INSERT INTO user_permission_sets (user_id, permission_set_id) VALUES (:userId, :permissionSetId)", nativeQuery = true)
    void assignUser(@Param("userId") UUID userId, @Param("permissionSetId") UUID permissionSetId);
}
