package com.xpronto.webgestao.infrastructure.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.TokenType;
import com.xpronto.webgestao.domain.services.jwt.UserPayload;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InviteTokenAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return !(request.getMethod().equalsIgnoreCase("POST") && request.getServletPath().equals("/api/users/confirm"));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = FilterHelper.extractToken(request);

        if (token.isPresent()) {
            try {
                UserPayload payload = jwtService.verify(token.get(), TokenType.INVITE_TOKEN);

                if (payload != null) {
                    var auth = new UsernamePasswordAuthenticationToken(payload, null, payload.getAuthorities());

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

}
