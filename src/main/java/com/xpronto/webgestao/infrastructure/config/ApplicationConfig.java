package com.xpronto.webgestao.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xpronto.webgestao.domain.repositories.PermissionRepository;
import com.xpronto.webgestao.domain.repositories.PermissionSetRepository;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.EncoderService;
import com.xpronto.webgestao.domain.services.email.EmailService;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.usecases.confirmuser.ConfirmUserUseCase;
import com.xpronto.webgestao.domain.usecases.createtenant.CreateTenantUseCase;
import com.xpronto.webgestao.domain.usecases.createuser.CreateUserUseCase;
import com.xpronto.webgestao.domain.usecases.refreshtoken.RefreshTokenUseCase;
import com.xpronto.webgestao.domain.usecases.signin.SignInUseCase;
import com.xpronto.webgestao.infrastructure.services.EncoderServiceImpl;
import com.xpronto.webgestao.infrastructure.services.JwtServiceAuth0;
import com.xpronto.webgestao.infrastructure.services.LocationIqGeocodingService;

@Configuration
public class ApplicationConfig {
    @Bean
    CreateTenantUseCase createTenantUseCase(UserRepository userRepository,
            PermissionSetRepository permissionSetRepository, TenantRepository tenantRepository,
            PermissionRepository permissionRepository, LocationIqGeocodingService geocodingService,
            EncoderServiceImpl encoderServiceImpl) {
        return new CreateTenantUseCase(userRepository, permissionSetRepository, tenantRepository, permissionRepository, geocodingService, encoderServiceImpl);
    }

    @Bean
    SignInUseCase signInUseCase(UserRepository userRepository, JwtServiceAuth0 jwtServiceAuth0, EncoderServiceImpl encoderServiceImpl) {
        return new SignInUseCase(userRepository, encoderServiceImpl, jwtServiceAuth0);
    }

    @Bean
    RefreshTokenUseCase refreshTokenUseCase(UserRepository userRepository, JwtServiceAuth0 jwtServiceAuth0) {
        return new RefreshTokenUseCase(userRepository, jwtServiceAuth0);
    }

    @Bean
    CreateUserUseCase createUserUseCase(UserRepository userRepository, TenantRepository tenantRepository, JwtServiceAuth0 jwtServiceAuth0, EmailService emailService) {
        return new CreateUserUseCase(userRepository, tenantRepository, jwtServiceAuth0, emailService);
    }

    @Bean
    ConfirmUserUseCase confirmUserUseCase(UserRepository userRepository, JwtService jwtService, EncoderService encoderService) {
        return new ConfirmUserUseCase(userRepository, jwtService, encoderService);
    }
}
