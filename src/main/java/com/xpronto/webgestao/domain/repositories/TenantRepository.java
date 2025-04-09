package com.xpronto.webgestao.domain.repositories;

import com.xpronto.webgestao.domain.model.Tenant;

public interface TenantRepository {
    Tenant save(Tenant tenant);
}
