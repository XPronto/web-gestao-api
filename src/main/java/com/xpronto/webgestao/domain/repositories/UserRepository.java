package com.xpronto.webgestao.domain.repositories;

import com.xpronto.webgestao.domain.model.User;

public interface UserRepository {
    User save(User user);
}
