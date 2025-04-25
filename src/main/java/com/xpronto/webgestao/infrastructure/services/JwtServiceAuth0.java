package com.xpronto.webgestao.infrastructure.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
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

    @Value("${api.jwt.invite-token.secret}")
    private String inviteTokenSecret;

    @Value("${api.jwt.invite-token.expiration}")
    private int inviteTokenExpiration;

    private final String issuer = "xPronto_api";

    @Override
    public Tokens signAuthTokens(User user) throws Exception {
        String accessToken = signAccessToken(user);
        String refreshToken = signRefreshToken(user);

        return new Tokens(accessToken, refreshToken);
    }

    @Override
    public String signInviteToken(User user) throws Exception {
        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withHeader(genHeaders(TokenType.INVITE_TOKEN))
                .withSubject(user.getId().toString())
                .withClaim("tenant_id", user.getTenant().getId().toString())
                .withExpiresAt(genExpiresAt(inviteTokenExpiration, ChronoUnit.MINUTES))
                .sign(algorithm(inviteTokenSecret));
    }

    private String signAccessToken(User user) throws Exception {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withHeader(genHeaders(TokenType.ACCESS_TOKEN))
                .withClaim("tenant_id", user.getTenant().getId().toString())
                .withClaim("permissions", User.mapPermissionsCodes(user.getPermissionSets()))
                .withExpiresAt(genExpiresAt(accessTokenExpiration, ChronoUnit.MINUTES))
                .sign(algorithm(accessTokenSecret));
    }

    private String signRefreshToken(User user) throws Exception {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withHeader(genHeaders(TokenType.REFRESH_TOKEN))
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

            case INVITE_TOKEN:
                return verifyInviteToken(token);

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

            String type = decodedJWT.getHeaderClaim("typ").asString();

            if (type == null || !type.equals("AC+JWT"))
                throw new JWTVerificationException("Invalid token type.");

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

            String type = decodedJWT.getHeaderClaim("typ").asString();

            if (type == null || !type.equals("RF+JWT"))
                throw new JWTVerificationException("Invalid token type.");

            return new UserPayload(decodedJWT.getSubject(), null, null);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private UserPayload verifyInviteToken(String token) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm(inviteTokenSecret))
                    .withIssuer(issuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            String type = decodedJWT.getHeaderClaim("typ").asString();

            if (type == null || !type.equals("IN+JWT"))
                throw new JWTVerificationException("Invalid token type.");

            return new UserPayload(decodedJWT.getSubject(), decodedJWT.getClaim("tenant_id").asString(), null);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Map<String, Object> genHeaders(TokenType tokenType) throws Exception {
        Map<String, Object> headers = new HashMap<String, Object>();

        switch (tokenType) {
            case ACCESS_TOKEN:
                headers.put("typ", "AC+JWT");
                break;

            case REFRESH_TOKEN:
                headers.put("typ", "RF+JWT");
                break;

            case INVITE_TOKEN:
                headers.put("typ", "IN+JWT");
                break;

            default:
                throw new Exception("Invalid token type.");
        }

        return headers;
    }
}
