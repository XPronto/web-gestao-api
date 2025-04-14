package com.xpronto.webgestao.infrastructure.persistence.adapter;

import java.util.UUID;

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

    @Override
    public Boolean existsByEmail(String email) {
        return jpaUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findById(UUID id) {
        return jpaUserRepository.findById(id)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

}
