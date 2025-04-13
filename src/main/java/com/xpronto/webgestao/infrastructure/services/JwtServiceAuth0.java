package com.xpronto.webgestao.infrastructure.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xpronto.webgestao.domain.model.PermissionSet;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.Tokens;

@Service
public class JwtServiceAuth0 implements JwtService {
    @Value("${api.jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${api.jwt.access-token.expiration}")
    private int accessTokenExpiration;

    @Value("${api.jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    @Value("${api.jwt.refresh-token.expiration}")
    private int refreshTokenExpiration;

    private final String issuer = "xPronto_api";

    @Override
    public Tokens signAuthTokens(User user) {
        String accessToken = signAccessToken(user);
        String refreshToken = signRefreshToken(user);

        return new Tokens(accessToken, refreshToken);
    }

    private String signAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withClaim("tenant_id", user.getTenant().getId().toString())
                .withClaim("permissions", mapPermissions(user.getPermissionSets()))
                .withExpiresAt(genExpiresAt(accessTokenExpiration, ChronoUnit.MINUTES))
                .sign(algorithm(accessTokenSecret));
    }

    private String signRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(genExpiresAt(refreshTokenExpiration, ChronoUnit.DAYS))
                .sign(algorithm(refreshTokenSecret));
    }

    private List<String> mapPermissions(List<PermissionSet> permissionSets) {
        return permissionSets.stream()
                .flatMap((permissionSet -> permissionSet.getPermissions().stream()
                        .map(permission -> permission.getCode())))
                .toList();
    }

    private Date genExpiresAt(int expiration, ChronoUnit chronoUnit) {
        Instant expiresAt = Instant.now().plus(expiration, chronoUnit);
        return Date.from(expiresAt);
    }

    private Algorithm algorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }
}
