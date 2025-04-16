package com.xpronto.webgestao.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpronto.webgestao.api.dtos.request.CreateUserRequest;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.usecases.createuser.CreateUserCommand;
import com.xpronto.webgestao.domain.usecases.createuser.CreateUserUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest body) throws Exception {
        var data = new CreateUserCommand(body.getFirstName(), body.getLastName(), body.getEmail(), body.getTenantId());

        User user = createUserUseCase.execute(data);

        return ResponseEntity.created(locationUri(user.getId())).build();
    }

}
