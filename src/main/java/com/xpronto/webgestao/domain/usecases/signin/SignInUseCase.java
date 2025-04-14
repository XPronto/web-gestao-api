package com.xpronto.webgestao.domain.usecases.signin;

import com.xpronto.webgestao.domain.errors.BadRequestException;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.EncoderService;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.usecases.UseCase;

public class SignInUseCase implements UseCase<SignInCommand, Tokens> {
    private final UserRepository userRepository;
    private final EncoderService encoderService;
    private final JwtService jwtService;

    public SignInUseCase(UserRepository userRepository, EncoderService encoderService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
        this.jwtService = jwtService;
    }

    @Override
    public Tokens execute(SignInCommand command) throws Exception {
        User user = userRepository.findByEmail(command.getEmail());

        if (user == null)
            throw new BadRequestException("Incorrect email or password.");

        if (!encoderService.matches(command.getPassword(), user.getPasswordHash()))
            throw new BadRequestException("Incorrect email or password.");

        return jwtService.signAuthTokens(user);
    }

}
