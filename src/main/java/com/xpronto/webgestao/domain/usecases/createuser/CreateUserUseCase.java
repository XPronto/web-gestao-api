package com.xpronto.webgestao.domain.usecases.createuser;

import java.time.OffsetDateTime;

import com.xpronto.webgestao.domain.errors.ConflictException;
import com.xpronto.webgestao.domain.errors.NotFoundException;
import com.xpronto.webgestao.domain.model.Tenant;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.TenantRepository;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.email.EmailService;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.usecases.UseCase;

public class CreateUserUseCase implements UseCase<CreateUserCommand, User> {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    public CreateUserUseCase(UserRepository userRepository, TenantRepository tenantRepository, JwtService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    @Override
    public User execute(CreateUserCommand command) throws Exception {
        if (userRepository.existsByEmail(command.getEmail(), command.getTenantId()))
            throw new ConflictException("E-mail already registered.");

        Tenant tenant = tenantRepository.findById(command.getTenantId());

        if (tenant == null)
            throw new NotFoundException("Tenant not found.");

        User newUser = new User();
        newUser.setFirtstName(command.getFirstName());
        newUser.setLastName(command.getLastName());
        newUser.setEmail(command.getEmail());
        newUser.setTenant(tenant);
        newUser.setCreatedAt(OffsetDateTime.now());

        User user = userRepository.save(newUser);

        String inviteToken = jwtService.signInviteToken(user);

        emailService.sendInviteEmail(user.getEmail(), user.getFirtstName(), inviteToken);

        return user;
    }

}
