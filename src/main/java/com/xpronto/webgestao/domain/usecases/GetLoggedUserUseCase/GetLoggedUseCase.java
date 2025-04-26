package com.xpronto.webgestao.domain.usecases.GetLoggedUserUseCase;

import com.xpronto.webgestao.domain.errors.NotFoundException;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.repositories.UserRepository;
import com.xpronto.webgestao.domain.usecases.UseCase;

public class GetLoggedUseCase implements UseCase<GetLoggedUserCommand, User> {

    private final UserRepository userRepository;
    
    public GetLoggedUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(GetLoggedUserCommand command) throws Exception {
       User user = userRepository.findById(command.getUserId(), command.getTenantId());

       if (user == null) {
              throw new NotFoundException("User not found");
       }
       return user;
    }  
    
}