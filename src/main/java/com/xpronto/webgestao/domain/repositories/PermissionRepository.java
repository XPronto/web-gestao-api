package com.xpronto.webgestao.domain.repositories;

import java.util.List;

import com.xpronto.webgestao.domain.model.Permission;

public interface PermissionRepository {
    List<Permission> findAll();
}
