package com.xpronto.webgestao.infrastructure.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xpronto.webgestao.domain.model.User;
import com.xpronto.webgestao.domain.services.jwt.JwtService;
import com.xpronto.webgestao.domain.services.jwt.TokenType;
import com.xpronto.webgestao.domain.services.jwt.Tokens;
import com.xpronto.webgestao.domain.services.jwt.UserPayload;

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
                .withClaim("permissions", User.mapPermissionsCodes(user.getPermissionSets()))
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

    private Date genExpiresAt(int expiration, ChronoUnit chronoUnit) {
        Instant expiresAt = Instant.now().plus(expiration, chronoUnit);
        return Date.from(expiresAt);
    }

    private Algorithm algorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }

    @Override
    public UserPayload verify(String token, TokenType type) throws Exception {
        switch (type) {
            case ACCESS_TOKEN:
                return verifyAccessToken(token);

            case REFRESH_TOKEN:
                return verifyRefreshToken(token);

            default:
                throw new Exception("Invalid token type.");
        }
    }

    private UserPayload verifyAccessToken(String token) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm(accessTokenSecret))
                    .withIssuer(issuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, Claim> claims = decodedJWT.getClaims();
            String sub = decodedJWT.getSubject();
            String tenantId = claims.get("tenant_id").asString();
            List<String> permissions = claims.get("permissions").asList(String.class);

            return new UserPayload(sub, tenantId, permissions);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private UserPayload verifyRefreshToken(String token) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm(refreshTokenSecret))
                    .withIssuer(issuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return new UserPayload(decodedJWT.getSubject(), null, null);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
