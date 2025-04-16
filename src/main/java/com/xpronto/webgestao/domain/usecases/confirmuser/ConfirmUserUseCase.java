package com.xpronto.webgestao.domain.usecases.confirmuser;

import com.xpronto.webgestao.domain.errors.ConflictException;
import com.xpronto.webgestao.domain.errors.NotFoundException;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.EncoderService;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.usecases.UseCase;

public class ConfirmUserUseCase implements UseCase<ConfirmUserCommand, Tokens> {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EncoderService encoderService;

    public ConfirmUserUseCase(UserRepository userRepository, JwtService jwtService, EncoderService encoderService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoderService = encoderService;
    }

    @Override
    public Tokens execute(ConfirmUserCommand command) throws Exception {
        User user = userRepository.findById(command.getUserId(), command.getTenantId());

        if (user == null)
            throw new NotFoundException("User not found.");

        if (user.isVerified() && user.getPasswordHash() != null)
            throw new ConflictException("User has already been verified.");

        String passwordHash = encoderService.encode(command.getPassword());

        user.setVerified(true);
        user.setPasswordHash(passwordHash);

        User createdUser = userRepository.save(user);

        return jwtService.signAuthTokens(createdUser);
    }

}
