package com.xpronto.webgestao.domain.usecases.refreshtoken;

import com.xpronto.webgestao.domain.errors.NotFoundException;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.usecases.UseCase;

public class RefreshTokenUseCase implements UseCase<RefreshTokenCommand, Tokens> {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenUseCase(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Tokens execute(RefreshTokenCommand command) throws Exception {
        User user = userRepository.findById(command.getUserId());

        if (user == null)
            throw new NotFoundException("User not found.");

        return jwtService.signAuthTokens(user);
    }

}
