package com.xpronto.webgestao.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xpronto.webgestao.api.dtos.request.SignInRequest;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.services.jwt.UserPayload;
import com.xpronto.webgestao.domain.usecases.refreshtoken.RefreshTokenCommand;
import com.xpronto.webgestao.domain.usecases.refreshtoken.RefreshTokenUseCase;
import com.xpronto.webgestao.domain.usecases.signin.SignInCommand;
import com.xpronto.webgestao.domain.usecases.signin.SignInUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignInUseCase signInUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("sign-in")
    public ResponseEntity<Tokens> signIn(@RequestBody @Valid SignInRequest body) throws Exception {
        var data = new SignInCommand(body.getEmail(), body.getPassword());

        Tokens tokens = signInUseCase.execute(data);

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("refresh-token")
    public ResponseEntity<Tokens> refreshToken(@AuthenticationPrincipal UserPayload user) throws Exception {
        var command = new RefreshTokenCommand(user.getUserId());
        Tokens tokens = refreshTokenUseCase.execute(command);

        return ResponseEntity.ok(tokens);
    }

}
