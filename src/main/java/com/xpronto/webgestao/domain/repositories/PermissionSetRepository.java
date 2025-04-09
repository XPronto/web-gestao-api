package com.xpronto.webgestao.domain.repositories;

import java.util.UUID;

import com.xpronto.webgestao.domain.model.PermissionSet;

public interface PermissionSetRepository {
    PermissionSet save(PermissionSet permissionSet);
    void assignUser(UUID permissionSetId, UUID userId);
}
