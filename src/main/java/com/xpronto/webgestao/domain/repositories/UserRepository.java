package com.xpronto.webgestao.domain.repositories;

import java.util.UUID;

import com.xpronto.webgestao.domain.model.User;

public interface UserRepository {
    User findByEmail(String email);
    User findById(UUID id);
    User findById(UUID id, UUID tenantId);
    boolean existsByEmail(String email);
    boolean existsByEmail(String email, UUID tenantId);
    User save(User user);
}
