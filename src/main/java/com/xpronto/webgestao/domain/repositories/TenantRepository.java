package com.xpronto.webgestao.domain.repositories;

import com.xpronto.webgestao.domain.model.Tenant;

public interface TenantRepository {
    boolean existsByDocument(String document);
    Tenant save(Tenant tenant);
}
