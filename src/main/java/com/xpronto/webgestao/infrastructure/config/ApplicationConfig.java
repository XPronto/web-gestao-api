package com.xpronto.webgestao.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xpronto.webgestao.domain.repositories.PermissionRepository;
import com.xpronto.webgestao.domain.repositories.PermissionSetRepository;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantUseCase;
import com.xpronto.webgestao.infrastructure.services.geolocation.LocationIqGeocodingService;

@Configuration
public class ApplicationConfig {
    @Bean
    CreateTenantUseCase createTenantUseCase(UserRepository userRepository, PermissionSetRepository permissionSetRepository, TenantRepository tenantRepository, PermissionRepository permissionRepository, LocationIqGeocodingService geocodingService) {
        return new CreateTenantUseCase(userRepository, permissionSetRepository, tenantRepository, permissionRepository, geocodingService);
    }
}
