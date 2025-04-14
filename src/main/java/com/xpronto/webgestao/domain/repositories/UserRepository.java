package com.xpronto.webgestao.domain.repositories;

import java.util.UUID;

import com.xpronto.webgestao.domain.model.User;

public interface UserRepository {
    User findByEmail(String email);
    User findById(UUID id);
    Boolean existsByEmail(String email);
    User save(User user);
}
