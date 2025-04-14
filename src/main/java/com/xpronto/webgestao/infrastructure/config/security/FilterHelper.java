package com.xpronto.webgestao.infrastructure.config.security;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public class FilterHelper {
    public static Optional<String> extractToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && accessToken.startsWith("Bearer "))
            return Optional.of(accessToken.substring(7));

        return Optional.empty();
    }
}
