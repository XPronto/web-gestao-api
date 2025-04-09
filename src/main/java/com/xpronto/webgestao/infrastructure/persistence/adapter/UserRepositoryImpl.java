package com.xpronto.webgestao.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;

import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.infrastructure.persistence.entity.UserEntity;
import com.xpronto.webgestao.infrastructure.persistence.mapper.UserMapper;
import com.xpronto.webgestao.infrastructure.persistence.repository.JpaUserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        return UserMapper.toDomain(savedUserEntity);
    }

}
