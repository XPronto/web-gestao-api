package com.xpronto.webgestao.infrastructure.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xpronto.webgestao.domain.errors.UnauthorizedException;
import com.xpronto.webgestao.utils.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${api.api-keys.web-gestao-api-key}")
    private String apiKey;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String method = request.getMethod();

        return !(method.equalsIgnoreCase("POST") && request.getServletPath().equals("/api/tenants"));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String headerKey = request.getHeader("x-api-key");

        if (headerKey == null || !headerKey.equals(apiKey)) {
            var exception = new UnauthorizedException("Invalid api key.");

            response.setStatus(exception.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(Utils.toJSON(exception.mapToResponse(request.getServletPath())));
            return;
        }

        filterChain.doFilter(request, response);
    }

}
