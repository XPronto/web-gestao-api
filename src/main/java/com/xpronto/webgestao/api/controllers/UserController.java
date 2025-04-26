package com.xpronto.webgestao.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpronto.webgestao.api.dtos.request.ConfirmUserRequest;
import com.xpronto.webgestao.api.dtos.request.CreateUserRequest;
import com.xpronto.webgestao.api.dtos.UserResponseDTO;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.services.jwt.UserPayload;
import com.xpronto.webgestao.domain.usecases.GetLoggedUserUseCase.GetLoggedUseCase;
import com.xpronto.webgestao.domain.usecases.GetLoggedUserUseCase.GetLoggedUserCommand;
import com.xpronto.webgestao.domain.usecases.confirmuser.ConfirmUserCommand;
import com.xpronto.webgestao.domain.usecases.confirmuser.ConfirmUserUseCase;
import com.xpronto.webgestao.domain.usecases.createuser.CreateUserCommand;
import com.xpronto.webgestao.domain.usecases.createuser.CreateUserUseCase;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final CreateUserUseCase createUserUseCase;
    private final ConfirmUserUseCase confirmUserUseCase;
    private final GetLoggedUseCase getLoggedUserUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest body) throws Exception {
        var data = new CreateUserCommand(body.getFirstName(), body.getLastName(), body.getEmail(), body.getTenantId());

        User user = createUserUseCase.execute(data);

        return ResponseEntity.created(locationUri(user.getId())).build();
    }

    @PostMapping("confirm")
    public ResponseEntity<Tokens> confirmUser(@AuthenticationPrincipal UserPayload payload, @RequestBody @Valid ConfirmUserRequest body) throws Exception {
        ConfirmUserCommand data = new ConfirmUserCommand(payload.getUserId(), payload.getTenantId(), body.getPassword());
        Tokens tokens = confirmUserUseCase.execute(data);

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/me") 
    public ResponseEntity<UserResponseDTO> getLoggedUser(@AuthenticationPrincipal UserPayload payload) throws Exception {
        GetLoggedUserCommand command = new GetLoggedUserCommand(payload.getUserId(), payload.getTenantId());
        User user = getLoggedUserUseCase.execute(command);
        
        return ResponseEntity.ok(new UserResponseDTO(user.getFirtstName(), user.getLastName(), user.getEmail()));
    }
}
