package com.xpronto.webgestao.domain.repositories;

import com.xpronto.webgestao.domain.model.User;

public interface UserRepository {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User save(User user);
}
