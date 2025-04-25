package com.xpronto.webgestao.domain.repositories;

import java.util.UUID;

import com.xpronto.webgestao.domain.model.Tenant;

public interface TenantRepository {
    Tenant findById(UUID id);
    boolean existsByDocument(String document);
    Tenant save(Tenant tenant);
}
