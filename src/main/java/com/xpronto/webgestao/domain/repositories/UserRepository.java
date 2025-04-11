package com.xpronto.webgestao.domain.repositories;

import com.xpronto.webgestao.domain.model.User;

public interface UserRepository {
    Boolean existsByEmail(String email);
    User save(User user);
}
